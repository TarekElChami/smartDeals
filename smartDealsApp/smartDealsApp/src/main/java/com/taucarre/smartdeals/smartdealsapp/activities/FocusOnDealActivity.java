package com.taucarre.smartdeals.smartdealsapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Strings;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.persistence.DealsDataDao;

import java.io.IOException;
import java.util.List;


public class FocusOnDealActivity extends FragmentActivity {

    private SmartDealsApplication smartDealsApplication;
    private GoogleMap map;
    TextView nomZoom;
    TextView marchandZoom;
    TextView descZoom;
    TextView prixZoom;
    ImageView imageDealZoom;
    Deal dealDetaille;
    String adresseDeal = "";
    DealsDataDao dealsDataDao;

    private static final Double Latitude_PARIS  = 48.8534100 ;
    private static final Double Longetude_PARIS =  2.3488000 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_on_deal);

        smartDealsApplication = (SmartDealsApplication) getApplication();
        dealsDataDao = new DealsDataDao(smartDealsApplication);
        Intent intent = getIntent();
        String nomDeal = intent.getStringExtra("nom");
        String marchandDeal = intent.getStringExtra("marchand");
        String descriptionDeal = intent.getStringExtra("description");


        nomZoom = (TextView) findViewById(R.id.nomDealZoom);
        marchandZoom = (TextView) findViewById(R.id.marchandDealZoom);
        descZoom = (TextView) findViewById(R.id.textDescDealZoom);
        prixZoom = (TextView) findViewById(R.id.prixDealZoom);
        imageDealZoom = (ImageView) findViewById(R.id.imageDealZoom);


        map =  ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        String params[] = {nomDeal, marchandDeal, descriptionDeal};


        //new ChagerDetailsDeal().execute(params);

        dealDetaille = dealsDataDao.getDealForDetails(params[0], params[1], params[2]);
        dealsDataDao.close();
        //dealDetaille = deal;

        nomZoom.setText(dealDetaille.getNomDeal());
        descZoom.setText(dealDetaille.getDesciprtionDeal());
        marchandZoom.setText(dealDetaille.getNomMarchand());
        prixZoom.setText(String.valueOf(dealDetaille.getPrix()) + "(€)");

        String encodedB64ImageDeal = dealDetaille.getImageDeal();
        if(!Strings.isNullOrEmpty(encodedB64ImageDeal)){
            byte[] byteArrayImageDeal =  Base64.decode(encodedB64ImageDeal, Base64.DEFAULT);
            Bitmap bitmapImageDeal = BitmapFactory.decodeByteArray(byteArrayImageDeal,
                    0, byteArrayImageDeal.length);
            imageDealZoom.setImageBitmap(bitmapImageDeal);
            imageDealZoom.setMaxHeight(128);
            imageDealZoom.setMaxWidth(128);
            imageDealZoom.setAdjustViewBounds(true);
        }

        String latitudeDeal = dealDetaille.getLatitudeDeal();
        String longetitudeDeal = dealDetaille.getLongitudeDeal();
        String adresseDeal = dealDetaille.getAdresseDeal();

        MarkerOptions markerOptions = new MarkerOptions();
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressesDeal;
        LatLng latLng = null;

        if(Strings.isNullOrEmpty(adresseDeal)){
            latLng = new LatLng(Latitude_PARIS, Longetude_PARIS);

        }else {

            if (Strings.isNullOrEmpty(latitudeDeal)) {
                try {
                    addressesDeal = geocoder.getFromLocationName(adresseDeal, 1);

                    if (addressesDeal != null) {
                        Address addresse = addressesDeal.get(0);
                        latLng = new LatLng(addresse.getLatitude(), addresse.getLongitude());

                        String parametres[] = {
                                String.valueOf(dealDetaille.getIdDeal()),
                                String.valueOf(addresse.getLatitude()),
                                String.valueOf(addresse.getLongitude()),
                                nomDeal
                        };
                        new UpdateCoordonneDeal().execute(parametres);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                latLng = new LatLng(Double.parseDouble(latitudeDeal),
                        Double.parseDouble(longetitudeDeal));

            }
        }

        markerOptions.position(latLng);
        markerOptions.title(nomDeal);
        markerOptions.snippet(descriptionDeal);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        Marker marker = map.addMarker(markerOptions);
        marker.showInfoWindow();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    protected void onStop() {
        super.onStop();
        smartDealsApplication.getDbHelper().close();
       // map.clear();
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

        class ChagerDetailsDeal extends AsyncTask<String ,Void, Void>{

            @Override
            protected Void doInBackground(String[] params) {
                Deal deal = dealsDataDao.getDealForDetails(params[0], params[1], params[2]);
                dealsDataDao.close();
                dealDetaille = deal;
                return null;

            }

        }

    class UpdateCoordonneDeal extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... params) {
            dealsDataDao.updateDeal(params[0], params[1],params[2]);
            return "Deals " + params[3] + "updated : lat(" + params[1] + ")/long(" + params[2] + ")";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(smartDealsApplication,s,Toast.LENGTH_LONG).show();
        }
    }

}
