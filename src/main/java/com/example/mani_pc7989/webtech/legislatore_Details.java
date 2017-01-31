package com.example.mani_pc7989.webtech;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class legislatore_Details extends AppCompatActivity {

    static String fb_id = "";
    static String tw_id = "";
    static String website_url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislatore__details);
        setTitle("Legslators Details");

        try {
            // SEt title
            //getActionBar().setTitle("Legislator Info");

            String passedArg = getIntent().getExtras().getString("Leg_name");


            // Check for this record in fav
            ArrayList<ALResults> _results = BusinessObjects.Archive.FAV_Legislatore.getResults();

            if (_results != null && _results.size() > 0) {
                for (int k = 0; k < _results.size(); k++) {
                    String _name = _results.get(k).getLastName() + "," + _results.get(k).getFirstName();
                    if (_name.equals(passedArg)) {

                        ImageView img = (ImageView) findViewById(R.id.d_leg_star);
                        img.setImageResource(R.drawable.y);
                        break;
                    }
                }
            }


            final ALegislators legislators = BusinessObjects.Archive.A_Legislators;
            for (int i = 0; i < legislators.count; i++) {
                String name = legislators.getResults().get(i).getLastName() + "," + legislators.getResults().get(i).getFirstName();

                if (name.equals(passedArg)) {

                    // Settting Legislators Image
                    ImageView leg_img = (ImageView) findViewById(R.id.d_leg_img);

                    Picasso.with(getBaseContext())
                            .load("https://theunitedstates.io/images/congress/original/" + legislators.getResults().get(i).getBioguideId() + ".jpg")
                            .resize(250, 250)
                            .into(leg_img);


                    // Setting party Image
                    ImageView patyImg = (ImageView) findViewById(R.id.d_leg_partylogo);
                    TextView d_leg_patyname = (TextView) findViewById(R.id.d_leg_partyname);

                    if (legislators.getResults().get(i).getParty().equals("R")) {
                        patyImg.setImageResource(R.drawable.r);
                        d_leg_patyname.setText("Republican");
                    } else {
                        patyImg.setImageResource(R.drawable.d);
                        d_leg_patyname.setText("Democratic");
                    }


                    TextView _name = (TextView) findViewById(R.id.d_leg_name);
                    _name.setText(legislators.getResults().get(i).getTitle() + "." + name);


                    TextView _email = (TextView) findViewById(R.id.d_leg_email);
                    _email.setText(legislators.getResults().get(i).getOcEmail());
                    _email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView _email = (TextView) findViewById(R.id.d_leg_email);
                           // startActivity(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                             //        "mailto", _email.getText().toString(), null)));

                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto", _email.getText().toString(), null));
                            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                        }
                    });


                    TextView _chamber = (TextView) findViewById(R.id.d_leg_chamber);
                    _chamber.setText(legislators.getResults().get(i).getChamber());


                    TextView _contact = (TextView) findViewById(R.id.d_leg_contact);
                    _contact.setText(legislators.getResults().get(i).getPhone());

                    Date startterm = new SimpleDateFormat("yyyy-mm-dd").parse(legislators.getResults().get(i).getTermStart());
                    String _tdate = new SimpleDateFormat("MMM D,YYYY").format(startterm);

                    TextView _startterm = (TextView) findViewById(R.id.d_leg_start_term);
                    _startterm.setText(_tdate);

                    Date endterm = new SimpleDateFormat("yyyy-mm-dd").parse(legislators.getResults().get(i).getTermEnd());
                    String __tdate = new SimpleDateFormat("MMM D,YYYY").format(endterm);

                    TextView _endterm = (TextView) findViewById(R.id.d_leg_endTerm);
                    _endterm.setText(__tdate);


                    double _Current = new Date().getTime();
                    double _start = new SimpleDateFormat("YYYY-MM-DD").parse(legislators.getResults().get(i).getTermStart()).getTime();
                    double _end = new SimpleDateFormat("YYYY-MM-DD").parse(legislators.getResults().get(i).getTermEnd()).getTime();


                    double Progress = Math.round(((_Current-_start)/(_end-_start))*100);

                    ProgressBar _pbar = (ProgressBar) findViewById(R.id.progressBar1);
                    _pbar.setProgress((int) Progress);


                    TextView _term = (TextView) findViewById(R.id.d_leg_term);
                    int p = (int) Progress;
                    _term.setText(p+"%");


                    TextView _offcie = (TextView) findViewById(R.id.d_leg_office);
                    _offcie.setText(legislators.getResults().get(i).getOffice());

                    TextView _state = (TextView) findViewById(R.id.d_leg_state);
                    _state.setText(Utilities.Utility.GetState(legislators.getResults().get(i).getState()));

                    TextView _fax = (TextView) findViewById(R.id.d_leg_fax);
                    _fax.setText(legislators.getResults().get(i).getFax());

                    Date _bday = new SimpleDateFormat("yyyy-mm-dd").parse(legislators.getResults().get(i).getBirthday());
                    String __bday = new SimpleDateFormat("MMM D,YYYY").format(_bday);

                    TextView _birthday = (TextView) findViewById(R.id.d_leg_birthday);
                    _birthday.setText(__bday);


                    fb_id = legislators.getResults().get(i).getFacebookId();
                    tw_id = legislators.getResults().get(i).getTwitterId();
                    website_url = legislators.getResults().get(i).getWebsite();

                    // Adding fb

                    ImageView fb = (ImageView) findViewById(R.id.d_leg_fb);
                    fb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (fb_id == null) {

                                Toast.makeText(getApplicationContext(), "Facebook Id is not available for this record", Toast.LENGTH_LONG).show();
                            }else {
                                Uri uri = Uri.parse("https://facebook.com/" + fb_id); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    });


                    // twitter

                    final ImageView tw = (ImageView) findViewById(R.id.d_leg_tw);
                    tw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (tw_id == null) {

                                Toast.makeText(getApplicationContext(), "Twitter Id is not available for this record", Toast.LENGTH_LONG).show();
                            }else {
                                Uri uri = Uri.parse("https://twitter.com/" + tw_id); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    });


                    // website

                    final ImageView website = (ImageView) findViewById(R.id.d_leg_web);
                    website.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (website_url == null) {

                                Toast.makeText(getApplicationContext(), "Website URL is not available for this record", Toast.LENGTH_LONG).show();
                            }else {
                                Uri uri = Uri.parse(website_url); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    });


                    // Fav Image
                    ImageView img = (ImageView) findViewById(R.id.d_leg_star);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                TextView textView = (TextView) findViewById(R.id.d_leg_name);

                                // Check for Removal from Favroutes
                                ArrayList<ALResults> _results = BusinessObjects.Archive.FAV_Legislatore.getResults();

                                if (_results != null && _results.size() > 0) {
                                    for (int i = 0; i < _results.size(); i++) {
                                        String name = _results.get(i).getTitle() + "." + _results.get(i).getLastName() + "," + _results.get(i).getFirstName();
                                        if (textView.getText().equals(name)) {

                                            // removing the record from FAV
                                            _results.remove(_results.get(i));
                                            BusinessObjects.Archive.FAV_Legislatore.setResults(_results);

                                            // Change the Image to normal
                                            ImageView img = (ImageView) findViewById(R.id.d_leg_star);
                                            img.setImageResource(R.drawable.ic_star_w);
                                            return;
                                        }
                                    }
                                }

                                // Adding Record to Favroutes and changing Star Logo
                                ArrayList<ALResults> results = BusinessObjects.Archive.FAV_Legislatore.getResults();

                                for (int i = 0; i < BusinessObjects.Archive.A_Legislators.getResults().size(); i++) {

                                    String name = legislators.getResults().get(i).getTitle() + "." + legislators.getResults().get(i).getLastName() + "," + legislators.getResults().get(i).getFirstName();

                                    if (name.equals(textView.getText())) {
                                        if (results == null) {
                                            results = new ArrayList<ALResults>();
                                            results.add(BusinessObjects.Archive.A_Legislators.getResults().get(i));
                                        } else {
                                            boolean flag = false;
                                            for (int j = 0; j < results.size(); j++) {
                                                String s1 = results.get(j).getTitle();
                                                String s2 = results.get(j).getLastName();
                                                String s3 = results.get(j).getFirstName();
                                                String s4 = s1 + "." + s2 + "," + s3;
                                                if (s4.equals(name)) {
                                                    flag = true;
                                                }
                                            }

                                            if (!flag)
                                                results.add(BusinessObjects.Archive.A_Legislators.getResults().get(i));
                                        }

                                        BusinessObjects.Archive.FAV_Legislatore.setResults(results);

                                        ImageView img = (ImageView) findViewById(R.id.d_leg_star);
                                        img.setImageResource(R.drawable.y);
                                        break;
                                    }

                                }
                            } catch (Exception exp) {
                                int x = 10;
                            }
                        }
                    });

                /*

                RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.relativeLayout);

                ImageView fb = new ImageView(this);
                fb.setImageResource(R.drawable.f);
                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = Uri.parse("https://facebook.com/"+fb_id); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

                ImageView _star = (ImageView) findViewById(R.id.d_leg_star);
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                p.addRule(RelativeLayout.ALIGN_BOTTOM, _star.getId());

                relativelayout.addView(fb, p);

                */


                    break;
                }
            }
        }catch (Exception exp){

        }

    }
}
