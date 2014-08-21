package com.taucarre.smartdeals.smartdealsapp.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.persistence.DealsDataDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDealsService extends Service{

    SmartDealsApplication smartDealsApplication;

    public UpdateDealsService() {
        super();
        smartDealsApplication = (SmartDealsApplication) getApplication();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Updated Service", "Received start id " + startId + ": " + intent);

         DbWorker thread = new DbWorker();
          while(thread.getNombreUpdate() > 0){
                    thread.run();
            }
             if(thread.getNombreUpdate() == 0){
                    thread.interrupt();
                }

        Toast.makeText(getApplicationContext(), "updater service finished", Toast.LENGTH_LONG).show();

        this.stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "Updater service stoped", Toast.LENGTH_SHORT).show();
    }



    class DbWorker extends Thread{

        Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();
        private List<Deal> listeDealsARemplir;
        DealsDataDao dealsDataDao;
        private int nombreUpdate = 0;

        public DbWorker(){
            dealsDataDao = new DealsDataDao(smartDealsApplication);
            setName("update db worker");
        }


        public int getNombreUpdate() {
            return nombreUpdate;
        }

        public synchronized void setNombreUpdate(int nombreUpdate) {
            this.nombreUpdate = nombreUpdate;
        }

        @Override
        public void run() {
            super.run();

            try {
                Smartdeals.ListDeals listeDealCommand = apiServiceHandle.listDeals();
                DealCollection dealCollection = listeDealCommand.execute();

                List<Deal> listeItems = dealCollection.getItems();

                if(listeItems !=null && !listeItems.isEmpty()) {
                    for(Deal deal : listeDealsARemplir) {
                        dealsDataDao.insertOrIgnoreDeal(deal);
                        this.setNombreUpdate(this.getNombreUpdate()-1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
