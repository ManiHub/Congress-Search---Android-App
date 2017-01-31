package com.example.mani_pc7989.webtech;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.mani_pc7989.webtech.ALegislators;
import com.example.mani_pc7989.webtech.BusinessObjects;
import com.example.mani_pc7989.webtech.DataAccesser;
import com.example.mani_pc7989.webtech.R;
import com.example.mani_pc7989.webtech.TaskCompleted;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class Legislators extends Fragment implements TaskCompleted {


    public Legislators() {
        // Required empty public constructor
    }


    // ********* Variable declaration **********//
    String json="";
    View _view = null;
    static int _SelectedList;
    static  ALegislators _selectedLeg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_legislators, container, false);
        _view = view;
        // setting title
        getActivity().setTitle("Legislators");

        try{
                GetLegislatorsData(view);
        }catch (Exception exp){

        }

        AddTabsTOTabLayout(view);

        return view;
    }


    // Async call to Get ddata
    void GetLegislatorsData(View view){
        String Json="";

        try{

            if(BusinessObjects.Archive.A_Legislators==null) {

                new DataAccesser(new TaskCompleted() {
                    @Override
                    public void OnTaskCompleted(String json) {
                        try {

                            Gson gson = new GsonBuilder().create();

                            BusinessObjects.Archive.A_Legislators = gson.fromJson(json, ALegislators.class);

                            if (BusinessObjects.Archive.A_Legislators != null) {
                                ALegislators leg = SortByState(BusinessObjects.Archive.A_Legislators);
                                AppendDataTOUI(BusinessObjects.Archive.A_Legislators,R.id.leg_stae_lv,"S");
                            }

                        } catch (Exception exp) {
                            int x = 10;
                        }
                    }//http://104.198.0.197:8080/legislators?apikey=557e1c43ef49410ba98106c87347f140&per_page=all
                }).execute("http://104.198.0.197:8080/legislators?apikey=557e1c43ef49410ba98106c87347f140&per_page=all"); //http://sample-env.jt3d9biumb.us-west-2.elasticbeanstalk.com/index.php?type=Legislators
            }
            else {
                AppendDataTOUI(BusinessObjects.Archive.A_Legislators,R.id.leg_stae_lv,"S");
            }


        }catch (Exception exp){

        }
    }

    // Adding tabs to Tab Host
    void AddTabsTOTabLayout(View view)      {

        if (view != null){
            // adding tabs
            TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
            tabHost.setup();

            // adding Tab 1 -  Bt State
            TabHost.TabSpec spec1 = tabHost.newTabSpec("1");
            spec1.setIndicator("By State");
            spec1.setContent(R.id.tab1);
            tabHost.addTab(spec1);

            // adding Tab 2 -  Senate
            TabHost.TabSpec spec2 = tabHost.newTabSpec("2");
            spec2.setIndicator("Senate");
            spec2.setContent(R.id.tab2);
            tabHost.addTab(spec2);


            // adding Tab 3 -  House
            TabHost.TabSpec spec3 = tabHost.newTabSpec("3");
            spec3.setIndicator("House");
            spec3.setContent(R.id.tab3);
            tabHost.addTab(spec3);

            // Setting tab select color


            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    if(tabId.equals("1")){
                        ALegislators _legislators = SortByState(BusinessObjects.Archive.A_Legislators);
                        AppendDataTOUI(BusinessObjects.Archive.A_Legislators,R.id.leg_stae_lv,"S");
                    }else if (tabId.equals("2")){

                        ALegislators _legislators = FilterByHouse(BusinessObjects.Archive.A_Legislators,"SENATE");
                        AppendDataTOUI(_legislators,R.id.leg_senate_lv,"L");
                    }else if(tabId.equals("3")){
                        ALegislators _legislators = FilterByHouse(BusinessObjects.Archive.A_Legislators,"HOUSE");
                        AppendDataTOUI(_legislators,R.id.leg_house_lv,"L");
                    }
                }
            });

        }

    }


    // adding tabs listener
    void AppendDataTOUI(ALegislators legislators, int listId,String indexing) {

        if (legislators != null && legislators.getResults()!=null && legislators.getResults().size()>0) {
            int count = legislators.getResults().size();
            String[] listviewTitle = new String[count];
            String[] listviewShortDescription = new String[count];
            String[] listviewImage = new String[count];

        // adding legislators title

        for (int i = 0; i < count; i++) {
            listviewTitle[i] = legislators.getResults().get(i).getLastName()+","+legislators.getResults().get(i).getFirstName();
            String desc = "("+legislators.getResults().get(i).getParty()+")";
            desc +=" "+ Utilities.Utility.GetState(legislators.getResults().get(i).getState());
            desc +="- Distric "+legislators.getResults().get(i).getDistrict();

            listviewShortDescription[i] = desc;
            //listviewImage[i] = R.drawable.logo;
            listviewImage[i] = "https://theunitedstates.io/images/congress/original/"+legislators.getResults().get(i).getBioguideId()+".jpg";
        }
            // legislators.results.get(i).

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < count; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", listviewImage[i]);
            aList.add(hm);
        }



        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.leg_img, R.id.leg_name, R.id.leg_desc};

            ListView androidListView = (ListView) _view.findViewById(listId);
            CustomAdapter adapter=new CustomAdapter(this.getActivity(), listviewTitle, listviewShortDescription,listviewImage);
            androidListView.setAdapter(adapter);

            // for index L1
            List<String> _LindexState = new ArrayList<>();
            List<String> _LindexLastname = new ArrayList<>();
            for(int i=0;i<count;i++){
                if(!_LindexState.contains(legislators.getResults().get(i).getState().substring(0,1)))
                    _LindexState.add(legislators.getResults().get(i).getState().substring(0,1));

                if(!_LindexLastname.contains(legislators.getResults().get(i).getLastName().substring(0,1).toUpperCase()))
                    _LindexLastname.add(legislators.getResults().get(i).getLastName().substring(0,1).toUpperCase());
            }

            Collections.sort(_LindexLastname);
            Collections.sort(_LindexState);

            ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexlv);

            if(indexing.equals("S")) {
                IndexAdapter _adpater = new IndexAdapter(this.getActivity(), _LindexState);
                _Llistview.setAdapter(_adpater);
            }else
            {
                IndexAdapter _adpater = new IndexAdapter(this.getActivity(), _LindexLastname);
                _Llistview.setAdapter(_adpater);
            }

            _SelectedList = listId;
            _selectedLeg = legislators;
            _Llistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexlv);
                        String selectedchar = _Llistview.getItemAtPosition(position).toString();

                        ListView _list = (ListView) _view.findViewById(_SelectedList);
                        int _position = -1;

                        if (_SelectedList == R.id.leg_stae_lv)
                            _position = GetPositionState(_selectedLeg, selectedchar);
                        else
                            _position = GetPositionLastName(_selectedLeg, selectedchar);

                        if (_position != -1) {
                            _list.setSelection(_position);
                        }

                    }catch (Exception exp){

                    }
                }
            });

        // listview listener  - By state
            ListView listView = (ListView) _view.findViewById(R.id.leg_stae_lv);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.leg_name);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),legislatore_Details.class);
                    intent.putExtra("Leg_name",text);
                    startActivity(intent);

                }
            });



            // listner - By Senate
            ListView listView_senate = (ListView) _view.findViewById(R.id.leg_senate_lv);

            listView_senate.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.leg_name);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),legislatore_Details.class);
                    intent.putExtra("Leg_name",text);
                    startActivity(intent);

                }
            });


            // listener  - By HOuse

            ListView listView_house = (ListView) _view.findViewById(R.id.leg_house_lv);

            listView_house.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.leg_name);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),legislatore_Details.class);
                    intent.putExtra("Leg_name",text);
                    startActivity(intent);

                }
            });

      }
    }

    // sort by State
    ALegislators SortByState(ALegislators legislators){

        ALegislators _legislators= null;
        ArrayList<ALResults> resultlist = legislators.getResults() ;
        try{

            //Collections.sort(legislators.getResults(), resultlist);

        int y=0;
            Collections.sort(resultlist, new Comparator<ALResults>(){
                public int compare(ALResults o1, ALResults o2){
                    String  s1 = Utilities.Utility.GetState(o1.getState());
                    String  s2 = Utilities.Utility.GetState(o2.getState());
                    if(s1==null || s2==null)
                    {
                        int y=0;
                    }
                    return s1.compareTo(s2);
                }
            });

            int x =20;

        }catch (Exception exp){
            int x=0;
        }

        return legislators;
    }

    // Sort by Last Name
    ALegislators SortByLastName(ALegislators legislators){

        ALegislators _legislators= null;
        ArrayList<ALResults> resultlist = legislators.getResults() ;
        try{

            //Collections.sort(legislators.getResults(), resultlist);

            int y=0;
            Collections.sort(resultlist, new Comparator<ALResults>(){
                public int compare(ALResults o1, ALResults o2){
                    String  s1 = o1.getLastName();
                    String  s2 =o2.getLastName();
                    if(s1==null || s2==null)
                    {
                        int y=0;
                    }
                    return s1.compareTo(s2);
                }
            });

            int x =20;

        }catch (Exception exp){
            int x=0;
        }

        return legislators;
    }

    // sort by house/Senate
    ALegislators FilterByHouse(ALegislators legislators, String param){

        ALegislators _legislators = new ALegislators();
        ArrayList<ALResults> result = new ArrayList<>();

        for (int i=0;i<legislators.getResults().size();i++){
            if(legislators.getResults().get(i).getChamber().toUpperCase().equals(param)){
                result.add(legislators.getResults().get(i));
            }
        }
        _legislators.setResults(result);

        _legislators = SortByLastName(_legislators);
        return _legislators;
    }

    int GetPositionState(ALegislators legislators, String selectedchar){

        int position =0;
        for(int i=0;i<legislators.getResults().size();i++){
            if(selectedchar.equals(legislators.getResults().get(i).getState().substring(0,1).toUpperCase()))
                position=i;
        }

        return  position;
    }


    int GetPositionLastName(ALegislators legislators, String selectedchar){

        int position =0;
        for(int i=0;i<legislators.getResults().size();i++){
            if(selectedchar.equals(legislators.getResults().get(i).getLastName().substring(0,1).toUpperCase()))
                position=i;
        }

        return  position;
    }

    @Override
    public void OnTaskCompleted(String json) {

    }
}
