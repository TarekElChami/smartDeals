package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;

import java.io.IOException;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdaterService extends IntentService {

    private static final String ACTION_UPDATE = "smartdeals.action.update";
    private static final String ACTION_SYNCHRONIZE = "com.taucarre.smartdeals.smartdealsapp.services.action.synchronize";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.taucarre.smartdeals.smartdealsapp.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.taucarre.smartdeals.smartdealsapp.services.extra.PARAM2";

    /**
     * Starts this service to perform action update with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdate(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdaterService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action synchronize with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSynchronize(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdaterService.class);
        intent.setAction(ACTION_SYNCHRONIZE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public UpdaterService() {
        super("UpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionUpdate(param1, param2);
            } else if (ACTION_SYNCHRONIZE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSynchronize(param1, param2);
            }
        }
    }

    /**
     * Handle action Update in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate(String param1, String param2) {
        Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

        /*
        try {
            Smartdeals.ListDeals listeDealCommand = apiServiceHandle.listDeals();
            DealCollection dealCollection = listeDealCommand.execute();

            List<Deal> listeItems = dealCollection.getItems();


        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        Toast.makeText(getApplicationContext(),"update service started", Toast.LENGTH_LONG).show();

    }

    /**
     * Handle action synchronize in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSynchronize(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
