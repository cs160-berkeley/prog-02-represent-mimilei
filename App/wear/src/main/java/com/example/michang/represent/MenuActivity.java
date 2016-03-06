package com.example.michang.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {
    private Button zip;
    private Button curr_loc;
    private Button shake;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        Log.d("menuActivity", "setting onShakeListener");
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                Toast.makeText(MenuActivity.this, "Shake!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MenuActivity.this, DistrictMenuActivity.class);
                int rand_zip = 90000;
                i.putExtra("ZIPCODE", Integer.toString(rand_zip));
                startActivity(i);
            }
        });

//        Intent shakeIntent = new Intent(MenuActivity.this, ShakeEventListener.class);
//        startService(shakeIntent);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
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
