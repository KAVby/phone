package com.example.kureichyk.phone;

import android.app.Activity;
import android.app.ListActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.ListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

//import java.util.ArrayList;

//import static android.R.id.list;

public class MainActivity extends Activity {

    Button Butupdate;
    ListView lst;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Butupdate=(Button) findViewById(R.id.ButupdateBD);
        lst=(ListView) findViewById(R.id.list1);
        registerForContextMenu(lst);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Butupdate.setText(Double.toString(id)+Integer.toString(position+1));
            }});




    }

    public void onClickBD(View w){
        if (estDannie())
            FitstfromDB();
        else {
        ContactsDBfromXML();
       RegionDBfromXML();
        PodrDBfromXML();
        }
    }
    public void FitstfromDB(){
   //     lst=(ListView) findViewById(R.id.list1);
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        cursor = mSqLiteDatabase.query("regiont", null,
                null, null,
                null, null, "id2 ASC");

        String[] from = new String[] {DBHelper.id2, DBHelper.region};//берем этот набор данных
        int[] to = new int[] { R.id.l1, R.id.l2};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt, cursor, from, to);
        lst.setAdapter(scAdapter);

    }

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
                      if (values.containsKey("id"))
                          mSqLiteDatabase.insert("contacts", null, values);
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
                            values.put(DBHelper.phone, parser.getText());
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
                    if (values.containsKey("id2"))
                        mSqLiteDatabase.insert("regiont", null, values);
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
                    if (values.containsKey("id3"))
                        mSqLiteDatabase.insert("podrt", null, values);
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








}











