package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.IntentService;
import android.content.Intent;

import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DeconnecterService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_DECONNECTER = "smartdeals.action.deconnecter";


    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.taucarre.smartdeals.smartdealsapp.services.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.taucarre.smartdeals.smartdealsapp.services.extra.PARAM2";

    public DeconnecterService() {
        super("DeconnecterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DECONNECTER.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            }
        }
    }


    private void handleActionFoo(String param1, String param2) {
        SmartDealsApplication smartDealsApplication = (SmartDealsApplication) getApplication();
        smartDealsApplication.setUserAthentifie(false);
        this.stopSelf();
    }


}
