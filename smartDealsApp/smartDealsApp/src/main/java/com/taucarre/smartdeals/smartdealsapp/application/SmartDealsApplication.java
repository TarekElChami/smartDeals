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

    private SharedPreferences prefs;
    private DbHelper dbHelper;
    boolean updaterServiceRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);
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

    }
}
