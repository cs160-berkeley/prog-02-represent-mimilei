package com.example.michang.represent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GeneralDetail extends AppCompatActivity {
    private String name = "";
    private String email;
    private String title;
    private String party;
    private String eot; //end of term
    private ArrayList<String> committees;
    private HashMap<String, String> bills;
    private String holder = null;
    private JSONArray committees_json_array;
    private JSONArray bills_json_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_detail);
        RelativeLayout frame = (RelativeLayout)findViewById(R.id.general_detail_frame);
        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("index");
        Log.d("index to genDetail", Integer.toString(index));
        JSONObject currRep = null;
        try {
            currRep = CoverScreenActivity.congress_info_json.getJSONObject(index);
            Log.d("currRep", currRep.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = name.concat(currRep.getString("first_name"));
            name = name.concat(" ");
            name = name.concat(currRep.getString("last_name"));
            party = currRep.getString("party");
            title = currRep.getString("title");
            email = currRep.getString("oc_email");
            eot = currRep.getString("term_end"); //TODO: Could parse to make eot less ugly
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //set view layout to correct party
        RelativeLayout detailLayout;
        if (party.equals("D")) {
            detailLayout = (RelativeLayout) View.inflate(GeneralDetail.this, R.layout.detail_demo, null);
        } else if (party.equals("R")) {
            detailLayout = (RelativeLayout) View.inflate(GeneralDetail.this, R.layout.detail_repub, null);
        } else { //you must be independent
            detailLayout = (RelativeLayout) View.inflate(GeneralDetail.this, R.layout.detail_indep, null);
        }

        //Set basic views
        TextView repName = (TextView)detailLayout.findViewById(R.id.name);
        TextView repTitle = (TextView)detailLayout.findViewById(R.id.title);
        TextView repEmail = (TextView)detailLayout.findViewById(R.id.email);
        TextView endOfTerm = (TextView)detailLayout.findViewById(R.id.eot);

        repName.setText(name);
        if (title.equals("Sen")) {
            repTitle.setText("Senator");
        } else {
            repTitle.setText("Representative");
        }
        repEmail.setText(email);
        endOfTerm.setText(eot);

        //Get Committees ==========================================
        String bioguide = "";
        try {
            bioguide = currRep.getString("bioguide_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Construct url
        String committee_url = getString(R.string.find_committees_stub);
        committee_url = committee_url.concat(bioguide);
        committee_url = committee_url.concat("&");
        committee_url = committee_url.concat(getString(R.string.api_key));

        Log.d("Now requesting", committee_url);
        new HttpAsyncTask().execute(committee_url);
        Log.d("currLoc", "finished executing HttpAsyncTask obj");
        int c = 0;
        while (c < 100 || holder == null) {
            c++;
        }
        if (holder == null) {
            Log.d("committees", "committees string is null");
        }
        try {
            //Turn string into json array for easy access
            Log.d("committees", holder);
            JSONObject jsnobject = new JSONObject(holder);
            committees_json_array = jsnobject.getJSONArray("results");
            Log.d("committees_json_array", committees_json_array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout committee_layout = (LinearLayout)detailLayout.findViewById(R.id.committees);
        for(int i=0; i < committees_json_array.length(); i++) {
            TextView comm = new TextView(this);
            JSONObject comm_obj = null;
            try {
                comm_obj = committees_json_array.getJSONObject(i);
                comm.setText(comm_obj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            comm.setPadding(5, 5, 5, 5);
            comm.setTextColor(getResources().getColor(R.color.offWhite));
            committee_layout.addView(comm);
        }

        //TODO: Get Bills ==========================================
        //Construct url
        String bills_url = getString(R.string.find_bills_stub);
        bills_url = bills_url.concat(bioguide);
        bills_url = bills_url.concat("&");
        bills_url = bills_url.concat(getString(R.string.api_key));

        Log.d("Now requesting", bills_url);
        new HttpAsyncTask().execute(bills_url);
        Log.d("currLoc", "finished executing HttpAsyncTask obj");
        int x = 0;
        holder = null;
        while (x < 100 || holder == null) {
            x++;
        }
        if (holder == null) {
            Log.d("bills", "bills string is null");
        }
        try {
            //Turn string into json array for easy access
            Log.d("bills", holder);
            JSONObject jsnobject = new JSONObject(holder);
            bills_json_array = jsnobject.getJSONArray("results");
            Log.d("bills_json_array", committees_json_array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout bills_layout = (LinearLayout)detailLayout.findViewById(R.id.bills);
        for(int i=0; i < bills_json_array.length(); i++) {
            TextView billDate = new TextView(this);
            TextView bill = new TextView(this);
            JSONObject bills_obj = null;
            try {
                bills_obj = bills_json_array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                bill.setText(bills_obj.getString("official_title"));
                billDate.setText(bills_obj.getString("introduced_on"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bill.setPadding(5, 5, 5, 8);
            bill.setTextColor(getResources().getColor(R.color.offWhite));
            billDate.setPadding(5, 5, 5, 5);
            billDate.setTextColor(getResources().getColor(R.color.offWhite));
            billDate.setTextSize(14);

            bills_layout.addView(billDate);
            bills_layout.addView(bill);
        }
        ImageView img = (ImageView)detailLayout.findViewById(R.id.pic);
        String img_url = getString(R.string.img_stub);
        img_url = img_url.concat(bioguide);
        img_url = img_url.concat(".jpg");
        Picasso.with(GeneralDetail.this).load(img_url).resize(530,655).into(img);

        //Add layout to frame
        frame.addView(detailLayout);
    }

    //Api fetch class
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
                    holder = stringBuilder.toString();
                    Log.d("httpasynctask", holder);
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
            holder = result;
        }
    }

}
