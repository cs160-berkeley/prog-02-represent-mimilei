package com.example.michang.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class PhoneListenerService extends WearableListenerService {
//    public PhoneListenerService() {
//    }

    private static final String HOUSE = "/house";
    private static final String SENATOR1 = "/senator1";
    private static final String SENATOR2 = "/senator2";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());

        if (messageEvent.getPath().equalsIgnoreCase(HOUSE)) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, HouseDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("T", "about to start phone HouseDetailActivity");
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase(SENATOR1)) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Senator1DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("T", "about to start phone Senator1DetailActivity");
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase(SENATOR2)) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Senator2DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("T", "about to start phone Senator2DetailActivity");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
