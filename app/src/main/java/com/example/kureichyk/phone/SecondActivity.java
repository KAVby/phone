package com.example.kureichyk.phone;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by kureichyk on 11.01.2018.
 */

public class SecondActivity extends Activity {
    String position;
    ListView lst2;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button ButBack;
    String pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
     //   position=getIntent().getStringExtra("position");
        lst2=(ListView) findViewById(R.id.list2);
        ButBack =(Button) findViewById(R.id.ButBack);
        SecondfromDB();
        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                ButBack.setText((Long.toString(idd)+" + "+Integer.toString(position+1)));
                Intent intent = new Intent(SecondActivity.this, TherdActivity.class);
                intent.putExtra("position2", Long.toString(idd));   //передаю данные об ид списка
                intent.putExtra("position", pos);
                SecondActivity.this.finish();
                startActivity(intent);
            }
        });
    }
    public void SecondfromDB(){
         mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        pos=getIntent().getStringExtra("position");
        cursor = mSqLiteDatabase.query("regiont", null,
                "_id=?", new String[] {pos},
                null, null, null);
        cursor.moveToLast();
        String N_region;
      N_region=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id2));
        cursor2 = mSqLiteDatabase.query("podrt",null,
                "id_region3=?", new String[] {N_region},
                null, null,"id3 ASC");

        String[] from = new String[] {DBHelper.id_region3, DBHelper.podr};//берем этот набор данных
        int[] to = new int[] { R.id.l1, R.id.l2};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt, cursor2, from, to);
        lst2.setAdapter(scAdapter);

    }



public void onClickBack(View v){
    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
    SecondActivity.this.finish();
    startActivity(intent);

}

}
