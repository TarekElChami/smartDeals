package com.taucarre.smartdeals.smartdealsapp;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.taucarre.smartdeals.presentation.modele.ListeDeal;

import java.io.IOException;
import java.util.List;


public class ListeDealsActivity extends ListActivity {

    private static final String TAG = ListeDealsActivity.class.getSimpleName();
    ListView liste;
    ListeDeal listeDeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_deals);

        liste = (ListView) findViewById(android.R.id.list);
        liste.requestFocus();
        listeDeal = new ListeDeal();
        new ChargerListeDeal().execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.liste_deals, menu);
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


    class ChargerListeDeal extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            liste.setAdapter(new ListeDealAdapter(getBaseContext(), listeDeal));
            Toast.makeText(getBaseContext(), "Fin chargement liste", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

            try {
                Smartdeals.ListDeals listeDealCommand = apiServiceHandle.listDeals();
                DealCollection dealCollection = listeDealCommand.execute();

                List<Deal> listeItems = dealCollection.getItems();
                for (Deal deal : listeItems) {
                    listeDeal.add(deal);
                }

            } catch (IOException e) {

            }
            return null;
        }

    }

}
