package com.example.michang.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

public class MenuActivity extends Activity{
    private Button zip;
    private Button curr_loc;
    private Button shake;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String congress_lookup_url = "";
    public static String congress_lookup; //Result returned from calling sunlight url
    public static JSONArray congress_info_json;
    public static String all_counties_str;
    public static JSONArray all_counties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        try {
            InputStream is = getAssets().open("votingData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            all_counties_str = new String(buffer, "UTF-8");
            Log.d("counties", all_counties_str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject temp = new JSONObject(all_counties_str);
            all_counties = temp.getJSONArray("results");
            Log.d("counties arr", all_counties.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        Log.d("menuActivity", "setting onShakeListener");
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Toast.makeText(MenuActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MenuActivity.this, DistrictMenuActivity.class);

                int total = all_counties.length();
                int randIndex = ThreadLocalRandom.current().nextInt(0,total);

//                int rand_zip = 90000;
//                i.putExtra("ZIPCODE", Integer.toString(rand_zip));
                i.putExtra("RANDINDEX", randIndex);
                startActivity(i);
            }
        });

        zip = (Button)findViewById(R.id.choose_zip);
        curr_loc = (Button)findViewById(R.id.choose_loc);
        shake = (Button)findViewById(R.id.choose_shake);

        zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ZipcodeActivity.class);
                startActivity(i);
            }
        });

        curr_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MenuActivity.this, DistrictMenuActivity.class);
                startActivity(i);

            }
        });

        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ShakeInfoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
