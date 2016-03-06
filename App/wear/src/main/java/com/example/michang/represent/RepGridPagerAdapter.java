package com.example.michang.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;

import java.util.List;

/**
 * Created by michang on 3/3/16.
 */
public class RepGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final float MAXIMUM_CARD_EXPANSION_FACTOR = 1.0f;

    private final Context mContext;
    private List<RepsGlanceActivity.RepList> repData;
    private Drawable mBackground;

    public RepGridPagerAdapter(Context context, FragmentManager fm, List<RepsGlanceActivity.RepList> repLists) {
        super(fm);
        mContext = context;
        repData = repLists;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        RepsGlanceActivity.RepList rep = repData.get(row);

        RepFragment fragment = RepFragment.newInstance(rep.getName(), rep.getParty(), rep.getPortrait());
        final String r = rep.getReptype();
        fragment.setCardGravity(Gravity.BOTTOM);
        fragment.setContentPadding(5, 5, 5, 5);
        fragment.setExpansionEnabled(false);
        fragment.setReptype(r);
        fragment.setmContext(mContext);

//        fragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                //do stuff when clicked
//                //Call watchToPhone
//                //Send message to phone with path /repList.getName()
//                Intent openPhoneDetail = new Intent(mContext, WatchToPhoneService.class);
//                openPhoneDetail.putExtra("REP_TYPE", r);
////                startService(openPhoneDetail);
//            }
//        });
        return fragment;
    }

    @Override
    public int getRowCount() {
        return repData.size();
    }

    @Override
    public int getColumnCount(int row) {
        return 1;
    }

//    @Override
//    public Drawable getBackgroundForRow(int row) {
//        return mContext.getResources().getDrawable(repData.get(row).getPortrait());
//    }
}
