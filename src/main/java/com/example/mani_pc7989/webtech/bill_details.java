package com.example.mani_pc7989.webtech;

import android.content.Intent;
import android.icu.text.NumberingSystem;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class bill_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        setTitle("Bill Details");

        // BAck button

       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            String bill_description = getIntent().getExtras().getString("bill_description").toUpperCase();

            ABills bills = null;


            boolean Ispresent =false;

            for(int i=0;i<BusinessObjects.Archive.AABills.getResults().size();i++) {
                if (bill_description.equals(BusinessObjects.Archive.AABills.getResults().get(i).getOfficialTitle().toUpperCase())) {
                    bills = BusinessObjects.Archive.AABills;
                    Ispresent = true;
                    break;
                }
            }

            if(!Ispresent){
                for(int i=0;i<BusinessObjects.Archive.ANBills.getResults().size();i++) {
                    if (bill_description.equals(BusinessObjects.Archive.ANBills.getResults().get(i).getOfficialTitle().toUpperCase())) {
                        bills = BusinessObjects.Archive.ANBills;
                        break;
                    }
                }
            }



            // Check for this record in Fav
            ArrayList<ABResult> _results = BusinessObjects.Archive.FAV_Bills.getResults();

            if(_results!=null && _results.size()>0){
                for(int k=0;k<_results.size();k++){
                    String _bill_description = _results.get(k).getOfficialTitle().toUpperCase();
                    if(_bill_description.equals(bill_description)){

                        ImageView img = (ImageView) findViewById(R.id.d_bill_star);
                        img.setImageResource(R.drawable.y);
                        break;
                    }
                }
            }



            for (int i = 0; i < bills.getResults().size(); i++) {

                if (bill_description.equals(bills.getResults().get(i).getOfficialTitle().toUpperCase())) {

                    TextView _bid = (TextView) findViewById(R.id.b_details_billid);
                    _bid.setText(bills.getResults().get(i).getBillId().toUpperCase());

                    TextView _btitle = (TextView) findViewById(R.id.b_details_title);
                    _btitle.setText(bills.getResults().get(i).getOfficialTitle());

                    TextView _btype = (TextView) findViewById(R.id.b_details_btype);
                    _btype.setText(bills.getResults().get(i).getBillType().toUpperCase());


                    if (bills.getResults().get(i).getSponsor() != null) {
                        TextView _bsponsor = (TextView) findViewById(R.id.b_details_sponsor);

                        String sponsor = bills.getResults().get(i).getSponsor().getTitle() + ".";
                        sponsor += bills.getResults().get(i).getSponsor().getLastName() + ",";
                        sponsor += bills.getResults().get(i).getSponsor().getFirstName();
                        _bsponsor.setText(sponsor);
                    }


                    TextView _bchamber = (TextView) findViewById(R.id.b_details_chamber);
                    if (bills.getResults().get(i).getChamber() != null)
                        _bchamber.setText(bills.getResults().get(i).getChamber().substring(0,1).toUpperCase()+bills.getResults().get(i).getChamber().substring(1));

                    if (bills.getResults().get(i).getHistory() != null) {
                        TextView _bstatus = (TextView) findViewById(R.id.b_details_statuc);
                        if (bills.getResults().get(i).getHistory().getActive())
                            _bstatus.setText("Active");
                        else
                            _bstatus.setText("New");
                    }

                    Date _dateFormat = new SimpleDateFormat("yyyy-mm-dd").parse(bills.getResults().get(i).getIntroducedOn());
                    String __tdate = new SimpleDateFormat("MMM D,YYYY").format(_dateFormat);


                    TextView _bIntroducedOn = (TextView) findViewById(R.id.b_details_introduced);
                    _bIntroducedOn.setText(__tdate);


                    TextView _bcongressurl = (TextView) findViewById(R.id.b_details_congressurl);
                    if (bills.getResults().get(i).getUrls() != null) {
                        //TextView _bcongressurl = (TextView) findViewById(R.id.b_details_congressurl);
                        _bcongressurl.setText(bills.getResults().get(i).getUrls().getCongress());
                    }

                    _bcongressurl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            TextView _bcongressurl = (TextView) findViewById(R.id.b_details_congressurl);
                            String _url = _bcongressurl.getText().toString();

                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(_url)));
                        }
                    });




                    if (bills.getResults().get(i).getLastVersion() != null) {
                        TextView _bversionstatus = (TextView) findViewById(R.id.b_details_versionstatus);
                        _bversionstatus.setText(bills.getResults().get(i).getLastVersion().getVersionName());
                    }


                    TextView _bbillurl = (TextView) findViewById(R.id.b_details_burl);
                    if (bills.getResults().get(i).getLastVersion() != null && bills.getResults().get(i).getLastVersion().getUrls() != null) {
                        _bbillurl.setText(bills.getResults().get(i).getLastVersion().getUrls().getPdf());
                    }

                    _bbillurl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            TextView _bbillurl = (TextView) findViewById(R.id.b_details_burl);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/gview?embedded=true&url="+_bbillurl.getText().toString())));
                        }
                    });

                    // Image

                    ImageView img = (ImageView) findViewById(R.id.d_bill_star);

                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {

                                TextView cidtv = (TextView) findViewById(R.id.b_details_title);
                                String title = cidtv.getText().toString();

                                // Check for the record in fav
                                boolean _ISP = CheckForBill(BusinessObjects.Archive.FAV_Bills,title);

                                if(_ISP){
                                    return;
                                }

                                // Adding this bill to the FAV

                                boolean IsPresent = AddbillsToFav(BusinessObjects.Archive.AABills, title);

                                if(!IsPresent) {
                                    boolean Ispres = AddbillsToFav(BusinessObjects.Archive.ANBills, title);
                                }

                                ImageView img = (ImageView) findViewById(R.id.d_bill_star);
                                img.setImageResource(R.drawable.y);


                            } catch (Exception exp) {
                                int x = 10;
                            }
                        }
                    });


                    break;

                }
            }
        } catch (Exception exp) {

            int x = 10;
        }
    }

    boolean AddbillsToFav(ABills bills, String title) {

        try {
            for (int i = 0; i < bills.getResults().size(); i++) {
                String commit_id = bills.getResults().get(i).getOfficialTitle();
                if (commit_id.equals(title)) {

                    ArrayList<ABResult> results = BusinessObjects.Archive.FAV_Bills.getResults();

                    if (results == null) {
                        results = new ArrayList<ABResult>();
                        results.add(bills.getResults().get(i));
                        BusinessObjects.Archive.FAV_Bills.setResults(results);
                        return true;

                    } else {
                        boolean flag = false;

                        for (int k = 0; k < results.size(); k++) {

                            String tempid = results.get(k).getOfficialTitle();

                            if (title.equals(tempid)) {
                                flag = true;
                            }
                        }

                        if (!flag){
                            results.add(bills.getResults().get(i));
                            BusinessObjects.Archive.FAV_Bills.setResults(results);
                            return true;
                        }

                    }
                    break;
                }

            }
        }catch (Exception exp){

            int x =10;
        }
        return false;
    }

    boolean CheckForBill(ABills bill, String title){
        try{

            if(bill!=null && bill.getResults()!=null && bill.getResults().size()>0){

                for(int i=0;i<bill.getResults().size();i++){
                    String _title = bill.getResults().get(i).getOfficialTitle().toUpperCase();

                    if(title.toUpperCase().equals(_title))
                    {
                        ArrayList<ABResult> _res = BusinessObjects.Archive.FAV_Bills.getResults();
                        _res.remove(bill.getResults().get(i));
                        BusinessObjects.Archive.FAV_Bills.setResults(_res);

                        ImageView imageView = (ImageView) findViewById(R.id.d_bill_star);
                        imageView.setImageResource(R.drawable.ic_star_w);
                        return true;
                    }
                }
            }

        }catch (Exception exp){

        }

        return  false;
    }

    /*
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        //NavUtils.navigateUpFromSameTask(this);
        return  true;
    }

    */
}