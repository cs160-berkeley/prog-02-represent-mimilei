package com.example.michang.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ZipcodeActivity extends Activity {

    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zipcode);
        go = (Button)findViewById(R.id.enter_zipcode_button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start RepsAtAGlance activity
                Intent i = new Intent(ZipcodeActivity.this, DistrictMenuActivity.class);
                EditText zip = (EditText)findViewById(R.id.enter_zipcode);
                i.putExtra("ZIPCODE", zip.getText().toString());
                startActivity(i);
            }
        });
    }

}
