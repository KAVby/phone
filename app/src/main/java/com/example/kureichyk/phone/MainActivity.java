package com.example.kureichyk.phone; //// TODO: 30.01.2018 в поиске не видно деления на подотделы (паспы, дежурные части и т.п.)
// TODO: что-нибудь придумать с кривыми кодами телефонов или где кодов нету,
// TODO: нет id некоторых подразделений например 3905
// TODO: придумать как вшить базу, не формируя ее на устройстве
// TODO: проверку наличия БД изменить как надо
// TODO: позакрывать курсоры


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import static com.example.kureichyk.phone.DBHelper.phone;
import static com.example.kureichyk.phone.R.layout.activity_main;

//import android.widget.ListView;



public class MainActivity extends Activity {

    Button Butupdate, butsearch;
    ListView lst;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    TextView textDateBD;

    public static final String APP_PREFERENCES = "mysettings";
    public static  String APP_PREFERENCES_str1_ = "str1_";
    SharedPreferences mSettings;


    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        Butupdate=(Button) findViewById(R.id.ButupdateBD);
        butsearch=(Button) findViewById(R.id.butsearch);
        lst=(ListView) findViewById(R.id.list1);
textDateBD=(TextView) findViewById(R.id.textDateBD);

        if (estDannie())
        { FitstfromDB();
       }
        else {
            new DBCrate(this, this).execute();

            FitstfromDB();}

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
 //               Butupdate.setText((Long.toString(idd)+" + "+Integer.toString(position+1)));
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("position1", Long.toString(idd));   //передаю данные об ид списка
                MainActivity.this.finish();
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                startActivity(intent);
        }
        });




    }

    public void onClickBD(View w){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected = networkInfo.isConnected();

        if (isWifiConnected==true)
        new JDBCConnect(this, this).execute("SELECT * FROM region", "SELECT * FROM podr", "SELECT * FROM data");
            else {

            AlertDialog.Builder goLogin = new AlertDialog.Builder(this);
            goLogin.setMessage("...нет соединения с серваком...\n\nВы должны быть подключены по \nWi-Fi к сети МЧС.\n\n  по интернету нельзя :)  ");
            goLogin.setCancelable(false);
            goLogin.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                 }
            });
            goLogin.show();

            }










    }
    public void FitstfromDB(){
   //     lst=(ListView) findViewById(R.id.list1);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(APP_PREFERENCES_str1_))
            textDateBD.setText(mSettings.getString(APP_PREFERENCES_str1_, "none"));
        else textDateBD.setText("БД от 10.02.2018");

        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        cursor = mSqLiteDatabase.query("regiont", null,
                null, null,
                null, null, "id2 ASC");
        String[] from = new String[]{DBHelper.region, DBHelper.region_full};//берем этот набор данных
        int[] to = new int[]{R.id.l1, R.id.l2};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt2, cursor, from, to);



        lst.setAdapter(scAdapter);

    }
//public void knopka(String s){
//
//    Butupdate.setText(s);
//
//}


 //======================================================================================

    public boolean estDannie() {     //проверяем есть ли данные в таблице
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = mSqLiteDatabase.query("contacts", new String[]{mDatabaseHelper._ID, mDatabaseHelper.id},
                null, null,
                null, null, null);
        cursor.moveToLast();
        if ((cursor.getCount() > 0))
        {cursor.close();

            return true;}
        else {cursor.close();
            return false;}
    }
//=========================================================================================

public void onClickSearch(View v){
    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
 //   intent.putExtra("position1", Long.toString(idd));   //передаю данные об ид списка
    MainActivity.this.finish();
  //  overridePendingTransition(R.anim.right_in,R.anim.left_out);
    startActivity(intent);
}

//=========================================================================
// преобразуем все в одну табл. пока ищу другой способ
    public void rebild (){
        String s,s2,s3;
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        cursor=mSqLiteDatabase.rawQuery("SELECT * FROM regiont   ORDER BY id2" , null);
        cursor.moveToFirst();
       do{
       s=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.region));
       s2=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id2));
//s3="UPDATE contacts SET id_region="+ "5"+" WHERE id_region="+"0";
          Cursor c= mSqLiteDatabase.rawQuery("UPDATE contacts SET id_region=? WHERE id_region=?",new String[]{s,s2});
            c.moveToFirst();
            c.close();} // курсор здесь просто нужен, зачем никто не знает
           while (cursor.moveToNext()) ;

        cursor=mSqLiteDatabase.rawQuery("SELECT * FROM podrt   ORDER BY id3" , null);
        cursor.moveToFirst();
        do{
            s=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.podr));
            s2=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id3));
            if (s=="" ) s="нет";
            if (s==null ) s="null";

//s3="UPDATE contacts SET id_region="+ "5"+" WHERE id_region="+"0";
//            Cursor d =mSqLiteDatabase.rawQuery("SELECT EXISTS(SELECT * FROM contacts WHERE id_podr=?)",new String[]{s});
//            cursor.moveToLast();
//            if (d.getCount()>0){
            Cursor c= mSqLiteDatabase.rawQuery("UPDATE contacts SET id_podr=? WHERE id_podr=? AND EXISTS (SELECT * FROM contacts WHERE id_podr=?)",new String[]{s,s2,s2});
            c.moveToFirst(); // курсор здесь просто нужен, зачем никто не знает
            c.close();} // курсор здесь просто нужен, зачем никто не знает
        while (cursor.moveToNext()) ;

    }


}











