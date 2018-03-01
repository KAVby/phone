package com.example.kureichyk.phone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.Calendar;

import static com.example.kureichyk.phone.DBHelper.phone;

/**
 * Created by sas on 01-Mar-18.
 */

public class DBCrate extends AsyncTask<String, Void, Integer> {

    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;

    Context context;

    private ProgressDialog pDialog;
    private MainActivity activity;

    DBCrate(MainActivity activity, Context context){
        this.activity=activity;
        this.context=context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setTitle("Первый запуск");
        pDialog.setMessage("Формирование данных...");
        pDialog.setCancelable(false);
        pDialog.show();
    }



    @SuppressLint("WrongConstant")
    @Override
    protected Integer doInBackground(String... strings) {


            mDatabaseHelper = new DBHelper(context, "phone.db", null, 1);
            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            //       ArrayList<String> list = new ArrayList<String>();
            try {
                XmlPullParser parser = context.getResources().getXml(R.xml.contacts);
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
                Toast.makeText(context,
                        "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                        .show();
            } values.clear();
////////////////////////////////////////////////////////////////////////////////////////
//        ContentValues values = new ContentValues();
        //      ArrayList<String> list = new ArrayList<String>();
        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.region);
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
            Toast.makeText(context,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        }values.clear();
//        setListAdapter(new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, list));

////////////////////////////////////////////////////////////////////////////////////////

        try {
            XmlPullParser parser = context.getResources().getXml(R.xml.podr);
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
            Toast.makeText(context,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        }values.clear();



///////////////////////////////////////////////////////////////////////////////////////
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);


        pDialog.dismiss();

        AlertDialog.Builder goLogin = new AlertDialog.Builder(context);

         goLogin.setMessage("база создана");
         goLogin.setCancelable(false);
        goLogin.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.recreate();
            }
        });
//        AlertDialog alertLogin = goLogin.create();
//        alertLogin.show();

        goLogin.show();


    }
}
