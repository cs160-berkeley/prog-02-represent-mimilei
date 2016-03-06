package com.example.michang.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RepsAtAGlance extends AppCompatActivity {

    private Button see_more_house;
    private Button see_more_senator1;
    private Button see_more_senator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("T", "in RepsAtAGlance");
        setContentView(R.layout.activity_reps_at_aglance);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            startActivity(intent);
            Log.d("T", "received a message, starting activity");
        }

        see_more_house = (Button)findViewById(R.id.seeMore_house);
        see_more_senator1 = (Button)findViewById(R.id.seeMore_senator1);
        see_more_senator2 = (Button)findViewById(R.id.seeMore_senator2);

        see_more_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RepsAtAGlance.this, HouseDetailActivity.class);
                startActivity(i);
            }
        });

        see_more_senator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RepsAtAGlance.this, Senator1DetailActivity.class);
                startActivity(i);
            }
        });

        see_more_senator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RepsAtAGlance.this, Senator2DetailActivity.class);
                startActivity(i);
            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
