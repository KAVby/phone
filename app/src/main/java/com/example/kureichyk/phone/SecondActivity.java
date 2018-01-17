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
import android.widget.TextView;

/**
 * Created by kureichyk on 11.01.2018.
 */

public class SecondActivity extends Activity {
    String position1, Path1;
    ListView lst2;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button ButBack;
    TextView textWereYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
     //   position=getIntent().getStringExtra("position");
        lst2=(ListView) findViewById(R.id.list2);
        ButBack =(Button) findViewById(R.id.ButBack);
        textWereYou = (TextView) findViewById(R.id.textWereYou);
        SecondfromDB();
        lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
//                ButBack.setText((Long.toString(idd)+" + "+Integer.toString(position+1)));
                Intent intent = new Intent(SecondActivity.this, TherdActivity.class);
                intent.putExtra("position2", Long.toString(idd));   //передаю данные об ид списка
                intent.putExtra("position1", position1);// и данные чтобы вернуться назад
                intent.putExtra("path1", Path1);
                SecondActivity.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }
    public void SecondfromDB(){
         mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        position1=getIntent().getStringExtra("position1");
        cursor = mSqLiteDatabase.query("regiont", null,
                "_id=?", new String[] {position1},
                null, null, null);
        cursor.moveToLast();
        String N_region;
      N_region=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id2));
        cursor2 = mSqLiteDatabase.query("podrt",null,
                "id_region3=?", new String[] {N_region},
                null, null,"id3 ASC");
        Path1="/"+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.region));
textWereYou.setText(Path1);
        String[] from = new String[] {DBHelper.podr_full, DBHelper.podr};//берем этот набор данных
   //     String[] from = new String[] {DBHelper.podr};//берем этот набор данных
        int[] to = new int[] { R.id.l2, R.id.l1};// и вставляем их сюда
    //   int[] to = new int[] {  R.id.l2};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt2, cursor2, from, to);
        lst2.setAdapter(scAdapter);

    }



public void onClickBack(View v){
    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
    SecondActivity.this.finish();
    startActivity(intent);
    overridePendingTransition(R.anim.right_out,R.anim.left_in);

}

}
