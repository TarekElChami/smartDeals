package com.taucarre.smartdeals.smartdealsapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.google.common.base.Strings;

import java.io.IOException;


public class AjoutDealActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    private Spinner listeCategorie;
    private int typeOnUi = 0;
    private int typeInitialise = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_deal);

        listeCategorie = (Spinner) findViewById(R.id.categorieDealListe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeDeals,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listeCategorie.setAdapter(adapter);
        listeCategorie.setOnItemSelectedListener(this);

        typeOnUi = 1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ajout_deal, menu);
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(this.typeInitialise < this.typeOnUi){

            Log.i(TAG, "initilisation liste type");

            typeInitialise++;
        }else{

            Log.i(TAG, "choix type deal/lancement activite liste");

            String typeDeal =  (String) adapterView.getItemAtPosition(i);


//            Pair<Context,String>[] param = new Pair[2];
//            param[0] = new Pair<Context, String>(this, "exempleDeal");
//            param[0] = new Pair<Context, String>(this, "get");
//            new ServletDealAsynchTask().execute(param);

            //  Intent intent = new Intent(Constantes.ACTION_OUVERTURE_LISTE_DEALS);
            // intent.putExtra(Constantes.TYPE_DEAL,typeDeal);

            //startActivity(intent);


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onClickInsertDeal(View view) {
        View rootView = view.getRootView();

        TextView dealNameInput = (TextView)rootView.findViewById(R.id.nomDealEditText);
        if (dealNameInput.getText()==null ||
                Strings.isNullOrEmpty(dealNameInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Name", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealDescInput = (TextView)rootView.findViewById(R.id.descDealEditText);
        if (dealDescInput.getText()==null ||
                Strings.isNullOrEmpty(dealDescInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Description", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealNomMarchandInput = (TextView)rootView.findViewById(R.id.nomMarchandEditText);
        if (dealNomMarchandInput.getText()==null ||
                Strings.isNullOrEmpty(dealNomMarchandInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Marchand", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealpriceInput = (TextView)rootView.findViewById(R.id.prixDealEditText);
        if (dealpriceInput.getText()==null ||
                Strings.isNullOrEmpty(dealpriceInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal price", Toast.LENGTH_SHORT).show();
            return;
        };

        final String dealNameString = dealNameInput.getText().toString();
        final String dealDescString = dealDescInput.getText().toString();
        final String dealMarchandString = dealNomMarchandInput.getText().toString();

        String dealPriceString = dealpriceInput.getText().toString();
        final int dealPrice = Integer.parseInt(dealPriceString);


        AsyncTask<Void, Void, Deal> sendDeals = new AsyncTask<Void, Void, Deal> () {
            @Override
            protected Deal doInBackground(Void... unused) {
                // Retrieve service handle.
                Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

                try {
                    Deal deal = new Deal();
                    deal.setNomDeal(dealNameString);
                    deal.setDesciprtionDeal(dealDescString);
                    deal.setNomMarchand(dealMarchandString);
                    deal.setPrix(dealPrice);

                    Smartdeals.InsertDeal insertDealCommand = apiServiceHandle.insertDeal(deal);
                    insertDealCommand.execute();

                    return deal;
                } catch (IOException e) {
                    Log.e(TAG, "Exception during API call", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Deal deal) {
                if (deal !=null) {
                    Toast.makeText(getBaseContext(),"Deal enregistré", Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "No Deals were returned by the API.");
                }
            }
        };

        sendDeals.execute((Void)null);
    }
}
