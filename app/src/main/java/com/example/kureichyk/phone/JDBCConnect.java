package com.example.kureichyk.phone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.SimpleCursorAdapter;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kureichyk on 16.02.2018.
 */

public class JDBCConnect extends AsyncTask<String, Void, Integer> {
    final static String url = "jdbc:mysql://172.26.200.36:3306/phone";
    final static String LOGIN = "user";
    final static String PASS= "user";

    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;

    Context context;

    private ProgressDialog pDialog;
    private MainActivity activity;

    SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
    int state;

    public static final String APP_PREFERENCES = "mysettings";
    public static  String APP_PREFERENCES_str1_ = "str1_";
     SharedPreferences mSettings;






//конструктор класса
JDBCConnect(MainActivity activity, Context context){
    this.activity=activity;
    this.context=context;

}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setTitle("Прием данных");
        pDialog.setMessage("Обновление базы...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Integer doInBackground(String... query) {
        mDatabaseHelper = new DBHelper(context, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            String replace;
            try {state=1;
                con =  DriverManager.getConnection(url, LOGIN, PASS);
                if (con != null) {

                    st = con.createStatement();


                    rs = st.executeQuery(query[0]);
                    mSqLiteDatabase.delete("regiont",null,null);
                    if (rs != null) {
                        int columnCount = rs.getMetaData().getColumnCount();

                        while (rs.next()) {
                        //    JSONObject rowObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                switch (i){
                                    case 1:
                                        values.put(DBHelper.id2, rs.getString(i));
                                        break;
                                    case 2:
                                        replace=rs.getString(i); // убираем артефакты в скачиваемой базе
                                        if (replace.equals("&nbsp;&nbsp;МГУ МЧС&nbsp;&nbsp;"))
                                            replace="МГУ МЧС";
                                        if (replace.equals("&nbsp;&nbsp;РЦТО&nbsp;&nbsp;"))
                                            replace="РЦТО";
                                        if (replace.equals("&nbsp;&nbsp;НИИ ПБиЧС&nbsp;&nbsp;"))
                                            replace="НИИ ПБиЧС";

                                        values.put(DBHelper.region, replace);
                                        //                          list.add(parser.getText());
                                        break;
                                    case 3:
                                        values.put(DBHelper.region_full, rs.getString(i));
                                        break;
                                    case 4:
                                        values.put(DBHelper.address_region,rs.getString(i));

                                        break;
                                }
                             //   values.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            }
                            //resultSet.put(rowObject);
                            mSqLiteDatabase.insert("regiont", null, values);
                        }
                    }

                    st = null;
                    rs = null;
                    values.clear();
                    st = con.createStatement();
                    rs = st.executeQuery(query[1]);
                    mSqLiteDatabase.delete("podrt",null,null);
                    if (rs != null) {
                        int columnCount = rs.getMetaData().getColumnCount();
                        // Сохранение данных в JSONArray
                        while (rs.next()) {
                            //    JSONObject rowObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                switch (i){
                                    case 1:
                                        values.put(DBHelper.id3, rs.getString(i));
                                        break;
                                    case 2:
                                        values.put(DBHelper.id_region3, rs.getString(i));
                                        //                          list.add(parser.getText());
                                        break;
                                    case 3:
                                        values.put(DBHelper.podr, rs.getString(i));
                                        break;
                                    case 4:
                                        values.put(DBHelper.podr_full,rs.getString(i));

                                        break;
                                }
                                //   values.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            }
                            //resultSet.put(rowObject);
                            mSqLiteDatabase.insert("podrt", null, values);
                        }
                    }

///////data to contacts
                    st = null;
                    rs = null;
                    values.clear();
                    st = con.createStatement();
                    rs = st.executeQuery(query[2]);
                    mSqLiteDatabase.delete("contacts",null,null);
                    if (rs != null) {
                        int columnCount = rs.getMetaData().getColumnCount();
                        // Сохранение данных в JSONArray
                        while (rs.next()) {
                            //    JSONObject rowObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                switch (i){
                                    case 1:
                                        values.put(DBHelper.id, rs.getString(i));
                                        break;
                                    case 2:
                                        values.put(DBHelper.id_region, rs.getString(i));
                                        //                          list.add(parser.getText());
                                        break;
                                    case 3:
                                        values.put(DBHelper.id_podr, rs.getString(i));
                                        break;
                                    case 4:
                                        values.put(DBHelper.phone,rs.getString(i));
                                        break;
                                    case 5:
                                        replace=rs.getString(i);// делаем null для фио с нулевой длинной для сортировки
                                        if (replace.equals(""))
                                            replace=null;
                                        values.put(DBHelper.fio, replace);
                                        break;
                                    case 6:
                                        values.put(DBHelper.doljnost, rs.getString(i));
                                        break;
                                    case 7:
                                        values.put(DBHelper.zvanie, rs.getString(i));
                                        break;
                                    case 8:
                                        values.put(DBHelper.mob, rs.getString(i));
                                        break;
                                    case 9:
                                        values.put(DBHelper.fax, rs.getString(i));
                                        break;
                                    case 10:
                                        values.put(DBHelper.email, rs.getString(i));
                                        break;
                                    case 11:
                                        values.put(DBHelper.home, rs.getString(i));
                                        break;
                                    case 12:
                                        values.put(DBHelper.prim, rs.getString(i));
                                        break;
                                    case 13:
                                        values.put(DBHelper.address, rs.getString(i));
                                        break;
                                    case 14:
                                        values.put(DBHelper.kod, rs.getString(i));
                                        break;
                                    case 15:
                                        values.put(DBHelper.vnutr, rs.getString(i));
                                        break;
                                    case 16:
                                        values.put(DBHelper.centrex, rs.getString(i));
                                        break;
                                    case 17:
                                        values.put(DBHelper.cm, rs.getString(i));
                                        break;
                                }
                                //   values.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            }
                            //resultSet.put(rowObject);
                            mSqLiteDatabase.insert("contacts", null, values);
                        }
                    }





                }

            }  catch (SQLException e) {
                e.printStackTrace();
                state=0;
            }  finally {
                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            state=0;
        }
        return state;
    }

    @Override
    protected void onPostExecute(Integer state) {
        super.onPostExecute(state);

        pDialog.dismiss();

        AlertDialog.Builder goLogin = new AlertDialog.Builder(context);
        if (state==0)
        goLogin.setMessage("...нет соединения с серваком...\n\nВы должны быть подключены по \nWi-Fi к сети МЧС.\n\n  по интернету нельзя :)  ");
        else  {goLogin.setMessage("база обновлена");
            Calendar now=Calendar.getInstance();
          //  activity.textDateBD.setText("БД от "+dateFormat.format(now.getTime()));
            mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);//закидываем выбранную дату в файл
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_str1_, "БД от "+dateFormat.format(now.getTime()));
            editor.apply();
        }
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




//        pDialog2 = new ProgressDialog(activity);
//        pDialog2.setTitle("Прием данных");
//            pDialog2.setMessage("нет соединения");
//            pDialog2.show(); }
//        else {
//        pDialog2 = new ProgressDialog(activity);
//        pDialog2.setTitle("Прием данных");
//        pDialog2.setMessage("ок");
//        pDialog2.show();}
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


}




