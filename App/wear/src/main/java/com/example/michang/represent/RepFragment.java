package com.example.michang.represent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RepFragment extends CardFragment {
    private View fragmentView;
    private View.OnClickListener listener;
    private String reptype;
    private Context mContext;

    public void setReptype(String n) {
        reptype = n;
    }

    public void setmContext(Context m) {
        mContext = m;
    }

    static RepFragment newInstance(String title, String description, int pic) {
        RepFragment f = new RepFragment();

        Bundle args = new Bundle();
        args.putString("name", title);
        args.putString("party", description);
        args.putInt("portrait", pic);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateContentView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_rep, container, false);
        TextView person = (TextView) fragmentView.findViewById(R.id.rep_name);
        person.setText(getArguments().getString("name"));
        TextView party = (TextView) fragmentView.findViewById(R.id.rep_party);
        party.setText(getArguments().getString("party"));
        fragmentView.setOnClickListener(new View.OnClickListener() {
            TextView text_name = new TextView(getActivity());

            @Override
            public void onClick(final View view) {
                Intent openPhoneDetail = new Intent(mContext, WatchToPhoneService.class);
                openPhoneDetail.putExtra("REP_TYPE", reptype);
                mContext.startService(openPhoneDetail);
            }
        });
        return fragmentView;
    }

    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;
    }
}
