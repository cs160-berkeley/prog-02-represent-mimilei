package com.example.michang.represent;

import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CoverScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button zipcodeButton;
    private Button currentLoc;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView tvLatlong;
    String congress_lookup_url = "";
    public static String congress_lookup; //Result returned from calling sunlight url
    public static JSONArray congress_info_json;
    ArrayList<JSONObject> congress = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_screen);
        //For google maps api
//        tvLatlong = (TextView) findViewById(R.id.lat_long);

        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
        }
        //End for google maps api

        zipcodeButton = (Button) findViewById(R.id.zipcode_button);
        zipcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Inovked when zipcode button clicked
                //Start RepsAtAGlance activity
                Intent i = new Intent(CoverScreenActivity.this, RepsAtAGlance.class);
                startActivity(i);
                Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);
                EditText zip = (EditText) findViewById(R.id.zipcode_input);

                //Create url for zipcode
                congress_lookup_url = getString(R.string.find_congress_stub);
                congress_lookup_url = congress_lookup_url.concat("zip=");
                congress_lookup_url = congress_lookup_url.concat(zip.getText().toString());
                congress_lookup_url = congress_lookup_url.concat("&");
                congress_lookup_url = congress_lookup_url.concat(getString(R.string.api_key));
                Log.d("zipcode", congress_lookup_url);
                new HttpAsyncTask().execute(congress_lookup_url);
                int c = 0;
                while (c < 100 || congress_lookup == null) {
                    c++;
                }
                if (congress_lookup == null) {
                    Log.d("zipcode", "congress_lookup is null");
                    Log.d("zipcode: cong_lookup", congress_lookup);
                }
                //Parse JSON for url
                try {
                    JSONObject result = (JSONObject) new JSONTokener(congress_lookup).nextValue();
                    congress_info_json = result.getJSONArray("results");
                    Log.d("zipcode: json arr", congress_info_json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                toWatch.putExtra("SUNLIGHT", congress_lookup_url);
                toWatch.putExtra("RAWJSON", congress_lookup);
//                toWatch.putExtra("ZIPCODE", zip.getText().toString());
                startService(toWatch);
            }
        });

        currentLoc = (Button) findViewById(R.id.location_button);
        currentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current location
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if (mLastLocation != null) {
                //If location is not null, create url based on location
//                    tvLatlong.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
//                            String.valueOf(mLastLocation.getLongitude()));
                    Toast.makeText(getBaseContext(), "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
                            String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
                    String latitude = "latitude=".concat(String.valueOf(mLastLocation.getLatitude()));
                    String longitude = "longitude=".concat(String.valueOf(mLastLocation.getLongitude()));
                    congress_lookup_url = getString(R.string.find_congress_stub);
                    congress_lookup_url = congress_lookup_url.concat(latitude);
                    congress_lookup_url = congress_lookup_url.concat("&");
                    congress_lookup_url = congress_lookup_url.concat(longitude);
                    congress_lookup_url = congress_lookup_url.concat("&");
                    congress_lookup_url = congress_lookup_url.concat(getString(R.string.api_key));
                    Log.d("Request URL", congress_lookup_url);
                } else {
                    Log.d("coverScreenActivity", "mLastLocation is null");
                }

                Log.d("Now requesting", congress_lookup_url);
                new HttpAsyncTask().execute(congress_lookup_url);
                Log.d("currLoc", "finished executing HttpAsyncTask obj");
                int c = 0;
                while (c < 100 || congress_lookup == null) {
                    c++;
                }
                if (congress_lookup == null) {
                    Log.d("zipcode", "congress_lookup is null");
                    Log.d("zipcode: cong_lookup", congress_lookup);
                }

                //JSON Parsing of url results
                try {
                    //Turn string into json array for easy access
                    if (congress_lookup == null) {
                        Log.d("currLoc", "congress_lookup is null");
                    } else {
                        Log.d("currLoc", "congress_lookup isn't null");
                        Log.d("congress_lookup", congress_lookup.toString());
                    }
                    JSONObject jsnobject = new JSONObject(congress_lookup);
                    congress_info_json = jsnobject.getJSONArray("results");
                    for (int i = 0; i < congress_info_json.length(); i++) {
                        congress.add(congress_info_json.getJSONObject(i));
                    }
                    Log.d("currLoc", "added reps to congress arrList");
                    Log.d("congress_info_json", congress_info_json.toString());
                    Log.d("congress", congress.toString());
                    System.out.println(congress_info_json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Start RepsAtAGlance
                Intent i = new Intent(CoverScreenActivity.this, RepsAtAGlance.class);
                startActivity(i);
                Intent toWatch = new Intent(getBaseContext(), PhoneToWatchService.class);

//                toWatch.putExtra("SUNLIGHT", congress_lookup_url);
                toWatch.putExtra("RAWJSON", congress_lookup);
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
//        Log.d("response", GET(getString(R.string.congress_home)));
//        if (isConnected()) {
//            Log.d("onCreate", "is connected");
//        } else {
//            Log.d("onCreate", "not connected");
//        }
//        new HttpAsyncTask().execute(congress_lookup_url);
//        new HttpAsyncTask().execute(getString(R.string.congress_home));
//        new HttpAsyncTask().execute("http://congress.api.sunlightfoundation.com/legislators/locate?latitude=37.338&longitude=-121.886&apikey=89dfa91887d84874918993d2fd56e6af");
//        Log.d("end of onCreate", "After issuing get request");
    }

    //CUSTOM CODE FOR GOOGLE MAPS API COURTESTY OF http://hmkcode.com/android-get-last-location/
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.d("coverScreenActivity", "in onConnect");
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    //END CUSTOM CODE FOR GOOGLE MAPS API

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

    //Networking

    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static String GET(String urls){
        Log.d("GET requesting url", urls);
        InputStream inputStream = null;
        String result = "";
        try {
            URL urlObj = new URL(urls);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            Log.d("GET resp code", Integer.toString(urlConnection.getResponseCode()));
            try {
                Log.d("GET", "inside second try of GET");
                Log.d("GET", "AHHHHHHHHHHH1");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //IOException thrown on this line
                Log.d("GET", "AHHHHHHHHHHH2");
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                    Log.d("GET", "A buffered reader line wasn't null");
                }
                bufferedReader.close();
                if (stringBuilder == null) {
                    Log.d("GET", "stringBuilder is null");
                }
                Log.d("GET", stringBuilder.toString());
                congress_lookup = stringBuilder.toString();
                if (congress_lookup == null) {
                    Log.e("NULL ISSUE", "congress_lookup is null");
                }
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }

        } catch (IOException e) {
            Log.e("GET", "ioexception");
            return "Unable to retrieve web page. URL may be invalid.";
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

//    // convert inputstream to String
//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }

    //End Networking

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d("httpAsyncTask url", urls[0]);

            //Trying something new--please work

            try {
                URL toSunlight = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) toSunlight.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.d("httpasynctask", stringBuilder.toString());
                    congress_lookup = stringBuilder.toString();
                    Log.d("httpasynctask", congress_lookup);
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

//            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                result = "THERE WAS AN ERROR";
            }
//            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
//            getResponse.setText(result);
            Log.d("GET response", result);
            congress_lookup = result;
        }
    }
}

