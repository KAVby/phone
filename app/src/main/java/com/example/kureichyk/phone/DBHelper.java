package com.example.kureichyk.phone;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kureichyk on 05.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    // имя базы данных
    private static final String DATABASE_NAME = "phone.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 1;


    // имя таблицы
    private static final String DATABASE_TABLE = "contacts";
    // названия столбцов
    public static final String id = "id";
    public static final String id_region = "id_region";                      //
    public static final String id_podr = "id_podr";
    public static final String phone = "phone";
    public static final String fio = "fio";
    public static final String doljnost = "doljnost"; //
    public static final String zvanie = "zvanie";
    public static final String mob = "mob";
    public static final String fax = "fax";
    public static final String email = "email";
    public static final String home = "home";
    public static final String prim = "prim";
    public static final String address = "address";
    public static final String kod = "kod";
    public static final String vnutr = "vnutr";
    public static final String centrex = "centrex";
    public static final String cm = "cm";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + id + " integer, "
            + id_region + " integer, "
            + id_podr + " integer, "
            + phone + " text, "
            + fio + " text, "
            + doljnost + " text,"
            + zvanie + " text,"
            + mob + " text,"
            + fax + " text,"
            + email + " text,"
            + home + " text,"
            + prim + " text,"
            + address + " text,"
            + kod + " text,"
            + vnutr + " text,"
            + centrex + " text,"
            + cm + " text);";


    private static final String DATABASE_TABLE2 = "regiont";
    // названия столбцов
    public static final String id2 = "id2";
    public static final String region = "region";                      //
    public static final String region_full = "region_full";
    public static final String address_region = "address_region";


    private static final String DATABASE_CREATE_SCRIPT2 = "create table "
            + DATABASE_TABLE2 + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + id2 + " integer, "
            + region + " text, "
            + region_full + " text, "
            + address_region + " text);";

    private static final String DATABASE_TABLE3 = "podrt";
    // названия столбцов
    public static final String id3 = "id3";
    public static final String id_region3 = "id_region3";                      //
    public static final String podr = "podr";
    public static final String podr_full = "podr_full";


    private static final String DATABASE_CREATE_SCRIPT3 = "create table "
            + DATABASE_TABLE3 + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + id3 + " integer, "
            + id_region3 + " text, "
            + podr + " text, "
            + podr_full + " text);";






    private DBHelper mDBHelper;
    private SQLiteDatabase db;

    DBHelper(String s, Context context, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(DATABASE_CREATE_SCRIPT);
        db.execSQL(DATABASE_CREATE_SCRIPT2);
        db.execSQL(DATABASE_CREATE_SCRIPT3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


}


