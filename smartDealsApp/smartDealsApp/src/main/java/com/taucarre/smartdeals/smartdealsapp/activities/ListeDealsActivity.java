package com.taucarre.smartdeals.smartdealsapp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.modele.ListeDeal;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.persistence.DealsDataDao;

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

        Intent intent2 = new Intent("smartdeals.action.synchronize");
        startService(intent2);

        Intent intent = new Intent("smartdeals.action.update");
        startService(intent);

        liste = (ListView) findViewById(android.R.id.list);
        liste.requestFocus();
        registerForContextMenu(liste);
        listeDeal = new ListeDeal();

        boolean showFavori = getIntent().getBooleanExtra("show_favoris", false);
        if(showFavori){
            String params[] =  {
                    String.valueOf(smartDealsApplication.getUsersAuthentifie().get(0).getIdUser())};
            new ChargerListeFavori().execute(params);
        }else{
            new ChargerListeDeal().execute();
            }


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        smartDealsApplication = (SmartDealsApplication) getApplication();
        if(smartDealsApplication.isUserAthentifie()) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.deconnecter).setVisible(true);
            menu.findItem(R.id.ajouterDeal).setVisible(true);
            menu.findItem(R.id.configurerProfilMenu).setVisible(true);
            menu.findItem(R.id.showFavoris).setVisible(true);

        }else{
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.deconnecter).setVisible(false);
            menu.findItem(R.id.ajouterDeal).setVisible(false);
            menu.findItem(R.id.configurerProfilMenu).setVisible(false);
            menu.findItem(R.id.showFavoris).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.liste_deals_insider, menu);

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        smartDealsApplication.getDbHelper().close();
        Intent intent2 = new Intent("smartdeals.action.synchronize");
        Intent intent = new Intent("smartdeals.action.update");
        stopService(intent2);
        stopService(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        String action;
            switch (id) {
                case R.id.login:
                    intent.setClass(smartDealsApplication, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.deconnecter:
                    action = "smartdeals.action.deconnecter";
                    intent.setAction(action);
                    startService(intent);
                    break;
                case R.id.ajouterDeal:
                    action = "SMARTDEALS_PROPOSITION_DEAL_ACTIVITE";
                    intent.setAction(action);
                    startActivity(intent);
                    break;
                case R.id.configurerProfilMenu:
                    action = "SMARTDEALS_CONFIGURER_PROFIL_ACTIVITE";
                    intent.setAction(action);
                    startActivity(intent);
                    break;
                case R.id.action_settings:
                    startActivity(new Intent(this, ParametrerApplicationActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                    break;
                case R.id.updateMenu:
                    action = "smartdeals.action.update";
                    intent.setAction(action);
                    startService(intent);
                    break;
                case R.id.showFavoris:
                    intent.setClass(smartDealsApplication,ListeDealsActivity.class);
                    intent.putExtra("show_favoris", true);
                    startActivity(intent);
            }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(smartDealsApplication.isUserAthentifie()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_liste_principale, menu);
        }

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Deal deal = listeDeal.get(info.position);
        switch (item.getItemId()) {
            case R.id.detailsDealContext:
                Toast.makeText(getApplicationContext(),"detaisl",Toast.LENGTH_LONG).show();
                return true;
            case R.id.addDealToFavorite:
                String params[] =  {
                        String.valueOf(smartDealsApplication.getUsersAuthentifie().get(0).getIdUser()),
                        String.valueOf(deal.getIdDeal())
                        };
                new AddDealToFavorites().execute(params);
                return true;
            case R.id.modifierDealContext:
                if(deal.getAddedBy() != null && deal.getAddedBy().equals(smartDealsApplication.getUsersAuthentifie().get(0).getIdUser())){
                    smartDealsApplication.setDealAModifier(deal);
                    Intent intent = new Intent();
                    String action = "SMARTDEALS_PROPOSITION_DEAL_ACTIVITE";
                    intent.putExtra("modificationDeal", true);
                    intent.setAction(action);
                    startActivity(intent);
                }else{
                    Toast.makeText(smartDealsApplication,"Modification deal non autorisée", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.voirCommentairesContext:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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

            DealsDataDao dealsDataDao = new DealsDataDao(smartDealsApplication);
            List<Deal> listeItems = dealsDataDao.getLimitedDeals(smartDealsApplication.getTaillListe());


                for (Deal deal : listeItems) {
                    listeDeal.add(deal);
                }
            smartDealsApplication.setListeDeal(listeDeal);
            dealsDataDao.close();
            return null;
        }

    }


    class ChargerListeFavori extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            liste.setAdapter(new ListeDealAdapter(getBaseContext(), listeDeal));
            Toast.makeText(getBaseContext(), "Fin chargement liste", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params) {

            DealsDataDao dealsDataDao = new DealsDataDao(smartDealsApplication);
            List<Deal> listeItems = dealsDataDao.getLimitedDeals(smartDealsApplication.getTaillListe());

            List<String> listeFavoris = dealsDataDao.geFavorisDealsByUSer(params[0]);

            for (Deal deal : listeItems) {
                if(listeFavoris.contains(String.valueOf(deal.getIdDeal()))){
                    listeDeal.add(deal);
                }
            }
            smartDealsApplication.getDbHelper().close();
            smartDealsApplication.setListeDeal(listeDeal);
            return null;
        }

    }

    class AddDealToFavorites extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Deal ajouté aux favoris",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params) {
            DealsDataDao dao = new DealsDataDao(smartDealsApplication);
            dao.ajouterDealToFavoris(params[0], params[1]);
            dao.close();
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
