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
            + id + " integer not null, "
            + id_region + " integer not null, "
            + id_podr + " integer not null, "
            + phone + " text not null, "
            + fio + " text not null, "
            + doljnost + " text not null,"
            + zvanie + " text not null,"
            + mob + " text not null,"
            + fax + " text not null,"
            + email + " text not null,"
            + home + " text not null,"
            + prim + " text not null,"
            + address + " text not null,"
            + kod + " text not null,"
            + vnutr + " text not null,"
            + centrex + " text not null,"
            + cm + " text not null);";


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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


}


