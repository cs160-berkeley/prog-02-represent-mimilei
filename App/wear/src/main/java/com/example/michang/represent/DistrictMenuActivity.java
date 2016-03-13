package com.example.michang.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DistrictMenuActivity extends Activity {

    private String zip;
    private Button chooseVote;
    private Button chooseCongress;
    private TextView county;
    private TextView state;
    public static String obama = "80";
    public static String romney = "20";
    public static String curr_county = "Santa Clara";
    public static String curr_state = "CA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_menu);
        zip = "95129";
        int i = -1;

        if (getIntent().getExtras() != null) {
            i = getIntent().getExtras().getInt("RANDINDEX");
//            zip = getIntent().getExtras().getString("ZIPCODE");
        }
        Log.d("DistrictMenuActivity", "Received zip = " + zip);
//        if (!zip.equals("95129")) {
//            county = (TextView)findViewById(R.id.county);
//            county.setText("Big Edo County");
//        }

        if (i > -1) {
            zip = "00000";
            county = (TextView)findViewById(R.id.county);
            state = (TextView)findViewById(R.id.state);

            JSONObject county_json = null;
            try {
                county_json = MenuActivity.all_counties.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("county", county_json.toString());
            String county_name = "";
            String county_state = "";
            try {
                county_name = county_json.getString("county-name");
                county_state = county_json.getString("state-postal");
                obama = county_json.getString("obama-percentage");
                romney = county_json.getString("romney-percentage");
                curr_county = county_name;
                curr_state = county_state;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            county.setText(county_name);
            state.setText(county_state);

        }
        chooseVote = (Button)findViewById(R.id.vote_button);
        chooseCongress = (Button)findViewById(R.id.congress_button);
        chooseVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start RepsAtAGlance activity
                Intent i = new Intent(DistrictMenuActivity.this, VoteViewActivity.class);
                i.putExtra("ZIPCODE", zip);
                startActivity(i);
            }
        });
        chooseCongress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start RepsAtAGlance activity
                Intent i = new Intent(DistrictMenuActivity.this, RepsGlanceActivity.class);
                i.putExtra("ZIPCODE", zip);
                startActivity(i);
            }
        });

    }

}
