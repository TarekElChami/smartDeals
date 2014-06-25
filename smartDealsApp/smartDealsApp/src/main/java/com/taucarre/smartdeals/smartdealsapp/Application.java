package com.taucarre.smartdeals.smartdealsapp;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.google.api.client.util.Lists;

import java.util.ArrayList;

/**
 * Created by tarekelchami on 25/06/14.
 */
public class Application extends android.app.Application {
    ArrayList<Deal> deals = Lists.newArrayList();
}
