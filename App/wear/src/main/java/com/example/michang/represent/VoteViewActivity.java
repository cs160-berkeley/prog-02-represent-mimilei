package com.example.michang.represent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class VoteViewActivity extends Activity {

    private TextView mTextView;
    private String zip;
    private TextView obama;
    private TextView romney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_vote_view);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });

        obama = (TextView)findViewById(R.id.obamaPercent);
        romney = (TextView)findViewById(R.id.romneyPercent);

        zip = getIntent().getExtras().getString("ZIPCODE");
        Log.d("VoteViewActivity", "got zip = " + zip);
        if (!zip.equals("95129")) {
            obama.setText("80%");
            romney.setText("10%");
        }
        if (zip.equals("90000")) {
            obama.setText("100%");
            romney.setText("0%");
        }
    }
}
