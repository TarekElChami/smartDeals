package com.taucarre.smartdeals.smartdealsapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class FocusOnDealActivity extends FragmentActivity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_on_deal);

        Intent intent = getIntent();
        String nomDeal = intent.getStringExtra("nom");
        String marchandDeal = intent.getStringExtra("marchand");
        String descriptionDeal = intent.getStringExtra("description");

        TextView nomZoom = (TextView) findViewById(R.id.nomDealZoom);
        nomZoom.setText(nomDeal);

        TextView descZoom = (TextView) findViewById(R.id.textDescDealZoom);
        descZoom.setText(descriptionDeal);

        TextView marchandZoom = (TextView) findViewById(R.id.marchandDealZoom);
        marchandZoom.setText(marchandDeal);

        map =  ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.focus_on_deal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
