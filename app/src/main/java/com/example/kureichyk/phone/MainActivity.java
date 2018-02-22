package com.example.kureichyk.phone; //// TODO: 30.01.2018 в поиске не видно деления на подотделы (паспы, дежурные части и т.п.)
// TODO: что-нибудь придумать с кривыми кодами телефонов или где кодов нету,
// TODO: нет id некоторых подразделений например 3905
// TODO: придумать как вшить базу, не формируя ее на устройстве
// TODO: проверку наличия БД изменить как надо
// TODO: позакрывать курсоры



import android.app.Activity;
import android.app.ListActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.ListView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import static com.example.kureichyk.phone.DBHelper.phone;
import static com.example.kureichyk.phone.R.layout.activity_main;

//import java.util.ArrayList;

//import static android.R.id.list;

public class MainActivity extends Activity {

    Button Butupdate, butsearch;
    ListView lst;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;

    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        Butupdate=(Button) findViewById(R.id.ButupdateBD);
        butsearch=(Button) findViewById(R.id.butsearch);
        lst=(ListView) findViewById(R.id.list1);


        if (estDannie())
        { FitstfromDB();
        new JDBCConnect().execute();}
        else {
            //Butupdate.setText("первый запуск, нажмите эту кнопку чтобы сформировать БД и ожидайте 1-3 мин взависимости от мощности вашего девайса"); // не работает такая схема сообщения об ожидании
//            ContactsDBfromXML();
//            RegionDBfromXML();
//            PodrDBfromXML();
           // rebild ();
         //   Butupdate.setText("Обновить БД - пока не сделал");

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

//    public void onClickBD(View w){
//       // knopka("ожидайте 1-3 мин взависимости от мощности вашего девайса");
//         if (estDannie())
//            FitstfromDB();
//        else {
//        ContactsDBfromXML();
//       RegionDBfromXML();
//        PodrDBfromXML();
//            FitstfromDB();
//           }
//    }
    public void FitstfromDB(){
   //     lst=(ListView) findViewById(R.id.list1);
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
    public void ContactsDBfromXML(){

        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
 //       ArrayList<String> list = new ArrayList<String>();
        try {
            XmlPullParser parser = getResources().getXml(R.xml.contacts);
            String S2="";
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("table"))
                {
 //                   list.add("  ");
//                      if (values.containsKey("id"))
//                          mSqLiteDatabase.insert("contacts", null, values);
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("column")) {
                        S2=parser.getAttributeValue(0);
                        parser.next();
 //                   list.add(S2+"  "+parser.getText());
                    switch (S2){
                        case "id":
                            values.put(DBHelper.id, parser.getText());
                            break;
                        case "id_region":
                            values.put(DBHelper.id_region, parser.getText());
                            break;
                        case "id_podr":
                            values.put(DBHelper.id_podr, parser.getText());
                            break;
                        case "phone":
                            values.put(phone, parser.getText());
                            break;
                        case "fio":
                            values.put(DBHelper.fio, parser.getText());
                            break;
                        case "doljnost":
                            values.put(DBHelper.doljnost, parser.getText());
                            break;
                        case "zvanie":
                            values.put(DBHelper.zvanie, parser.getText());
                            break;
                        case "mob":
                            values.put(DBHelper.mob, parser.getText());
                            break;
                        case "fax":
                            values.put(DBHelper.fax, parser.getText());
                            break;
                        case "email":
                            values.put(DBHelper.email, parser.getText());
                            break;
                        case "home":
                            values.put(DBHelper.home, parser.getText());
                            break;
                        case "prim":
                            values.put(DBHelper.prim, parser.getText());
                            break;
                        case "address":
                            values.put(DBHelper.address, parser.getText());
                            break;
                        case "kod":
                            values.put(DBHelper.kod, parser.getText());
                            break;
                        case "vnutr":
                            values.put(DBHelper.vnutr, parser.getText());
                            break;
                        case "centrex":
                            values.put(DBHelper.centrex, parser.getText());
                            break;
                        case "cm":
                            values.put(DBHelper.cm, parser.getText());
                            mSqLiteDatabase.insert("contacts", null, values);
                            break;
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        } //Butupdate.setText("Обновить БД - пока не сделал");
//        setListAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, list));
    }

//========================================================================================
    public void RegionDBfromXML(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
  //      ArrayList<String> list = new ArrayList<String>();
        try {
            XmlPullParser parser = getResources().getXml(R.xml.region);
            String S2="";
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("table"))
                {
 //                   list.add("  ");// для пропуска строки
//                    if (values.containsKey("id2"))
//                        mSqLiteDatabase.insert("regiont", null, values);
        //            values.clear();
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("column")) {
                    S2=parser.getAttributeValue(0);
                    parser.next();
 //                   list.add(S2+"  "+parser.getText());
                    switch (S2){
                        case "id":
                            values.put(DBHelper.id2, parser.getText());
                            break;
                        case "region":
                            values.put(DBHelper.region, parser.getText());
  //                          list.add(parser.getText());
                            break;
                        case "region_full":
                            values.put(DBHelper.region_full, parser.getText());
                            break;
                        case "address_region":
                            values.put(DBHelper.address_region, parser.getText());
                            mSqLiteDatabase.insert("regiont", null, values);
                            break;
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        }
//        setListAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, list));
    }

//=========================================================================================================

    public void PodrDBfromXML(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        ArrayList<String> list = new ArrayList<String>();
        try {
            XmlPullParser parser = getResources().getXml(R.xml.podr);
            String S2="";
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("table"))
                {
 //                   list.add("  ");
//                    if (values.containsKey("id3"))
//                        mSqLiteDatabase.insert("podrt", null, values);
                }
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("column")) {
                    S2=parser.getAttributeValue(0);
                    parser.next();
  //                  list.add(S2+"  "+parser.getText());
                    switch (S2){
                        case "id":
                            values.put(DBHelper.id3, parser.getText());
                            break;
                        case "id_region":
                            values.put(DBHelper.id_region3, parser.getText());
                            break;
                        case "podr":
                            values.put(DBHelper.podr, parser.getText());
                            break;
                        case "podr_full":
                            values.put(DBHelper.podr_full, parser.getText());
                            mSqLiteDatabase.insert("podrt", null, values);
                            break;
                    }
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        }
//        setListAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, list));
    }


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











