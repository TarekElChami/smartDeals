package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;

/**
 * @author tarekelchami
 */
public class BootReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent callingIntent) {

    long interval = ((SmartDealsApplication) context.getApplicationContext())
        .getInterval();
    if (interval == SmartDealsApplication.INTERVAL_NEVER)
      return;

    Intent updaterCommentaireIntent = new Intent(context, UpdaterService.class);
    updaterCommentaireIntent.setAction("smartdeals.action.updatecommentaire");
    PendingIntent pendingIntent = PendingIntent.getService(context, -1, updaterCommentaireIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);

    Intent updaterDealsIntent = new Intent(context, UpdaterService.class);
    updaterDealsIntent.setAction("smartdeals.action.update");
    PendingIntent pendingDealsIntent = PendingIntent.getService(context, -1, updaterDealsIntent,
              PendingIntent.FLAG_UPDATE_CURRENT);


    AlarmManager alarmManager = (AlarmManager) context
        .getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System
        .currentTimeMillis(), interval, pendingIntent);
    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System
              .currentTimeMillis(), interval, pendingDealsIntent);

    Log.d("BootReceiver", "onReceived");
  }

}
