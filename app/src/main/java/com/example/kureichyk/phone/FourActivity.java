package com.example.kureichyk.phone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kureichyk on 12.01.2018.
 */

public class FourActivity extends Activity {
    Cursor cursor, cursor2, cursor3;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;
    Button button_mob, button_phone;
    String pos4; // для вызова активити два нужны данные позиции курсора, поэтому тягаем их за собой сюда и потом отдаем обратно
    String position1, position2, position3, tel_mob, tel_phone, Path1, Path2, search;
    TextView text_fio, text_doljnost, text_zvanie, text_mob, text_phone, textWereYou;


    private final int IDD_LIST_OPERATORS = 1;
    AlertDialog.Builder ad;
    Context context;


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
        textWereYou = (TextView) findViewById(R.id.textWereYou);
        FourfromDB();

    }

    public void FourfromDB(){
        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        position3=getIntent().getStringExtra("position3");
        position2=getIntent().getStringExtra("position2");
        position1=getIntent().getStringExtra("position1");
        search=getIntent().getStringExtra("search");

        Path2=getIntent().getStringExtra("path2");
        Path1=getIntent().getStringExtra("path1");


//        pos=getIntent().getStringExtra("position");

        cursor = mSqLiteDatabase.query("contacts", null,
                "_id=?", new String[] {position3},
                null, null, null);
        cursor.moveToLast();
        String s,s2;
        s=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id_region));
        s2=cursor.getString(cursor.getColumnIndex(mDatabaseHelper.id_podr));

        cursor2 = mSqLiteDatabase.rawQuery("SELECT * FROM podrt  WHERE id_region3=? and id3=?" ,new String[]{s,s2});
        cursor2.moveToLast();

        cursor3 = mSqLiteDatabase.rawQuery("SELECT * FROM regiont  WHERE id2=? " ,new String[]{s});
        cursor3.moveToLast();
//        textWereYou.setText(Path2);
        s=cursor3.getString(cursor3.getColumnIndex(mDatabaseHelper.region));
        s2=cursor2.getString(cursor2.getColumnIndex(mDatabaseHelper.podr));
       textWereYou.setText("../"+s+"../"+s2);

        text_fio.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.fio)));
        text_doljnost.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.doljnost)));
        text_zvanie.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.zvanie)));
        tel_mob="+375"+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.mob));
        tel_phone="+375"+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.kod))+cursor.getString(cursor.getColumnIndex(mDatabaseHelper.phone));
        tel_phone=tel_phone.replace("+3750", "+375");
        tel_mob=tel_mob.replace("+375null", "нет номера");
        tel_mob=tel_mob.replace("+375NULL", "нет номера");

        text_mob.setText(tel_mob);
        text_phone.setText(tel_phone);

        if ((tel_mob.length()<13)&&(tel_mob.equals("нет номера")==false)){
            text_mob.setTextColor(Color.parseColor("#ff0000"));text_mob.setText("+375(код)"+tel_mob.substring(4)+"\n    MTS,Vel,Life?");}

        if (tel_phone.length()<13)
            text_phone.setText(tel_phone+"\nформат ном?");
   }

    public void onClickBack4(View v){
if (search!=null) {// если пришли из поиска
    Intent intent = new Intent(FourActivity.this, SearchActivity.class);
    FourActivity.this.finish();
    overridePendingTransition(R.anim.right_out,R.anim.left_in);
    startActivity(intent);

}
        else {
        Intent intent = new Intent(FourActivity.this, TherdActivity.class);
        intent.putExtra("position1", position1);
        intent.putExtra("position2", position2);
        intent.putExtra("position2", position2);
        intent.putExtra("path1", Path1);

        FourActivity.this.finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
        startActivity(intent);

    }}
    public void onClickHome(View v){

        Intent intent = new Intent(FourActivity.this, MainActivity.class);
        FourActivity.this.finish();
        startActivity(intent);

    }

public void onClickDial(View w){
    if (tel_mob.length()==11){
        showDialog(IDD_LIST_OPERATORS);
    }
    else
    if (tel_mob.length()==13){
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel_mob, null));
    startActivity(intent);}
    else button_mob.setText("нет номера");

}
    public void onClickDial2(View w){

        if (tel_phone.length()==13){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel_phone, null));
            startActivity(intent);}
        else button_phone.setText("нет номера");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (search!=null) {
            Intent intent = new Intent(FourActivity.this, SearchActivity.class);
            FourActivity.this.finish();
            overridePendingTransition(R.anim.right_out,R.anim.left_in);
            startActivity(intent);

        }
        else{
        Intent intent = new Intent(FourActivity.this, TherdActivity.class);
        intent.putExtra("position1", position1);
        intent.putExtra("position2", position2);
        intent.putExtra("position2", position2);
        intent.putExtra("path1", Path1);

        FourActivity.this.finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
        startActivity(intent);
    }}


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case IDD_LIST_OPERATORS:

                final String[] mOperatorsName ={"+37529"+tel_mob.substring(4), "+37533"+tel_mob.substring(4), "+37544"+tel_mob.substring(4), "+37525"+tel_mob.substring(4)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Выбирите код оператора"); // заголовок для диалога

                builder.setItems(mOperatorsName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mOperatorsName[item], null));
                        startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                builder.setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                return builder.create();

            default:
                return null;
        }
    }


}



