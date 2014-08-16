package com.taucarre.smartdeals.smartdealsapp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.DealCollection;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.modele.ListeDeal;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.services.UpdaterService;

import java.io.IOException;
import java.util.List;


public class ListeDealsActivity extends ListActivity {

    private static final String TAG = ListeDealsActivity.class.getSimpleName();
    ListView liste;
    ListeDeal listeDeal;
    SmartDealsApplication smartDealsApplication;
    int tailleListeAffiche = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_deals);

        smartDealsApplication = (SmartDealsApplication) getApplication();


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
        Intent intent = new Intent();
        String action;
        switch (id){
            case R.id.ajouterDeal:
                action = "SMARTDEALS_PROPOSITION_DEAL_ACTIVITE";
                intent.setAction(action);
                startActivity(intent);
                break;
            case R.id.configurerProfilMenu :
                action ="SMARTDEALS_CONFIGURER_PROFIL_ACTIVITE";
                intent.setAction(action);
                startActivity(intent);
                break;
            case R.id.test:
                action = "SMARTDEALS_TEST";
                intent.setAction(action);
                startActivity(intent);
                break;
            case R.id.action_settings :
                startActivity(new Intent(this, ParametrerApplicationActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.updateMenu :
                action = "smartdeals.action.update";
                intent.setAction(action);
                startService(intent);
                break;
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
                e.printStackTrace();
            }
            return null;
        }

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        TextView titreDeal = (TextView) v.findViewById(R.id.nomDeal);
        TextView marchandDeal = (TextView)  v.findViewById(R.id.emplacementDeal);
        TextView descriptionDeal = (TextView) v.findViewById(R.id.textDescDeal);

        String nomDeal = titreDeal.getText().toString();
        String marchand = marchandDeal.getText().toString();
        String description = descriptionDeal.getText().toString();

        Intent intent =  new Intent();
        intent.putExtra("nom", nomDeal);
        intent.putExtra("marchand", marchand);
        intent.putExtra("description", description);

        intent.setClass(this, FocusOnDealActivity.class);
        startActivity(intent);

    }

}
