package com.taucarre.smartdeals.smartdealsapp.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.User;
import com.google.api.client.util.Lists;
import com.taucarre.smartdeals.smartdealsapp.modele.ListeDeal;
import com.taucarre.smartdeals.smartdealsapp.persistence.DbHelper;
import com.taucarre.smartdeals.smartdealsapp.persistence.DealsDataDao;
import com.taucarre.smartdeals.smartdealsapp.persistence.UsersDataDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekelchami on 25/06/14.
 */
public class SmartDealsApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public ArrayList<Deal> deals = Lists.newArrayList();

    private static final String TAG = SmartDealsApplication.class.getSimpleName();
    private static final int TAILLE_LISTE_PAR_DEFAULT = 10;
    public static final long INTERVAL_NEVER = 0;
    private boolean serviceRunning;

    private ListeDeal listeDeal;

    private SharedPreferences prefs;
    private DbHelper dbHelper;
    boolean updaterServiceRunning;
    boolean dataBaseCreated = false;
    int taillListe;

    private boolean userAthentifie = false;
    private List<User> usersAuthentifie;

    private Deal dealAModifier = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);
        taillListe = Integer.valueOf(prefs.getString("taille_liste", "10"));
        this.dbHelper = new DbHelper(this);
        dataBaseCreated = true;
        Log.i(TAG, "Smart Deals application started");

        UsersDataDao usersDataDao = new UsersDataDao(this);
        usersAuthentifie = usersDataDao.getAllUsers();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.dbHelper.close();
        Log.i(TAG, "Smart deals application terminated");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        taillListe = Integer.parseInt(this.prefs.getString("taille_liste",String.valueOf(TAILLE_LISTE_PAR_DEFAULT)));

    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean isUpdaterServiceRunning() {
        return updaterServiceRunning;
    }

    public void setUpdaterServiceRunning(boolean updaterServiceRunning) {
        this.updaterServiceRunning = updaterServiceRunning;
    }


    public ListeDeal getListeDeal() {
        return listeDeal;
    }

    public void setListeDeal(ListeDeal listeDeal) {
        this.listeDeal = listeDeal;
    }

    public boolean isUserAthentifie() {
        return userAthentifie;
    }

    public void setUserAthentifie(boolean userAthentifie) {
        this.userAthentifie = userAthentifie;
    }


    public List<User> getUsersAuthentifie() {
        return usersAuthentifie;
    }

    public void setUsersAuthentifie(List<User> usersAuthentifie) {
        this.usersAuthentifie = usersAuthentifie;
    }

    public int getTaillListe() {
        return taillListe;
    }

    public void setTaillListe(int taillListe) {
        this.taillListe = taillListe;
    }

    public Deal getDealAModifier() {
        return dealAModifier;
    }

    public void setDealAModifier(Deal dealAModifier) {
        this.dealAModifier = dealAModifier;
    }


    public long getInterval() {
        return Long.parseLong(prefs.getString("interval", "0"));
    }

    public boolean isServiceRunning() {
        return serviceRunning;
    }

    public void setServiceRunning(boolean serviceRunning) {
        this.serviceRunning = serviceRunning;
    }

}
