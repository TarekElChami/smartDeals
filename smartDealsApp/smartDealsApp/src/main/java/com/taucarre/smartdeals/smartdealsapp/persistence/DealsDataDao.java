package com.taucarre.smartdeals.smartdealsapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Deal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekelchami on 15/08/14.
 */
public class DealsDataDao {

    private static final String TAG = DealsDataDao.class.getSimpleName();


    private static final String GET_ALL_ORDER_BY = DbHelper.DATE_CREATION_DEAL + " DESC";


    final DbHelper dbHelper;

    public DealsDataDao(Context context) {
        this.dbHelper = new DbHelper(context);
        Log.i(TAG, "creating database");
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insertOrIgnoreDeal(Deal deal) {
        ContentValues values = dealToValues(deal);

        Log.d(TAG, "insertOrIgnore on " + values);
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try {
            db.insertWithOnConflict(DbHelper.DEALS_TABLE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            db.close();
        }
    }

    public List<Deal> getAllDeals(){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<Deal> listeDeals = new ArrayList<Deal>();

        Cursor cursor =  db.query(DbHelper.DEALS_TABLE, null, null, null, null, null, GET_ALL_ORDER_BY);

        if(cursor != null) {
            while (!cursor.isAfterLast()) {
                Deal deal = cursorToDeal(cursor);
                listeDeals.add(deal);
                cursor.moveToNext();
            }
        }

        return listeDeals;
    }

    public Cursor getDealById(int idDeal){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        return db.query(DbHelper.DEALS_TABLE,null, DbHelper.ID_DEAL + " = " + idDeal,
                null, null, null, null);
    }

    /**
     * Deletes ALL the data
     */
    public void delete() {
        // Open Database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the data
        db.delete(DbHelper.DEALS_TABLE, null, null);

        // Close Database
        db.close();
    }

    private ContentValues dealToValues(Deal deal){
        ContentValues values = new ContentValues();
        values.put(DbHelper.ID_DEAL, deal.getIdDeal().intValue());
        values.put(DbHelper.NOM_DEAL, deal.getNomDeal());
        values.put(DbHelper.MARCHAND_DEAL, deal.getNomMarchand());
        values.put(DbHelper.CATEGORIE_DEAL, deal.getCategorieDeal());
        values.put(DbHelper.TYPE_DEAL, deal.getTypeDeal());
        values.put(DbHelper.DESCRIPTION_DEAL, deal.getDesciprtionDeal());
        values.put(DbHelper.IMAGE_DEAL, deal.getImageDeal());
        values.put(DbHelper.EXPIRE_DEAL, deal.getExpire());
        values.put(DbHelper.DATE_CREATION_DEAL, deal.getDateDeCreationDeal().getValue());
        values.put(DbHelper.PRIX_DEAL, deal.getPrix());
        values.put(DbHelper.PRIX_CONSTATE_DEAL, deal.getPrix());
        values.put(DbHelper.ADRESSE_DEAL,"adresse");
        values.put(DbHelper.LATITUDE_DEAL,"");
        values.put(DbHelper.LONGETUDE_DEAL, "");
        values.put(DbHelper.EVALUATION_DEAL, 0);

        return values;
    }


    private Deal cursorToDeal(Cursor cursor){
        Deal deal = new Deal();
        deal.setIdDeal(new Long(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.ID_DEAL))));
        deal.setNomDeal(cursor.getString(cursor.getColumnIndex(DbHelper.NOM_DEAL)));
        deal.setNomMarchand(cursor.getString(cursor.getColumnIndex(DbHelper.MARCHAND_DEAL)));
        deal.setImageDeal(cursor.getString(cursor.getColumnIndex(DbHelper.IMAGE_DEAL)));
        deal.setDesciprtionDeal(cursor.getString(cursor.getColumnIndex(DbHelper.DESCRIPTION_DEAL)));
        deal.setTypeDeal(cursor.getString(cursor.getColumnIndex(DbHelper.TYPE_DEAL)));
        deal.setCategorieDeal(cursor.getString(cursor.getColumnIndex(DbHelper.CATEGORIE_DEAL)));
        deal.setPrix(cursor.getInt(cursor.getColumnIndex(DbHelper.PRIX_DEAL)));
        deal.setPrixGeneralementConstate(cursor.getInt(cursor.getColumnIndex(DbHelper.PRIX_CONSTATE_DEAL)));
        int valueExpire = cursor.getInt(cursor.getColumnIndex(DbHelper.EXPIRE_DEAL));
        if(valueExpire == 0){
            deal.setExpire(false);
        }else{
            deal.setExpire(true);
        }
        //deal.setDateDeCreationDeal();
        //deal.setDateExpirationDeal();
        return  deal;
    }



}
