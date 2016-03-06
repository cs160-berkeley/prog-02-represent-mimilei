package com.example.michang.represent;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RepsGlanceActivity extends Activity {

    private TextView mTextView;
    private List<RepList> reps = new ArrayList<RepList>();

    private String houseName = "Michael M. Honda";
    private String senator1Name = "Sarah Palin";
    private String senator2Name = "Ma Ying-jeou";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps_glance);
        setupData();
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

}


