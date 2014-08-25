package com.taucarre.smartdeals.smartdealsapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appspot.smart_deals.smartdeals.model.Deal;
import com.appspot.smart_deals.smartdeals.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekelchami on 15/08/14.
 */
public class UsersDataDao {

    private static final String TAG = UsersDataDao.class.getSimpleName();


    final DbHelper dbHelper;

    public UsersDataDao(Context context) {
        this.dbHelper = new DbHelper(context);
        Log.i(TAG, "initialisation DataBase");
    }

    public void close() {
        this.dbHelper.close();
    }

    public void  insertOrIgnoreUser(User user) {
        ContentValues values = userToValues(user);

        Log.d(TAG, "insertOrIgnore on " + values);
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try {
            db.insertWithOnConflict(DbHelper.USERS_TABLE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            db.close();
        }
    }

    public User getUserByMail(String eMail){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        /*Cursor cursor = db.query(DbHelper.USERS_TABLE,null, DbHelper.MAIL_USER + " = " + eMail,
                null, null, null, null);
        */
        Cursor cursor = db.rawQuery("select * from users where " +
                                     DbHelper.MAIL_USER + " = ? ",
                                     new String[]{eMail});

        if(cursor.moveToNext()){
            return cursorToUser(cursor);
        }else{
            return null;
        }
    }

    public List<User> getAllUsers(){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<User> listeUsers = new ArrayList<User>();

        Cursor cursor =  db.query(DbHelper.USERS_TABLE, null, null, null, null, null, null);

        if(cursor != null && cursor.moveToNext() && cursor.getCount() > 0 ) {
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                listeUsers.add(user);
                cursor.moveToNext();
            }
        }

        return listeUsers;
    }


    private ContentValues userToValues(User user){
        ContentValues values = new ContentValues();
        values.put(DbHelper.ID_USER, user.getIdUser());
        values.put(DbHelper.NOM_USER, user.getName());
        values.put(DbHelper.PASS_USER, user.getPassword());
        values.put(DbHelper.AVATAR_USER, user.getAvatar());
        values.put(DbHelper.LOGIN_USER, user.getLogin());
        values.put(DbHelper.NOTORIETE_USER, user.getNotoriorite());
        values.put(DbHelper.BANNED_USER, user.getNotoriorite());
        values.put(DbHelper.MAIL_USER, user.getMail());
        return values;
    }

    private User cursorToUser(Cursor cursor) {
       User user = new User();
       user.setIdUser(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.ID_USER))));
       user.setName(cursor.getString(cursor.getColumnIndex(DbHelper.NOM_USER)));
       user.setMail(cursor.getString(cursor.getColumnIndex(DbHelper.MAIL_USER)));
       user.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper.PASS_USER)));
       user.setLogin(cursor.getString(cursor.getColumnIndex(DbHelper.LOGIN_USER)));
       user.setNotoriorite(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.NOTORIETE_USER))));
       user.setAvatar(cursor.getString(cursor.getColumnIndex(DbHelper.AVATAR_USER)));
       int valueBanned = cursor.getInt(cursor.getColumnIndex(DbHelper.BANNED_USER));
        if(valueBanned == 0){
            user.setBanned(false);
        }else{
            user.setBanned(true);
        }
        return user;

    }
}
