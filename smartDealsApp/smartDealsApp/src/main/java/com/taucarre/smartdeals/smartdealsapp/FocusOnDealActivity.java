package com.taucarre.smartdeals.smartdealsapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class FocusOnDealActivity extends ActionBarActivity {

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
