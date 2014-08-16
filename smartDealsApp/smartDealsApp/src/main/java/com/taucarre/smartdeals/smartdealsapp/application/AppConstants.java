package com.taucarre.smartdeals.smartdealsapp.application;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

/**
 * Created by tarekelchami on 25/06/14.
 */
public class AppConstants {

    /**
     * Class instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    /**
     * Class instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();


    /**
     * Retrieve a SmartDeals api service handle to access the API.
     */
    public static Smartdeals getApiServiceHandle() {
        // Use a builder to help formulate the API request.
        Smartdeals.Builder smartDeals = new Smartdeals.Builder(AppConstants.HTTP_TRANSPORT,
                AppConstants.JSON_FACTORY,null).setApplicationName("smart-deals");

        return smartDeals.build();
    }

}

