package com.taucarre.smartdeals.smartdealsapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Comment;
import com.appspot.smart_deals.smartdeals.model.Deal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekelchami on 15/08/14.
 */
public class CommentairesDataDao {

    private static final String TAG = CommentairesDataDao.class.getSimpleName();


    private static final String GET_ALL_ORDER_BY = DbHelper.CREE_A_COMMENTAIRE + " DESC";


    final DbHelper dbHelper;

    public CommentairesDataDao(Context context) {
        this.dbHelper = new DbHelper(context);
        Log.i(TAG, "creating database");
    }

    public void close() {
        this.dbHelper.close();
    }

    public void  insertOrIgnoreCommmentaire(Comment comment) {
        ContentValues values = dealToValues(comment);

        Log.d(TAG, "insertOrIgnore on " + values);
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try {
            db.insertWithOnConflict(DbHelper.COMMENTAIRES_TABLE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            db.close();
        }
    }

    public Cursor getListeCommentairesByDeal(Long idDeal){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        return db.query(DbHelper.COMMENTAIRES_TABLE,null, DbHelper.ID_DEAL_COMMENTAIRE + " = " + idDeal,
                null, null, null, null);
    }


    public void delete() {
        // Open Database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete the data
        db.delete(DbHelper.COMMENTAIRES_TABLE, null, null);

        // Close Database
        db.close();
    }

    private ContentValues dealToValues(Comment comment){
        ContentValues values = new ContentValues();
        values.put(DbHelper.ID_COMMENTAIRE, comment.getIdComment());
        values.put(DbHelper.ID_DEAL_COMMENTAIRE, comment.getIdDeal());
        values.put(DbHelper.ID_USER_COMMENTAIRE, comment.getIdUser());
        values.put(DbHelper.NOM_USER_COMMENTAIRE, comment.getNomUser());
        values.put(DbHelper.CONTENU_COMMENTAIRE, comment.getTxtComment());
        values.put(DbHelper.CREE_A_COMMENTAIRE, comment.getDateDeCreation().getValue());
        return values;
    }


}
