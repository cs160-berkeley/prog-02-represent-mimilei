package com.example.michang.represent;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepsGlanceActivity extends Activity {

    private TextView mTextView;
    private List<RepList> reps = new ArrayList<RepList>();

    private String houseName = "Michael M. Honda";
    private String senator1Name = "Sarah Palin";
    private String senator2Name = "Ma Ying-jeou";
    public static String sunlight_url;
    private String holder = null;
    private JSONArray reps_json_array = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps_glance);

        JSONObject jsnobject = null;
        try {
            jsnobject = new JSONObject(getIntent().getExtras().getString("RAWJSON"));
            reps_json_array = jsnobject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

            Log.d("watch json_array", reps_json_array.toString());
        for(int i = 0; i < reps_json_array.length(); i++) {
            JSONObject one_rep = null;
            try {
                one_rep = reps_json_array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String name = "";
            String party = "";
            try {
                name = one_rep.getString("first_name");
                name = name.concat(" ");
                name = name.concat(one_rep.getString("last_name"));

                party = one_rep.getString("party");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (party.equals("D")) {
                party = "Democrat";
            } else if (party.equals("R")) {
                party = "Republican";
            } else {
                party = "Independent";
            }
            reps.add(new RepList(name, R.drawable.honda500x250, R.drawable.badge_demo, party, "house"));
        }
//        setupData();
        setupGridViewPager();
    }

    private void setupData() {
        String z = getIntent().getExtras().getString("ZIPCODE");
        if (z.equals("90000")){
            houseName = "Sakata Gintoki";
            senator1Name = "Katsura Koutarou";
            senator2Name = "Sakamoto Tatsuma";

        }
        reps.add(new RepList(houseName, R.drawable.honda500x250, R.drawable.badge_demo, "Democrat", "house"));
        reps.add(new RepList(senator1Name, R.drawable.palin, R.drawable.badge_repub, "Republican", "senator1"));
        reps.add(new RepList(senator2Name, R.drawable.mayingjeou, R.drawable.badge_indep, "Independent", "senator2"));
    }

    private void setupGridViewPager() {
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new RepGridPagerAdapter(this, getFragmentManager(), reps));
    }

    public static class RepList {
        private int portrait;
        private int badge;
        private String party;
        private String name;
        private String reptype;

        public RepList(String n, int pic, int b, String p, String rept) {
            name = n;
            portrait = pic;
            badge = b;
            party = p;
            reptype = rept;
        }

        public String getName() {
            return name;
        }

        public int getPortrait() {
            return portrait;
        }

        public int getBadge() {
            return badge;
        }

        public String getParty() {
            return party;
        }

        public String getReptype() {
            return reptype;
        }
    }

    //Api fetch class
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d("httpAsyncTask url", urls[0]);
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
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                result = "THERE WAS AN ERROR";
            }
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            Log.d("GET response", result);
            holder = result;
        }
    }


}


