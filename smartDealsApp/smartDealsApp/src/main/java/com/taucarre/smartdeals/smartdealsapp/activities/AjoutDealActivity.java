package com.taucarre.smartdeals.smartdealsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.google.common.base.Strings;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AjoutDealActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    private final static int SELECT_DEAL_PHOTO = 101;

    private Spinner listeCategorie;
    private Spinner listeTypeDeal;
    private ImageView dealImage;

    private String categorieDeal = "";
    private String typeDeal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_deal);

        listeCategorie = (Spinner) findViewById(R.id.categorieDealListe);

        listeTypeDeal = (Spinner) findViewById(R.id.typeDealSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorieDeal,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listeCategorie.setAdapter(adapter);
        listeCategorie.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.typeDeal,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listeTypeDeal.setAdapter(adapter1);
        listeTypeDeal.setOnItemSelectedListener(this);

        dealImage = (ImageView) findViewById(R.id.dealPhoto);

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

      /*  switch(view.getId()){
        //    case R.id.categorieDealListe :
          //      categorieDeal = (String) adapterView.getItemAtPosition(i);
                break;
            case R.id.typeDealSpinner :
                typeDeal = (String) adapterView.getItemAtPosition(i);
                break;
            default:
                break;
        }
*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onClickChooseDealImage(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,SELECT_DEAL_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(SELECT_DEAL_PHOTO == requestCode){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                try {
                    Bitmap bmpImage = decodeUri(selectedImage);
                    dealImage.setImageBitmap(bmpImage);
                    dealImage.setAdjustViewBounds(true);
                    dealImage.setMaxHeight(128);
                    dealImage.setMaxWidth(128);
                    Toast.makeText(getBaseContext(), "Image loaded", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 128;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

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

        categorieDeal = listeCategorie.getSelectedItem().toString();
        typeDeal = listeTypeDeal.getSelectedItem().toString();

        final String dealNameString = dealNameInput.getText().toString();
        final String dealDescString = dealDescInput.getText().toString();
        final String dealMarchandString = dealNomMarchandInput.getText().toString();

        String dealPriceString = dealpriceInput.getText().toString();
        final int dealPrice = Integer.parseInt(dealPriceString);

        Bitmap dealPhoto = ((BitmapDrawable)dealImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        dealPhoto.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] dealImageByteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String encodedDealImage = Base64.encodeToString(dealImageByteArray, Base64.DEFAULT);


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
                    deal.setImageDeal(encodedDealImage);
                    deal.setCategorieDeal(categorieDeal);
                    deal.setTypeDeal(typeDeal);

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
