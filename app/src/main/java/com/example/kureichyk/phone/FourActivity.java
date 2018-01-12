package com.example.kureichyk.phone;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by kureichyk on 12.01.2018.
 */

public class FourActivity extends Activity {
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button button_mob;
    String pos4; // для вызова активити два нужны данные позиции курсора, поэтому тягаем их за собой сюда и потом отдаем обратно
    String position3;
    TextView text_fio;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        button_mob =(Button) findViewById(R.id.button_mob);
        text_fio =(TextView) findViewById(R.id.text_fio);
        FourfromDB();

    }

    public void FourfromDB(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        position3=getIntent().getStringExtra("position3");
//        pos=getIntent().getStringExtra("position");

        cursor = mSqLiteDatabase.query("contacts", null,
                "_id=?", new String[] {position3},
                null, null, null);
        cursor.moveToLast();
String s;
        s=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.fio));
        text_fio.setText(s);


//        String N_otdela;
//        N_otdela=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id3));
//        cursor2 = mSqLiteDatabase.query("contacts",null,
//                "id_podr=?", new String[] {N_otdela},
//                null, null,null);
//
//        String[] from = new String[] {DBHelper.id_podr, DBHelper.fio};//берем этот набор данных
//        int[] to = new int[] { R.id.l1, R.id.l2};// и вставляем их сюда
//        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt, cursor2, from, to);
//        lst3.setAdapter(scAdapter);

    }




}



