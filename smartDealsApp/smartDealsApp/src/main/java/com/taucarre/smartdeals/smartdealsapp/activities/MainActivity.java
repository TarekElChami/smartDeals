package com.taucarre.smartdeals.smartdealsapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.Smartdeals.GetDealByName;
import com.appspot.smart_deals.smartdeals.Smartdeals.InsertDeal;

import com.google.common.base.Strings;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = "MainActivity";
    private DealDataAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prevent the keyboard from being visible upon startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ListView listView = (ListView) findViewById(R.id.deal_list_view);
        mListAdapter = new DealDataAdapter((SmartDealsApplication) getApplication());
        listView.setAdapter(mListAdapter);
    }

    /**
     * Simple use of an ArrayAdapter but we're using a static class to ensure no references to the
     * Activity exists.
     */
    static class DealDataAdapter extends ArrayAdapter {
        DealDataAdapter(SmartDealsApplication application) {
            super(application.getApplicationContext(), android.R.layout.simple_list_item_1,
                    application.deals);
        }

        void replaceData(Deal[] deals) {
            clear();
            for (Deal deal : deals) {
                add(deal);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);

            Deal deal = (Deal)this.getItem(position);

            StringBuilder sb = new StringBuilder();

            Set<String> fields = deal.keySet();
            boolean firstLoop = true;
            for (String fieldName : fields) {
                // Append next line chars to 2.. loop runs.
                if (firstLoop) {
                    firstLoop = false;
                } else {
                    sb.append("\n");
                }

                sb.append(fieldName)
                        .append(": ")
                        .append(deal.get(fieldName));
            }

            view.setText(sb.toString());
            view.setTextColor(Color.BLACK);
            return view;
        }
    }



    public void onClickGetDeal(View view) {
        View rootView = view.getRootView();
        TextView dealFetchInputName = (TextView)rootView.findViewById(R.id.deal_fetch_edit_text);
        if (dealFetchInputName.getText()==null ||
                Strings.isNullOrEmpty(dealFetchInputName.getText().toString())) {
            Toast.makeText(this, "Input a Deal Name", Toast.LENGTH_SHORT).show();
            return;
        };

        String dealFetchName = dealFetchInputName.getText().toString();

        // Use of an anonymous class is done for sample code simplicity. {@code AsyncTasks} should be
        // static-inner or top-level classes to prevent memory leak issues.
        // @see http://goo.gl/fN1fuE @26:00 for a great explanation.
        AsyncTask<String, Void, Deal> getAndDisplayDeal =
                new AsyncTask<String, Void, Deal> () {
                    @Override
                    protected Deal doInBackground(String... strings) {
                        // Retrieve service handle.
                        Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

                        try {
                            GetDealByName getDealCommand = apiServiceHandle.getDealByName(strings[0]);
                            Deal deal = getDealCommand.execute();
                            return deal;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Deal deal) {
                        if (deal!=null) {
                            displayDeal(deal);
                        } else {
                            Log.e(LOG_TAG, "No Deal were returned by the API.");
                        }
                    }
                };

        getAndDisplayDeal.execute(dealFetchName);
    }

    private void displayDeal(Deal... deals) {
        String msg;
        if (deals==null || deals.length < 1) {
            msg = "Deal was not present";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Log.d(LOG_TAG, "Displaying " + deals.length + " deals.");

            List<Deal> dealsList = Arrays.asList(deals);
            mListAdapter.replaceData(deals);
        }
    }


    public void onClickInsertDeal(View view) {
        View rootView = view.getRootView();

        TextView dealNameInput = (TextView)rootView.findViewById(R.id.deal_name_edit_text);
        if (dealNameInput.getText()==null ||
                Strings.isNullOrEmpty(dealNameInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Name", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealDescInput = (TextView)rootView.findViewById(R.id.deal_desc_edit_text);
        if (dealDescInput.getText()==null ||
                Strings.isNullOrEmpty(dealDescInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Description", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealNomMarchandInput = (TextView)rootView.findViewById(R.id.deal_marchand_edit_text);
        if (dealNomMarchandInput.getText()==null ||
                Strings.isNullOrEmpty(dealNomMarchandInput.getText().toString())) {
            Toast.makeText(this, "Input a Deal Marchand", Toast.LENGTH_SHORT).show();
            return;
        };

        TextView dealpriceInput = (TextView)rootView.findViewById(R.id.deal_price_edit_text);
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

                    InsertDeal insertDealCommand = apiServiceHandle.insertDeal(deal);
                    insertDealCommand.execute();

                    return deal;
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Exception during API call", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Deal deal) {
                if (deal !=null) {
                    displayDeal(deal);
                } else {
                    Log.e(LOG_TAG, "No Deals were returned by the API.");
                }
            }
        };

        sendDeals.execute((Void)null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.action_settings :
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}