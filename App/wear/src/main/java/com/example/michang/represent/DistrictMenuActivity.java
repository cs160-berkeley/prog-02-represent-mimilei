package com.example.michang.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DistrictMenuActivity extends Activity {

    private String zip;
    private Button chooseVote;
    private Button chooseCongress;
    private TextView county;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_menu);
        zip = "95129";
        if (getIntent().getExtras() != null) {
            zip = getIntent().getExtras().getString("ZIPCODE");
        }
        Log.d("DistrictMenuActivity", "Received zip = " + zip);
        if (!zip.equals("95129")) {
            county = (TextView)findViewById(R.id.county);
            county.setText("Big Edo County");
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
