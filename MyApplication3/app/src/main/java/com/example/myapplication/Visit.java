package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Visit extends AppCompatActivity {


    public final static String EXTRA_SPECIALIST = "name";
    public static String EXTRA_TEXT_ID = "id";
    public final static String EXTRA_SPECIALIST_ID = "id_sp";
    private DatabaseHelper db;
    private SQLiteDatabase mdb;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        final String id_pat = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        int position = getIntent().getExtras().getInt(EXTRA_SPECIALIST);
        final String id_sp = getIntent().getExtras().getString(EXTRA_SPECIALIST_ID);
        ListView listView = (ListView) findViewById(R.id.list_time);
        System.out.println("***************************visitvisit");
        System.out.println(id_pat);
        System.out.println("***************************");
        db = new DatabaseHelper(this);
        try {
            db.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb = db.getWritableDatabase();
        } catch (SQLiteException mSQLException) {
            throw mSQLException;
        }
        ArrayList<String> names = new ArrayList<>();
        String query =  "SELECT data, time "+"FROM doctors, time "+"WHERE doctors.id_doctors = time.id_doctors AND doctors.id_doctors = '"+ id_sp +"'";


        Cursor cursor = mdb.rawQuery(query, null);
        ArrayList<String> time_array = new ArrayList<>();
        ArrayList<String> data_array = new ArrayList<>();
        while (cursor.moveToNext()) {
            String data = cursor.getString(0);
            String time = cursor.getString(1);
            String result = data + "  " + time;
            time_array.add(time);
            data_array.add(data);
            names.add(result);
        }

        final ArrayList<String> final_time_array = time_array;
        final ArrayList<String> final_data_array = data_array;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);


        //передаем в активити дату , время , id специалиста, код пауиента
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int pos,
                                    long id) {
                Intent intent = new Intent(Visit.this, Enter.class);
                intent.putExtra(Enter.EXTRA_SPECIALIST_ID, id_sp);
                intent.putExtra(Enter.EXTRA_TEXT_ID, id_pat);
                intent.putExtra(Enter.EXTRA_TEXT_TIME, final_time_array.get(pos));
                intent.putExtra(Enter.EXTRA_TEXT_DATA, final_data_array.get(pos));
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
        cursor.close();
        mdb.close();
        db.close();
    }

    public void onClick(View view){
        super.finish();
    }

}