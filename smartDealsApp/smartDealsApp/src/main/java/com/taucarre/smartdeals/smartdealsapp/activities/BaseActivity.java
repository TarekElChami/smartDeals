package com.taucarre.smartdeals.smartdealsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;

/**
 * Activite de base pour gérer le menu en commun pour les activités
 * ainsi que les propriétés en commun
 * @author tarekelchami
 */
public class BaseActivity extends Activity {
  SmartDealsApplication smartDealsApplication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    smartDealsApplication = (SmartDealsApplication) getApplication();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.liste_deals_insider, menu);
      return true;
  }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        smartDealsApplication = (SmartDealsApplication) getApplication();
            menu.findItem(R.id.showFavoris).setVisible(false);
        if(smartDealsApplication.isUserAthentifie()) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.deconnecter).setVisible(true);
            menu.findItem(R.id.ajouterDeal).setVisible(true);
            menu.findItem(R.id.configurerProfilMenu).setVisible(true);
        }else{
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.deconnecter).setVisible(false);
            menu.findItem(R.id.ajouterDeal).setVisible(false);
            menu.findItem(R.id.configurerProfilMenu).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        String action;
        switch (id) {
            case R.id.login:
                intent.setClass(smartDealsApplication, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, ParametrerApplicationActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.updateMenu:
                action = "smartdeals.action.updatecommentaire";
                intent.setAction(action);
                startService(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
