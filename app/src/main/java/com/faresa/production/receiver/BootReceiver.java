package com.faresa.production.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("bootreceiver", "boot completed");
        /*Intent i = new Intent(context,AlarmReceiver.class);
        context.startService(i);*/
        AlarmReceiver receiver = new AlarmReceiver();
        try {
            receiver.registerNotification(context);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
