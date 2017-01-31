package com.example.mani_pc7989.webtech;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.mani_pc7989.webtech.ABills;
import com.example.mani_pc7989.webtech.ACommittees;
import com.example.mani_pc7989.webtech.BusinessObjects;
import com.example.mani_pc7989.webtech.DataAccesser;
import com.example.mani_pc7989.webtech.R;
import com.example.mani_pc7989.webtech.TaskCompleted;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Committees extends Fragment implements TaskCompleted{


    public Committees() {
        // Required empty public constructor
    }

    // ******* Variable Declaration *********//
    String json="";
    ACommittees committees = null;
    View _view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_committees, container, false);
        _view = view;

        // setting title
        getActivity().setTitle("Committees");

        try{
            GetCommitteesData();
        }catch (Exception exp){

        }

        AddTabsTOTabLayout(view);

        // Inflate the layout for this fragment
        return _view;
    }


    // Async call to get Data
    void GetCommitteesData(){

        try {
            if (BusinessObjects.Archive.ACommittees == null) {
                        new DataAccesser(new TaskCompleted() {
                            @Override
                            public void OnTaskCompleted(String json) {
                                try {

                                    Gson gson = new GsonBuilder().create();

                                    committees = gson.fromJson(json, ACommittees.class);

                                    if (committees != null) {
                                        BusinessObjects.Archive.ACommittees = committees;
                                        ACommittees _committees = FilterByParameter(BusinessObjects.Archive.ACommittees,"HOUSE");
                                        AppendDataTOUI(_committees, R.id.commit_house_cv);
                                    }

                                } catch (Exception exp) {
                                    int x = 10;
                                } // http://104.198.0.197:8080/committees?apikey=557e1c43ef49410ba98106c87347f140&per_page=all
                            }
                        }).execute("http://104.198.0.197:8080/committees?apikey=557e1c43ef49410ba98106c87347f140&per_page=all"); //http://sample-env.jt3d9biumb.us-west-2.elasticbeanstalk.com/index.php?type=Committees
            }else
            {
                ACommittees _committees = FilterByParameter(BusinessObjects.Archive.ACommittees,"HOUSE");
                AppendDataTOUI(_committees,R.id.commit_house_cv);
            }

            }catch(Exception exp){

            }
    }


    // Ading Tabs to Tabhost
    void AddTabsTOTabLayout(View view){

        if (view != null){
            // adding tabs
            TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
            tabHost.setup();

            // adding Tab 1 -  Bt State
            TabHost.TabSpec spec1 = tabHost.newTabSpec("1");
            spec1.setIndicator("House");
            spec1.setContent(R.id.tab1);
            tabHost.addTab(spec1);

            // adding Tab 2 -  Senate
            TabHost.TabSpec spec2 = tabHost.newTabSpec("2");
            spec2.setIndicator("Senate");
            spec2.setContent(R.id.tab2);
            tabHost.addTab(spec2);


            // adding Tab 3 -  House
            TabHost.TabSpec spec3 = tabHost.newTabSpec("3");
            spec3.setIndicator("Joint");
            spec3.setContent(R.id.tab3);
            tabHost.addTab(spec3);


            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                    if(tabId.equals("1")){
                        ACommittees _committees = FilterByParameter(BusinessObjects.Archive.ACommittees,"HOUSE");
                        AppendDataTOUI(_committees, R.id.commit_house_cv);

                    }else if (tabId.equals("2")){
                        ACommittees _committees = FilterByParameter(BusinessObjects.Archive.ACommittees,"SENATE");
                        AppendDataTOUI(_committees, R.id.commit_senate_cv);

                    }else if(tabId.equals("3")){
                        ACommittees _committees = FilterByParameter(BusinessObjects.Archive.ACommittees,"JOINT");
                        AppendDataTOUI(_committees, R.id.commit_joint_cv);


                    }
                }
            });

        }

    }


    // Appending listeners
    public void AppendDataTOUI(ACommittees committees, int listId){
        if (committees != null) {
            int count = committees.getResults().size();
            String[] listviewTitle = new String[count];
            String[] listviewShortDescription = new String[count];
            String[] listviewchamber = new String[count];


            // adding legislators title

            for (int i = 0; i < count; i++) {
                listviewTitle[i] = committees.getResults().get(i).getCommitteeId().toUpperCase();

                listviewShortDescription[i] = committees.getResults().get(i).getName();

                String chamber  = committees.getResults().get(i).getChamber();
                chamber=chamber.substring(0,1).toUpperCase()+chamber.substring(1);
                listviewchamber[i]=chamber;

            }
            // legislators.results.get(i).

            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < count; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("listview_title", listviewTitle[i]);
                hm.put("listview_discription", listviewShortDescription[i]);
                hm.put("listview_chamber", listviewchamber[i]);
                aList.add(hm);
            }


            String[] from = {"listview_title", "listview_discription","listview_chamber"};
            int[] to = {R.id.commit_title, R.id.commit_description, R.id.commit_chamber};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.committees_list_item, from, to);
            ListView androidListView = (ListView) _view.findViewById(listId);
            androidListView.setAdapter(simpleAdapter);


            // List view on click event handing - House

            ListView listView = (ListView) _view.findViewById(R.id.commit_senate_cv);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.commit_title);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),committee_details.class);
                    intent.putExtra("Commit_id",text);
                    startActivity(intent);

                }
            });


            // Listener to  Senate

            ListView listViewhouse = (ListView) _view.findViewById(R.id.commit_house_cv);

            listViewhouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.commit_title);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),committee_details.class);
                    intent.putExtra("Commit_id",text);
                    startActivity(intent);

                }
            });


            // Listener to Joint

            ListView listViewjoint = (ListView) _view.findViewById(R.id.commit_joint_cv);

            listViewjoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView t = (TextView) view.findViewById(R.id.commit_title);
                    String text = t.getText().toString();

                    Intent intent = new Intent(getActivity(),committee_details.class);
                    intent.putExtra("Commit_id",text);
                    startActivity(intent);

                }
            });



        }
    }


    ACommittees FilterByParameter(ACommittees committees, final String param){

        ACommittees filteredCommittees = new ACommittees();

        ArrayList<CResult> resultlist = new ArrayList<>();

        for(int i=0;i<committees.getCount();i++){
            if(committees.getResults().get(i).getChamber().toUpperCase().equals(param)){
                resultlist.add((committees.getResults().get(i)));
            }
        }

        filteredCommittees.setResults(resultlist);

        return  filteredCommittees;
    }


    @Override
    public void OnTaskCompleted(String json) {

    }

}
