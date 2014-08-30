package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Comment;
import com.appspot.smart_deals.smartdeals.model.CommentCollection;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.appspot.smart_deals.smartdeals.model.User;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.activities.ListeDealsActivity;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.persistence.CommentairesDataDao;
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

    private static final String TAG = UpdaterService.class.getSimpleName();
    SmartDealsApplication smartDealsApplication;

    public static final String ACTION_UPDATE = "smartdeals.action.update";
    public static final String ACTION_SYNCHRONIZE = "smartdeals.action.synchronize";
    public static final String ACTION_UPDATE_COMMENTAIRES = "smartdeals.action.updatecommentaire";

    private static final String EXTRA_PARAM1 = "smartdeals.synchronize.user";
    private static final String EXTRA_PARAM2 = "smartdeals.update.iddeal";

    public static final String NEW_COMMENTS_INTENT = "smartdeals.update.comments";
    public static final String RECEIVE_COMMENTS_BROADCAST = "new.comments.received";

    public static final String NEW_DEALS_INTENT = "smartdeals.update.deals";
    public static final String RECEIVE_DEALS_BROADCAST = "new.deals.received";

    public static final String RECEIVE_DEALS_NOTIFICATION = "com.taucarre.smartdeals.RECEIVE_DEALS_NOTIFICATIONS";

    private NotificationManager notificationManager;
    private Notification notification;

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
                handleActionUpdate(param1, null);
            } else if (ACTION_SYNCHRONIZE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionSynchronize(param1, null);
            }else if(ACTION_UPDATE_COMMENTAIRES.equals(action)){
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final Long param2 = intent.getLongExtra(EXTRA_PARAM2, new Long(0));
                handleActionUpdateCommentaires(param1, param2);
            }
        }
    }

    /**
     * Handle action Update in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate(String param1, String param2) {
        Log.d(TAG, "handle action update deals");
        Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();
        DealsDataDao dealsDataDao = new DealsDataDao(smartDealsApplication);
        int nombreDealsRecuperes = 0;

        int nombreDealsEnbase = dealsDataDao.getDealsNumber();

        try {
            Smartdeals.ListDeals listeDealCommand = apiServiceHandle.listDeals();
            DealCollection dealCollection = listeDealCommand.execute();

            List<Deal> listeItems = dealCollection.getItems();

            if(listeItems !=null && !listeItems.isEmpty()){
                nombreDealsRecuperes = listeItems.size();
                dealsDataDao.delete();
                for(Deal deal : listeItems) {
                    dealsDataDao.insertOrIgnoreDeal(deal);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(NEW_DEALS_INTENT);
        sendBroadcast(intent, RECEIVE_DEALS_NOTIFICATION);

        if(nombreDealsRecuperes > nombreDealsEnbase){
            this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            this.notification = new Notification(android.R.drawable.stat_notify_chat,
                    "", 0);
            sendNotification(nombreDealsRecuperes - nombreDealsEnbase);
        }

    }

    /**
     * Handle action synchronize in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSynchronize(String param1, String param2) {
        Log.d(TAG, "handle action synchronize users");
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

    private void handleActionUpdateCommentaires(String param1, Long param2) {
        Log.d(TAG, "handle action update commentaires");

        Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

        try {

            Smartdeals.FetchComments fetchComments = apiServiceHandle.fetchComments();
            CommentCollection collection = fetchComments.execute();

            List<Comment> listeItems = collection.getItems();

            if(listeItems !=null && !listeItems.isEmpty()){
                CommentairesDataDao dao = new CommentairesDataDao(smartDealsApplication);
                for(Comment c : listeItems) {
                    dao.insertOrIgnoreCommmentaire(c);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(NEW_COMMENTS_INTENT);
        sendBroadcast(intent, RECEIVE_DEALS_NOTIFICATION);

    }



    private void sendNotification(int nombreDeals) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, -1,
                new Intent(this, ListeDealsActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        this.notification.when = System.currentTimeMillis();
        this.notification.flags |= Notification.FLAG_AUTO_CANCEL;
        CharSequence notificationTitle = this
                .getText(R.string.msgNotificationTitle);
        CharSequence notificationSummary = this.getString(
                R.string.msgNotificationMessage, nombreDeals);
        this.notification.setLatestEventInfo(this, notificationTitle,
                notificationSummary, pendingIntent);
        this.notificationManager.notify(0, this.notification);
        Log.d(TAG, "send notification");
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
