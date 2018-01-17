package com.example.kureichyk.phone;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by kureichyk on 12.01.2018.
 */

public class FourActivity extends Activity {
    Cursor cursor;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button button_mob, button_phone;
    String pos4; // для вызова активити два нужны данные позиции курсора, поэтому тягаем их за собой сюда и потом отдаем обратно
    String position1, position2, position3, tel_mob, tel_phone;
    TextView text_fio, text_doljnost, text_zvanie, text_mob, text_phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        button_mob =(Button) findViewById(R.id.button_mob);
        button_phone =(Button) findViewById(R.id.button_phone);
        text_fio =(TextView) findViewById(R.id.text_fio);
        text_doljnost =(TextView) findViewById(R.id.text_doljnost);
        text_zvanie =(TextView) findViewById(R.id.text_zvanie);
        text_mob =(TextView) findViewById(R.id.text_mob);
        text_phone =(TextView) findViewById(R.id.text_phone);
        FourfromDB();

    }

    public void FourfromDB(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        position3=getIntent().getStringExtra("position3");
        position2=getIntent().getStringExtra("position2");
        position1=getIntent().getStringExtra("position1");


//        pos=getIntent().getStringExtra("position");

        cursor = mSqLiteDatabase.query("contacts", null,
                "_id=?", new String[] {position3},
                null, null, null);
        cursor.moveToLast();
//        String s;
//        s=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.fio));
        text_fio.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.fio)));
        text_doljnost.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.doljnost)));
        text_zvanie.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.zvanie)));
        tel_mob="+375"+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.mob));
        tel_phone="+375"+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.kod))+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.phone));  // todo здесь надо обработать код - гдето есть ноль гдето нету
        tel_phone=tel_phone.replace("+3750", "+375"); // не работает
        text_mob.setText(tel_mob);
        text_phone.setText(tel_phone);




     //  button_mob.setText(1);



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

    public void onClickBack4(View v){

        Intent intent = new Intent(FourActivity.this, TherdActivity.class);
        intent.putExtra("position1", position1);
        intent.putExtra("position2", position2);
        FourActivity.this.finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
        startActivity(intent);

    }
    public void onClickHome(View v){

        Intent intent = new Intent(FourActivity.this, MainActivity.class);
        FourActivity.this.finish();
        startActivity(intent);

    }

public void onClickDial(View w){
//    String phone = "+34666777888";
    if (tel_mob.length()==13){
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel_mob, null));
    startActivity(intent);}
else button_mob.setText("нет номера");

}
    public void onClickDial2(View w){
//    String phone = "+34666777888";
        if (tel_phone.length()==13){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel_phone, null));
            startActivity(intent);}
        else button_phone.setText("нет номера");

    }
}



