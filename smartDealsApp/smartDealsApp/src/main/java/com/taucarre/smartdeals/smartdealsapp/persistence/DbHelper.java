package com.taucarre.smartdeals.smartdealsapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tarekelchami on 16/08/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = DbHelper.class.getSimpleName();

    private static final int VERSION = 1;
    private static final String DATABASE = "smartdeals.db";

    public static final String DEALS_TABLE = "deals";
    public static final String USERS_TABLE = "users";
    public static final String PARAM_TABLE = "parametres";
    public static final String COMMENTAIRES_TABLE = "commentaires";

    /******* Deals TABLE Columns ******/
    public static final String ID_DEAL = "id_deal";
    public static final String NOM_DEAL = "nom_deal";
    public static final String MARCHAND_DEAL = "marchand_deal";
    public static final String PRIX_DEAL = "prix_deal";
    public static final String PRIX_CONSTATE_DEAL = "prix_constate_deal";
    public static final String IMAGE_DEAL = "image_deal";
    public static final String CATEGORIE_DEAL = "categorie_deal";
    public static final String TYPE_DEAL = "type_deal";
    public static final String ADRESSE_DEAL = "adresse_deal";
    public static final String DESCRIPTION_DEAL = "desc_deal";
    public static final String INTERNET_DEAL = "internet_deal";
    public static final String LATITUDE_DEAL = "latitude_deal";
    public static final String LONGETUDE_DEAL = "longetude_deal";
    public static final String DATE_CREATION_DEAL = "date_creation_deal";
    public static final String DATE_EXPIRATION_DEAL = "date_expiration_deal";
    public static final String EXPIRE_DEAL = "expire_deal";
    public static final String EVALUATION_DEAL = "evaluation_deal";


    /****** Users TABLE Columns *******/
    public static final String ID_USER = "id_user";
    public static final String NOM_USER = "nom_user";
    public static final String LOGIN_USER = "login_user";
    public static final String MAIL_USER = "mail_user";
    public static final String PASS_USER = "pass_user";
    public static final String ROLE_USER = "role_user";
    public static final String NOTORIETE_USER = "notoriete_user";
    public static final String AVATAR_USER = "avatar_user";
    public static final String BANNED_USER = "banned_user";


    /**** PARAMETRES TABLE Columns ******/
    public static final String NOMBRE_DEALS_AFFICHE = "nombre_deals_affiche ";
    public static final String PREFERED_LOCATION = "prefered_location";


    /**** COMMENTAIRES TABLE columns *****/
    public static final String ID_COMMENTAIRE = "id_commentaire";
    public static final String ID_USER_COMMENTAIRE = "id_user_commentaire";
    public static final String ID_DEAL_COMMENTAIRE = "id_deal_commentaire";
    public static final String CONTENU_COMMENTAIRE = "contenu_commentaire";


    private String CREATE_DEALS_TABLE =
                    " create table if not exists " + DEALS_TABLE + " ( " +
                    ID_DEAL  + " int primary key, " +
                    NOM_DEAL + " text, " +
                    MARCHAND_DEAL + " text, " +
                    PRIX_DEAL + " real, " +
                    PRIX_CONSTATE_DEAL + " real, " +
                    IMAGE_DEAL + " text, " +
                    CATEGORIE_DEAL + " text, " +
                    TYPE_DEAL + " text, " +
                    ADRESSE_DEAL + " text, " +
                    DESCRIPTION_DEAL + " text, " +
                    INTERNET_DEAL + " int, " +
                    LATITUDE_DEAL + " text, " +
                    LONGETUDE_DEAL + " text, " +
                    DATE_CREATION_DEAL + " int, " +
                    DATE_EXPIRATION_DEAL + " int, " +
                    EXPIRE_DEAL + " int, " +
                    EVALUATION_DEAL + " int ) ";


    private String CREATE_USERS_TABLE =
                    " create table if not exists " + USERS_TABLE + " ( " +
                    ID_USER  + " int primary key, " +
                    NOM_USER + " text, " +
                    LOGIN_USER + " text, " +
                    MAIL_USER + " text, " +
                    PASS_USER + " text, " +
                    AVATAR_USER + " text, " +
                    ROLE_USER + " text, " +
                    NOTORIETE_USER + " int, " +
                    BANNED_USER + " int ) ";


    private String CREATE_COMMENTAIRES_TABLE =
                    " create table if not exists" + COMMENTAIRES_TABLE + " ( " +
                    ID_COMMENTAIRE  + " int primary key, " +
                    ID_USER_COMMENTAIRE + " int, " +
                    ID_DEAL_COMMENTAIRE + " int, " +
                    CONTENU_COMMENTAIRE + " text ) ";



    public DbHelper(Context context){
        super(context,DATABASE,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "Creating DEALS TABLE");
            db.execSQL(CREATE_DEALS_TABLE);

            Log.d(TAG, "Creating USERS TABLE");
            db.execSQL(CREATE_USERS_TABLE);

            Log.d(TAG, "Creating COMMENTAIRES TABLE");
            db.execSQL(CREATE_COMMENTAIRES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DEALS_TABLE);
        db.execSQL("drop table " + USERS_TABLE);
        db.execSQL("drop table " + COMMENTAIRES_TABLE);
        this.onCreate(db);
    }
}
