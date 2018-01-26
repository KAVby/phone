package com.example.kureichyk.phone;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by kureichyk on 24.01.2018.
 */

public class SearchActivity extends Activity{
    EditText textSearch;
    ListView listsearch;
    Cursor cursor, cursor2;
    DBHelper mDatabaseHelper;
    SQLiteDatabase mSqLiteDatabase;
    SimpleCursorAdapter scAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textSearch=(EditText) findViewById(R.id.textSearch);
        listsearch=(ListView) findViewById(R.id.listsearch);




        mDatabaseHelper = new DBHelper(this, "phone.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
//        cursor = mSqLiteDatabase.query("contacts", null,
//                null, null,
//                null, null, "fio ASC");Z
        cursor = mSqLiteDatabase.rawQuery("SELECT * FROM contacts  WHERE fio is not null ORDER BY fio" , null);

        String[] from = new String[]{DBHelper.fio, DBHelper.mob, DBHelper.zvanie};//берем этот набор данных
        int[] to = new int[]{R.id.text1, R.id.text2, R.id.text3};// и вставляем их сюда
        scAdapter = new SimpleCursorAdapter(this, R.layout.list3, cursor, from, to);





        // установка слушателя изменения текста
        textSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            // при изменении текста выполняем фильтрацию
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scAdapter.getFilter().filter(s.toString());
            }
        });

        // устанавливаем провайдер фильтрации
        scAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                //  cursor=mSqLiteDatabase.rawQuery("SELECT * FROM contacts  WHERE fio like ? or mob like ? " , new String[]{"%"+constraint.toString()+"%", "%"+constraint.toString()+"%"});

                if (constraint == null || constraint.length() == 0) {
                    return mSqLiteDatabase.rawQuery("SELECT * FROM contacts  WHERE fio is not null ORDER BY fio" , null);
                }
                else {

                    return mSqLiteDatabase.rawQuery("SELECT * FROM contacts  WHERE fio like ? or mob like ? and fio is not null ORDER BY fio" , new String[]{"%"+constraint.toString()+"%", "%"+constraint.toString()+"%"});


                }
            }
        });


        listsearch.setAdapter(scAdapter);

        listsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                //               Butupdate.setText((Long.toString(idd)+" + "+Integer.toString(position+1)));

                Intent intent = new Intent(SearchActivity.this, FourActivity.class);
                intent.putExtra("position3", Long.toString(idd));   //передаю данные об ид списка
                intent.putExtra("search", "search"); // и то что мы пришли в четвертое активити из поиска
                SearchActivity.this.finish();
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                startActivity(intent);





            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        SearchActivity.this.finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);
        startActivity(intent);
    }
}
