package com.example.michang.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {

    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String SANJOSE = "/95129";
    private static final String SUNLIGHT = "/sunlight";
    private static final String RAWJSON = "/RAWJSON";
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use case

        if( messageEvent.getPath().equalsIgnoreCase( SUNLIGHT ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("message value", value);
            Intent intent = new Intent(this, RepsGlanceActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("SUNLIGHT", value);
            Log.d("watchListener", value);
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase( RAWJSON)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("message value", value);
            Intent intent = new Intent(this, RepsGlanceActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("RAWJSON", value);
            Log.d("watchListener", value);
            startActivity(intent);
        }
//        else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
//        }
        else {
            super.onMessageReceived( messageEvent );
        }
    }

    public WatchListenerService() {
    }

}
