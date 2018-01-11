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
 * Created by kureichyk on 12.01.2018.
 */

public class TherdActivity extends Activity {
    String position;
    ListView lst3;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button ButBack2;
    String pos; // для вызова активити два нужны данные позиции курсора, поэтому тягаем их за собой сюда и потом отдаем обратно



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therd);
        lst3=(ListView) findViewById(R.id.list3);
        ButBack2 =(Button) findViewById(R.id.ButBack2);
        TherdfromDB();
        lst3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                ButBack2.setText((Long.toString(idd)+" + "+Integer.toString(position+1)));
//                Intent intent = new Intent(SecondActivity.this, TherdActivity.class);
//                intent.putExtra("position2", Long.toString(idd));   //передаю данные об ид списка
//                intent.putExtra("position", pos);
//                SecondActivity.this.finish();
//                startActivity(intent);
            }
        });

    }

    public void TherdfromDB(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        position=getIntent().getStringExtra("position2");
        pos=getIntent().getStringExtra("position");

        cursor = mSqLiteDatabase.query("podrt", null,
                "_id=?", new String[] {position},
                null, null, null);
        cursor.moveToLast();
        String N_otdela;
        N_otdela=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id3));
        cursor2 = mSqLiteDatabase.query("contacts",null,
                "id_podr=?", new String[] {N_otdela},
                null, null,null);

        String[] from = new String[] {DBHelper.id_podr, DBHelper.fio};//берем этот набор данных
        int[] to = new int[] { R.id.l1, R.id.l2};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_txt, cursor2, from, to);
        lst3.setAdapter(scAdapter);

    }
    public void onClickBack2(View v){

        Intent intent = new Intent(TherdActivity.this, SecondActivity.class);
        intent.putExtra("position", pos);
        TherdActivity.this.finish();
        startActivity(intent);

    }
    public void onClickBack1(View v){

        Intent intent = new Intent(TherdActivity.this, MainActivity.class);
        TherdActivity.this.finish();
        startActivity(intent);

    }

}
