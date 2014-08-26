package com.taucarre.smartdeals.smartdealsapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Deal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

    public void  insertOrIgnoreDeal(Deal deal) {
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

    public void updateDeal(String idDeal, String lat, String longitude){
        ContentValues args = new ContentValues();
        args.put(DbHelper.LATITUDE_DEAL, lat);
        args.put(DbHelper.LONGETUDE_DEAL, longitude);
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.updateWithOnConflict(DbHelper.DEALS_TABLE,
                args,
                DbHelper.ID_DEAL + " = " + idDeal,
                null, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public List<Deal> getAllDeals(){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<Deal> listeDeals = new ArrayList<Deal>();

        Cursor cursor =  db.query(DbHelper.DEALS_TABLE, null, null, null, null, null, GET_ALL_ORDER_BY);

        if(cursor != null && cursor.moveToNext() && cursor.getCount() > 0 ) {
            while (!cursor.isAfterLast()) {
                Deal deal = cursorToDeal(cursor);
                listeDeals.add(deal);
                cursor.moveToNext();
            }
        }

        return listeDeals;
    }


    public List<Deal> getLimitedDeals(int limit){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<Deal> listeDeals = new ArrayList<Deal>();
        String requete = "select * from deals LIMIT " + limit ;
        Cursor cursor = db.rawQuery(requete, null);
        if(cursor != null && cursor.moveToNext() && cursor.getCount() > 0 ) {
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

    public Deal getDealForDetails(String nomDeal, String marchandDeal, String descDeal){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from deals where " +
                        DbHelper.NOM_DEAL + " = ? AND " +
                        DbHelper.MARCHAND_DEAL + " = ? AND " +
                        DbHelper.DESCRIPTION_DEAL  + " =  ? ",
                new String[]{nomDeal, marchandDeal, descDeal});
        /*
        Cursor cursor = db.query(
                DbHelper.DEALS_TABLE,null,
                DbHelper.NOM_DEAL + " = ? AND " +
                DbHelper.MARCHAND_DEAL + " = ? AND " + DbHelper.DESCRIPTION_DEAL + " =  ? ",
                new String[] {nomDeal, marchandDeal, descDeal},null, null, null);
*/
           if(cursor.moveToNext()){
                return cursorToDeal(cursor);
            }else{
            return null;
        }
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
        values.put(DbHelper.ID_DEAL, deal.getIdDeal());
        values.put(DbHelper.NOM_DEAL, deal.getNomDeal());
        values.put(DbHelper.MARCHAND_DEAL, deal.getNomMarchand());
        values.put(DbHelper.CATEGORIE_DEAL, deal.getCategorieDeal());
        values.put(DbHelper.TYPE_DEAL, deal.getTypeDeal());
        values.put(DbHelper.DESCRIPTION_DEAL, deal.getDesciprtionDeal());
        values.put(DbHelper.IMAGE_DEAL, deal.getImageDeal());
        values.put(DbHelper.EXPIRE_DEAL, deal.getExpire());
        if(deal.getDateDeCreationDeal() != null){
            values.put(DbHelper.DATE_CREATION_DEAL,deal.getDateDeCreationDeal().getValue());
        }
        values.put(DbHelper.PRIX_DEAL, deal.getPrix());
        values.put(DbHelper.PRIX_CONSTATE_DEAL, deal.getPrix());
        values.put(DbHelper.ADRESSE_DEAL, deal.getAdresseDeal());
        values.put(DbHelper.LATITUDE_DEAL, deal.getLatitudeDeal());
        values.put(DbHelper.LONGETUDE_DEAL, deal.getLongitudeDeal());
        values.put(DbHelper.EVALUATION_DEAL, deal.getScoreDeal());

        return values;
    }


    private Deal cursorToDeal(Cursor cursor){
        Deal deal = new Deal();
        deal.setIdDeal(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.ID_DEAL))));
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
        deal.setAdresseDeal(cursor.getString(cursor.getColumnIndex(DbHelper.ADRESSE_DEAL)));
        deal.setLatitudeDeal(cursor.getString(cursor.getColumnIndex(DbHelper.LATITUDE_DEAL)));
        deal.setLongitudeDeal(cursor.getString(cursor.getColumnIndex(DbHelper.LONGETUDE_DEAL)));
        //deal.setDateDeCreationDeal();
        //deal.setDateExpirationDeal();
        return  deal;
    }



}
