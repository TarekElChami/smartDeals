package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.appspot.smart_deals.smartdeals.model.User;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.persistence.DbHelper;
import com.taucarre.smartdeals.smartdealsapp.persistence.DealsDataDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdaterService extends IntentService {

    SmartDealsApplication smartDealsApplication;

    private static final String ACTION_UPDATE = "smartdeals.action.update";
    private static final String ACTION_SYNCHRONIZE = "smartdeals.action.synchronize";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "smartdeals.synchronize.user";
    private static final String EXTRA_PARAM2 = "com.taucarre.smartdeals.smartdealsapp.services.extra.PARAM2";

    @Override
    public void onCreate() {
        super.onCreate();
        smartDealsApplication = (SmartDealsApplication) getApplication();
    }

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

        try {
            Smartdeals.ListDeals listeDealCommand = apiServiceHandle.listDeals();
            DealCollection dealCollection = listeDealCommand.execute();

            List<Deal> listeItems = dealCollection.getItems();

            if(listeItems !=null && !listeItems.isEmpty()){
                DealsDataDao dealsDataDao = new DealsDataDao(smartDealsApplication);
                for(Deal deal : listeItems) {
                    dealsDataDao.insertOrIgnoreDeal(deal);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stopSelf();

    }

    /**
     * Handle action synchronize in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSynchronize(String param1, String param2) {
        try {
            if (smartDealsApplication.getUsersAuthentifie() != null &&
                    !smartDealsApplication.getUsersAuthentifie().isEmpty()) {
                Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();
                for(User user : smartDealsApplication.getUsersAuthentifie()) {
                    Smartdeals.InsertUser insertUserCommand = apiServiceHandle.insertUser(user);
                    insertUserCommand.execute();
                }
            }}catch(IOException e){
                e.printStackTrace();
            }
        }


    class DbWorker extends Thread{

        private List<Deal> listeDealsARemplir;
        DealsDataDao dealsDataDao;
        int nombreUpdate = 0;

        public DbWorker(){
            dealsDataDao = new DealsDataDao(smartDealsApplication);
            listeDealsARemplir = new ArrayList<Deal>();
            setName("update db worker");
        }

        public DbWorker(List<Deal> listeDeals){
            dealsDataDao = new DealsDataDao(smartDealsApplication);
            setName("update db worker");
            listeDealsARemplir = listeDeals;
            nombreUpdate = listeDealsARemplir.size();
        }

        @Override
        public void run() {
            super.run();
            for(Deal deal : listeDealsARemplir) {
                dealsDataDao.insertOrIgnoreDeal(deal);
                nombreUpdate--;
            }
        }
    }
}
