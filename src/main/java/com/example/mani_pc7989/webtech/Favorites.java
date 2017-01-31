package com.example.mani_pc7989.webtech;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment {

    static  View _view;

    public Favorites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        _view = inflater.inflate(R.layout.fragment_favorites, container, false);

        getActivity().setTitle("Favorites");

        // adding tabs to the UI

        AddTabsTOTabLayout(_view);

        AppendDataTOUI(BusinessObjects.Archive.FAV_Legislatore,R.id.fav_leg_lv);

        return _view;
    }


    // Adding tabs to Tab Host
    void AddTabsTOTabLayout(View view){

        if (view != null){
            // adding tabs
            TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);
            tabHost.setup();

            // adding Tab 1 -  Bt State
            TabHost.TabSpec spec1 = tabHost.newTabSpec("1");
            spec1.setIndicator("Legislators");
            spec1.setContent(R.id.tab1);
            tabHost.addTab(spec1);

            // adding Tab 2 -  Senate
            TabHost.TabSpec spec2 = tabHost.newTabSpec("2");
            spec2.setIndicator("Bills");
            spec2.setContent(R.id.tab2);
            tabHost.addTab(spec2);


            // adding Tab 3 -  House
            TabHost.TabSpec spec3 = tabHost.newTabSpec("3");
            spec3.setIndicator("Committees");
            spec3.setContent(R.id.tab3);
            tabHost.addTab(spec3);


            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    if(tabId.equals("1")){
                        AppendDataTOUI(BusinessObjects.Archive.FAV_Legislatore,R.id.fav_leg_lv);
                    }else if (tabId.equals("2")){
                        AppendDataTOUI(BusinessObjects.Archive.FAV_Bills,R.id.fav_bills_lv);
                    }else if(tabId.equals("3")){
                        AppendDataTOUI(BusinessObjects.Archive.FAV_Committess,R.id.fav_comit_lv);
                    }
                }
            });

        }

    }



    // adding data +  listener - Legislators
    void AppendDataTOUI(ALegislators legislators, int listId) {

        try {
            if (legislators != null && legislators.getResults() != null && legislators.getResults().size() > 0) {
                int count = legislators.getResults().size();
                String[] listviewTitle = new String[count];
                String[] listviewShortDescription = new String[count];
                String[] listviewImage = new String[count];
                List<String> _LindexState = new ArrayList<>();


                // adding legislators title

                for (int i = 0; i < count; i++) {
                    listviewTitle[i] = legislators.getResults().get(i).getLastName() + "," + legislators.getResults().get(i).getFirstName();
                    String desc = "(" + legislators.getResults().get(i).getParty() + ")";
                    desc += " " + Utilities.Utility.GetState(legislators.getResults().get(i).getState());
                    desc += " Distric " + legislators.getResults().get(i).getDistrict();

                    listviewShortDescription[i] = desc;
                    //listviewImage[i] = R.drawable.logo;
                    listviewImage[i] = "https://theunitedstates.io/images/congress/original/" + legislators.getResults().get(i).getBioguideId() + ".jpg";

                    if (!_LindexState.contains(legislators.getResults().get(i).getLastName().substring(0, 1).toUpperCase())) {
                        _LindexState.add(legislators.getResults().get(i).getLastName().substring(0, 1).toUpperCase());
                    }
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

                //SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.legislators_list_item, from, to);
                ListView androidListView = (ListView) _view.findViewById(listId);
                //androidListView.setAdapter(simpleAdapter);

                CustomAdapter adapter = new CustomAdapter(this.getActivity(), listviewTitle, listviewShortDescription, listviewImage);
                androidListView.setAdapter(adapter);

                // listview listener  - By state
                ListView listView = (ListView) _view.findViewById(R.id.fav_leg_lv);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView t = (TextView) view.findViewById(R.id.leg_name);
                        String text = t.getText().toString();

                        Intent intent = new Intent(getActivity(), legislatore_Details.class);
                        intent.putExtra("Leg_name", text);
                        startActivity(intent);

                    }
                });

                // Indexing

                ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexflv);

                Collections.sort(_LindexState);
                IndexAdapter _adpater = new IndexAdapter(this.getActivity(), _LindexState);
                _Llistview.setAdapter(_adpater);

                _Llistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexflv);
                        String selectedchar = _Llistview.getItemAtPosition(position).toString();

                        int _position = GetPositionLastName(BusinessObjects.Archive.FAV_Legislatore, selectedchar);

                        if (_position != -1) {
                            _Llistview.setSelection(_position);
                        }
                    }
                });

            }
        }catch (Exception exp){

        }
    }


    public  void AppendDataTOUI(ABills bills,int listId){
        try {
            ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexflv);
            _Llistview.setVisibility(View.INVISIBLE);

            if (bills != null && bills.getResults() != null && bills.getResults().size() > 0) {
                int count = bills.getResults().size();  //bills.results.size
                String[] listviewTitle = new String[count];
                String[] listviewShortDescription = new String[count];
                String[] listviewchamber = new String[count];


                // adding legislators title

                for (int i = 0; i < count; i++) {
                    listviewTitle[i] = bills.getResults().get(i).getBillId().toUpperCase();

                    listviewShortDescription[i] = bills.getResults().get(i).getOfficialTitle();

                    listviewchamber[i] = bills.getResults().get(i).getIntroducedOn();

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

                ListView listViewActive = (ListView) _view.findViewById(R.id.fav_bills_lv);

                listViewActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView t = (TextView) view.findViewById(R.id.bill_description);
                        String text = t.getText().toString().toUpperCase();

                        Intent intent = new Intent(getActivity(), bill_details.class);
                        intent.putExtra("bill_description", text);
                        intent.putExtra("bill_status", "A");

                        startActivity(intent);

                    }
                });

            }
        }catch (Exception exp){

        }
    }

    // Appending listeners
    public void AppendDataTOUI(ACommittees committees, int listId){
        try {

            ListView _Llistview = (ListView) _view.findViewById(R.id.L_indexflv);
            _Llistview.setVisibility(View.INVISIBLE);

            if (committees != null && committees.getResults() != null && committees.getResults().size() > 0) {
                int count = committees.getResults().size();
                String[] listviewTitle = new String[count];
                String[] listviewShortDescription = new String[count];
                String[] listviewchamber = new String[count];


                // adding legislators title

                for (int i = 0; i < count; i++) {
                    listviewTitle[i] = committees.getResults().get(i).getCommitteeId().toUpperCase();

                    listviewShortDescription[i] = committees.getResults().get(i).getName();

                    String chamber = committees.getResults().get(i).getChamber();
                    chamber = chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
                    listviewchamber[i] = chamber;

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
                int[] to = {R.id.commit_title, R.id.commit_description, R.id.commit_chamber};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.committees_list_item, from, to);
                ListView androidListView = (ListView) _view.findViewById(listId);
                androidListView.setAdapter(simpleAdapter);


                // List view on click event handing - House

                ListView listView = (ListView) _view.findViewById(R.id.fav_comit_lv);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView t = (TextView) view.findViewById(R.id.commit_title);
                        String text = t.getText().toString();

                        Intent intent = new Intent(getActivity(), committee_details.class);
                        intent.putExtra("Commit_id", text);
                        startActivity(intent);

                    }
                });

            }
        }catch (Exception exp){

        }
    }


    int GetPositionLastName(ALegislators legislators, String selectedchar){

        int position =0;
        try {
            for (int i = 0; i < legislators.getResults().size(); i++) {
                if (selectedchar.equals(legislators.getResults().get(i).getLastName().substring(0, 1).toUpperCase()))
                    position = i;
            }
        }catch (Exception exp){

        }

        return  position;
    }


}




