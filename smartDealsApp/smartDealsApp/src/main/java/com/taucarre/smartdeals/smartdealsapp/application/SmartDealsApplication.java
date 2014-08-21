package com.taucarre.smartdeals.smartdealsapp.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.google.api.client.util.Lists;
import com.taucarre.smartdeals.smartdealsapp.persistence.DbHelper;

import java.util.ArrayList;

/**
 * Created by tarekelchami on 25/06/14.
 */
public class SmartDealsApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public ArrayList<Deal> deals = Lists.newArrayList();

    private static final String TAG = SmartDealsApplication.class.getSimpleName();
    private static final int TAILLE_LISTE_PAR_DEFAULT = 10;
    private static final int FREQUENCE_UPDATE_PAR_DEFAULT = 30 * 60 * 1000;

    private SharedPreferences prefs;
    private DbHelper dbHelper;
    boolean updaterServiceRunning;
    int taillListe;
    int frequenceUpdate;

    @Override
    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);
        //String taillListe = this.prefs.getString("taille_liste",String.valueOf(TAILLE_LISTE_PAR_DEFAULT));
        //String frequenceUpdate = this.prefs.getString("frequence_update",String.valueOf(FREQUENCE_UPDATE_PAR_DEFAULT));
        this.dbHelper = new DbHelper(this);
        Log.i(TAG, "Smart Deals application started");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.dbHelper.close();
        Log.i(TAG, "Smart deals application terminated");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //String taillListe = this.prefs.getString("taille_liste","");
        //String frequenceUpdate = this.prefs.getString("frequence_update","");

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




}