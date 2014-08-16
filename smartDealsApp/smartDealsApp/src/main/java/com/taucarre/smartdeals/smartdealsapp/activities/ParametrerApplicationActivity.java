package com.taucarre.smartdeals.smartdealsapp.activities;

import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.taucarre.smartdeals.smartdealsapp.R;

public class ParametrerApplicationActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.parametres);

    }



}
