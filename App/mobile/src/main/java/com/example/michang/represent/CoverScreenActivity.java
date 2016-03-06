package com.example.michang.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CoverScreenActivity extends AppCompatActivity {

    private Button zipcodeButton;
    private Button currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_screen);
        zipcodeButton = (Button)findViewById(R.id.zipcode_button);
        zipcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start RepsAtAGlance activity
                Intent i = new Intent(CoverScreenActivity.this, RepsAtAGlance.class);
                startActivity(i);
                Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
                EditText zip = (EditText)findViewById(R.id.zipcode_input);
                toWatch.putExtra("ZIPCODE", zip.getText().toString());
                startService(toWatch);
            }
        });

        currentLoc = (Button) findViewById(R.id.location_button);
        currentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start RepsAtAGlance
                Intent i = new Intent(CoverScreenActivity.this, RepsAtAGlance.class);
                startActivity(i);
                Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
//                EditText zip = (EditText)findViewById(R.id.zipcode_input);
                toWatch.putExtra("ZIPCODE", "95129");
                startService(toWatch);
                Log.d("CoverScreenActivity", "started PhoneToWatchService from current location");
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            startActivity(intent);
            Log.d("T", "received a message, starting activity");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cover_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
