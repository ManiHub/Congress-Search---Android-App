package com.example.mani_pc7989.webtech;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.mani_pc7989.webtech.ABills;
import com.example.mani_pc7989.webtech.ALegislators;
import com.example.mani_pc7989.webtech.BusinessObjects;
import com.example.mani_pc7989.webtech.DataAccesser;
import com.example.mani_pc7989.webtech.R;
import com.example.mani_pc7989.webtech.TaskCompleted;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bills extends Fragment implements TaskCompleted {


    public Bills() {
        // Required empty public constructor
    }


    // ******* VAriable Declaration ***********//
    View _view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view  = inflater.inflate(R.layout.fragment_bills, container, false);
        _view=view;
        // setting title
        getActivity().setTitle("Bills");

        try{
            GetBillsData1();
        }catch (Exception exp){

        }

        AddTabsTOTabLayout(view);

        // Inflate the layout for this fragment
        return view;
    }

    // ASync call to get data
    void GetBillsData1(){
        try{

                    new DataAccesser(new TaskCompleted() {
                        @Override
                        public void OnTaskCompleted(String json) {
                            try{

                                Gson gson = new GsonBuilder().create();

                                BusinessObjects.Archive.AABills= gson.fromJson(json, ABills.class);
                                if(BusinessObjects.Archive.AABills!=null) {
                                    AppendDataTOUI(BusinessObjects.Archive.AABills,R.id.bill_active_bv);
                                }

                                GetBillsData2();

                            }catch (Exception exp){
                                int x =10;
                            } //http://104.198.0.197:8080/bills?history.active=true&apikey=&per_page=100"
                        }
                    }).execute("http://104.198.0.197:8080/bills?history.active=true&apikey=&per_page=100"); //http://sample-env.jt3d9biumb.us-west-2.elasticbeanstalk.com/index.php?type=Bills&status=active");

        }catch (Exception exp){

        }
    }

    void GetBillsData2(){
        try{

            new DataAccesser(new TaskCompleted() {
                @Override
                public void OnTaskCompleted(String json) {
                    try{

                        Gson gson = new GsonBuilder().create();

                        BusinessObjects.Archive.ANBills= gson.fromJson(json, ABills.class);
                        if(BusinessObjects.Archive.ANBills!=null) {
                            AppendDataTOUI(BusinessObjects.Archive.ANBills,R.id.bill_new_bv);
                        }

                    }catch (Exception exp){
                        int x =10;
                    } //http://104.198.0.197:8080/bills?history.active=true&apikey=&per_page=100"
                }
            }).execute("http://104.198.0.197:8080/bills?history.active=false&apikey=&per_page=100"); //http://sample-env.jt3d9biumb.us-west-2.elasticbeanstalk.com/index.php?type=Bills&status=active");

        }catch (Exception exp){

        }
    }


    // adding tabs to tabhost
    void AddTabsTOTabLayout(View view){

        if (view != null){
            // adding tabs
            TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
            tabHost.setup();

            // adding Tab 1 -  Bt State
            TabHost.TabSpec spec1 = tabHost.newTabSpec("1");
            spec1.setIndicator("Active Bills");
            spec1.setContent(R.id.tab1);
            tabHost.addTab(spec1);

            // adding Tab 2 -  Senate
            TabHost.TabSpec spec2 = tabHost.newTabSpec("2");
            spec2.setIndicator("New Bills");
            spec2.setContent(R.id.tab2);
            tabHost.addTab(spec2);



            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                    if(tabId.equals("1")){
                        AppendDataTOUI(BusinessObjects.Archive.AABills, R.id.bill_active_bv);
                    }else if (tabId.equals("2")){
                        AppendDataTOUI(BusinessObjects.Archive.ANBills, R.id.bill_new_bv);
                    }
                }
            });

        }

    }


    // Appending Data to UI
    public  void AppendDataTOUI(ABills bills,int listId){

        try {
            if (bills != null) {
                int count = bills.getResults().size();  //bills.results.size
                String[] listviewTitle = new String[count];
                String[] listviewShortDescription = new String[count];
                String[] listviewchamber = new String[count];


                // adding legislators title

                for (int i = 0; i < count; i++) {
                    listviewTitle[i] = bills.getResults().get(i).getBillId().toUpperCase();

                    listviewShortDescription[i] = bills.getResults().get(i).getOfficialTitle();

                    Date dateFormat = new SimpleDateFormat("yyyy-mm-dd").parse(bills.getResults().get(i).getIntroducedOn());
                    String _tdate = new SimpleDateFormat("MMM D,YYYY").format(dateFormat);

                    listviewchamber[i] = _tdate;

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


                String[] from = {"listview_title", "listview_discription", "listview_chamber"};
                int[] to = {R.id.bill_title, R.id.bill_description, R.id.bill_date};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.bills_list_item, from, to);
                ListView androidListView = (ListView) _view.findViewById(listId);
                androidListView.setAdapter(simpleAdapter);


                // List listenrr ACtive bills

                ListView listViewActive = (ListView) _view.findViewById(R.id.bill_active_bv);

                listViewActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView t = (TextView) view.findViewById(R.id.bill_description);
                        String text = t.getText().toString().toUpperCase();

                        Intent intent = new Intent(getActivity(), bill_details.class);
                        intent.putExtra("bill_description", text);

                        startActivity(intent);

                    }
                });

                // List New Bills details

                ListView listView = (ListView) _view.findViewById(R.id.bill_new_bv);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView t = (TextView) view.findViewById(R.id.bill_description);
                        String text = t.getText().toString().toUpperCase();

                        Intent intent = new Intent(getActivity(), bill_details.class);
                        intent.putExtra("bill_description", text);

                        startActivity(intent);

                    }
                });

            }
        }catch (Exception exp){

        }
    }

    @Override
    public void OnTaskCompleted(String json) {

    }

}
