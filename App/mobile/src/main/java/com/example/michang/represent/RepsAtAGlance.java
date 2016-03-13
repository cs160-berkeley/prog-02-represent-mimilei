package com.example.michang.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RepsAtAGlance extends AppCompatActivity {

    private Button see_more_house;
    private Button see_more_senator1;
    private Button see_more_senator2;
    private LinearLayout repsLayout;
    JSONArray rep_json = CoverScreenActivity.congress_info_json;
    public static int repsataglance_counter = 0;
    HashMap<String, Integer> repIndex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("T", "in RepsAtAGlance");

        setContentView(R.layout.activity_reps_at_aglance);
        repsLayout = (LinearLayout) findViewById(R.id.repsLayoutFrame);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            startActivity(intent);
            Log.d("T", "received a message, starting activity");
        }
//        TextView fs = (TextView) findViewById(R.id.from_sunlight);
        if (CoverScreenActivity.congress_info_json == null) {
            Log.d("repsataglance", "congress_info_json is null");
        }

        //For each rep in the returned JSON, create a glance view for them
        LayoutInflater inflater = (LayoutInflater) RepsAtAGlance.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int numReps = rep_json.length();
        for(int i = 0; i < numReps; i++) {
            Log.d("i =", Integer.toString(i));
            repsataglance_counter = i;
            JSONObject currRep = null;
            try {
                currRep = rep_json.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RelativeLayout newRep;
            String party = "";
            String title = "";
            String email = "";
            String website = "";
            String bioguide = "";
            String user = "";
            String twitter = "";
            try {
                party = currRep.getString("party");
                title = currRep.getString("title");
                email = currRep.getString("oc_email");
                website = currRep.getString("website");
                bioguide = currRep.getString("bioguide_id");
                twitter = currRep.getString("twitter_id");
                user = twitter;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (party.equals("D")) {
                newRep = (RelativeLayout)View.inflate(RepsAtAGlance.this, R.layout.glance_demo, null);
            } else if (party.equals("R")) {
                newRep = (RelativeLayout)View.inflate(RepsAtAGlance.this, R.layout.glance_repub, null);
            } else { //you must be an independent
                newRep = (RelativeLayout)View.inflate(RepsAtAGlance.this, R.layout.glance_indep, null);
            }
            newRep.setId(i);
            TextView repName = (TextView)newRep.findViewById(R.id.name);
            TextView repTitle = (TextView)newRep.findViewById(R.id.title);
            TextView repEmail = (TextView)newRep.findViewById(R.id.email);
            TextView repWebsite = (TextView)newRep.findViewById(R.id.website);
            ImageView img = (ImageView)newRep.findViewById(R.id.pic);
            TextView username = (TextView)newRep.findViewById(R.id.username);
            TextView twitterHandle = (TextView)newRep.findViewById(R.id.twitterhandle);
            try {
                String name = "";
                name = name.concat(currRep.getString("first_name"));
                name = name.concat(" ");
                name = name.concat(currRep.getString("last_name"));
                repName.setText(name);
                Log.d("rep name", name);
                if (title.equals("Sen")) {
                    repTitle.setText("Senator");
                    Log.d("rep title", "senator");
                } else {
                    repTitle.setText("Representative");
                    Log.d("rep title", "rep");
                }
                repEmail.setText(email);
                Log.d("rep email", email);
                repWebsite.setText(website);
                Log.d("rep site", website);
                username.setText(name);
                twitterHandle.setText("@"+twitter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String img_url = getString(R.string.img_stub);
            img_url = img_url.concat(bioguide);
            img_url = img_url.concat(".jpg");
            Picasso.with(RepsAtAGlance.this).load(img_url).resize(530,655).into(img);

            Button see_more = (Button)newRep.findViewById(R.id.seeMore);
            see_more.setId(i);

            View.OnClickListener toDetail = new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(RepsAtAGlance.this, GeneralDetail.class);
                    intent.putExtra("index", v.getId());
                    Log.d("id of clicked", Integer.toString(v.getId()));
                    startActivity(intent);
                }
            };
            see_more.setOnClickListener(toDetail);

//            see_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RepsAtAGlance.this, GeneralDetail.class);
//                intent.putExtra("index", repsataglance_counter); //index of the corresponding rep
//                startActivity(intent);
//            }
//        });
            repsLayout.addView(newRep);
        }

//        see_more_house = (Button)findViewById(R.id.seeMore_house);
//        see_more_senator1 = (Button)findViewById(R.id.seeMore_senator1);
//        see_more_senator2 = (Button)findViewById(R.id.seeMore_senator2);
//
//        see_more_house.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RepsAtAGlance.this, HouseDetailActivity.class);
//                startActivity(i);
//            }
//        });
//
//        see_more_senator1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RepsAtAGlance.this, Senator1DetailActivity.class);
//                startActivity(i);
//            }
//        });
//
//        see_more_senator2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RepsAtAGlance.this, Senator2DetailActivity.class);
//                startActivity(i);
//            }
//        });

    }
}
