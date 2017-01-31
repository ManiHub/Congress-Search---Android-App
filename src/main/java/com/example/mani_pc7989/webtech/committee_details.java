package com.example.mani_pc7989.webtech;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class committee_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_details);
        setTitle("Committee Details");

        try {
            String commit_id = getIntent().getExtras().getString("Commit_id").toUpperCase();


            // check if the Image exist in FAV already - Make star Yellow
            ArrayList<CResult> results = BusinessObjects.Archive.FAV_Committess.getResults();

            if(results!=null && results.size()>0){
                for(int i=0;i<results.size();i++){
                    if(commit_id.equals(results.get(i).getCommitteeId())){
                        ImageView img = (ImageView) findViewById(R.id.d_commit_star);
                        img.setImageResource(R.drawable.y);
                        break;
                    }
                }
            }



            ACommittees committees = BusinessObjects.Archive.ACommittees;

            for (int i = 0; i < committees.getCount(); i++) {

                if (commit_id.equals(committees.getResults().get(i).getCommitteeId().toUpperCase())) {

                    // Adding - remove from FAV
                    ImageView img = (ImageView) findViewById(R.id.d_commit_star);

                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try{


                                TextView cidtv = (TextView) findViewById(R.id.c_detail_commitId);
                                String id=cidtv.getText().toString();


                                // Check if this record is in fav -> remove from FAV

                                ArrayList<CResult> _results = BusinessObjects.Archive.FAV_Committess.getResults();

                                if(_results!=null && _results.size()>0){
                                    for(int i=0;i<_results.size();i++){
                                        if(id.equals(_results.get(i).getCommitteeId())){
                                            // removing the record from FAV
                                            _results.remove(_results.get(i));
                                            BusinessObjects.Archive.FAV_Committess.setResults(_results);
                                            // Change the Image to normal
                                            ImageView img = (ImageView) findViewById(R.id.d_commit_star);
                                            img.setImageResource(R.drawable.ic_star_w);
                                            return;
                                        }
                                    }
                                }

                                // Adding the Image to Favroutes and turn star to Yellow


                                for(int i=0;i<BusinessObjects.Archive.ACommittees.getResults().size();i++){
                                    String commit_id = BusinessObjects.Archive.ACommittees.getResults().get(i).getCommitteeId().toUpperCase();

                                    if(commit_id.equals(id)){

                                        ArrayList<CResult> results = BusinessObjects.Archive.FAV_Committess.getResults();

                                        if(results==null){
                                            results=new ArrayList<CResult>();
                                            results.add(BusinessObjects.Archive.ACommittees.getResults().get(i));
                                        }else {
                                            boolean flag = false;

                                            for (int k = 0; k < results.size(); k++) {

                                                String tempid = results.get(k).getCommitteeId();

                                                if (id.equals(tempid)) {
                                                    flag = true;
                                                }
                                            }

                                            if (!flag)
                                                results.add(BusinessObjects.Archive.ACommittees.getResults().get(i));
                                        }

                                            BusinessObjects.Archive.FAV_Committess.setResults(results);

                                            ImageView img = (ImageView) findViewById(R.id.d_commit_star);
                                            img.setImageResource(R.drawable.y);

                                        break;
                                    }

                                }

                            }catch (Exception exp){
                                int x=10;
                            }
                        }
                    });




                    TextView _cid = (TextView) findViewById(R.id.c_detail_commitId);
                    _cid.setText(committees.getResults().get(i).getCommitteeId());

                    TextView _cname = (TextView) findViewById(R.id.c_detail_name);
                    _cname.setText(committees.getResults().get(i).getName());


                    TextView _cchamber = (TextView) findViewById(R.id.c_detail_chamber);
                    _cchamber.setText(committees.getResults().get(i).getChamber().substring(0, 1).toUpperCase() + committees.getResults().get(i).getChamber().substring(1));

                   if(_cchamber.getText().equals("House"))
                    {
                        ImageView imageView=(ImageView) findViewById(R.id.c_details_cimg);
                        imageView.setImageResource(R.drawable.hh);
                    }


                    TextView _cpcommit = (TextView) findViewById(R.id.c_detail_pcommit);
                    if (committees.getResults().get(i).getParentCommitteeId() != null)
                        _cpcommit.setText(committees.getResults().get(i).getParentCommitteeId().toUpperCase());
                    else
                        _cpcommit.setText("N.A");

                    TextView _ccontact = (TextView) findViewById(R.id.c_detail_contact);
                    if (committees.getResults().get(i).getPhone() != null)
                        _ccontact.setText(committees.getResults().get(i).getPhone());
                    else
                        _ccontact.setText("N.A");



                    TextView _coffice = (TextView) findViewById(R.id.c_detail_office);
                    if (committees.getResults().get(i).getOffice() != null)
                        _coffice.setText(committees.getResults().get(i).getOffice());
                    else
                        _coffice.setText("N.A");


                    break;

                }

            }
        }catch (Exception exp){

        }


    }
}
