package com.example.michang.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CoverScreenActivity extends Activity {
    //This view is stylized to look like my cover screen
    private TextView mTextView;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_cover_screen);
        Log.d("watch_status", "getting intent");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            startActivity(intent);
            Log.d("watch_status", "starting received activity");
        }

        logo = (ImageView)findViewById(R.id.logo_color);
        if (logo != null) {
            Log.d("w:CoverScreenActivity", "logo is not null");
        } else {
            Log.d("w:CoverScreenActivity", "logo is null");
        }
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CoverScreenActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });


//        //Default stuff. Delete later?
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });
    }
}
