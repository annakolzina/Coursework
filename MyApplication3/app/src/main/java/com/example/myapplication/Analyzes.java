package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class Analyzes extends AppCompatActivity {

    public static String EXTRA_TEXT_ID = "id";
    private DatabaseHelper db;
    private SQLiteDatabase mdb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzes);
        String id = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        ListView listView = (ListView) findViewById(R.id.list_analyzes);
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
        String query =  "SELECT type,result"+
                " FROM analyzes, types_of_analyzes, patients"+
                " WHERE analyzes.id_analysis = types_of_analyzes.id_analysis AND patients.id_patient= analyzes.id_patient AND patients.code = '" + id + "'";


        Cursor cursor = mdb.rawQuery(query, null);
        final String id_pat = id;
        final ArrayList<String> data_array = new ArrayList<>();
        final ArrayList<String> diagnosis_array = new ArrayList<>();
        while (cursor.moveToNext()) {
            String data = cursor.getString(0);
            String diagnosis = cursor.getString(1);
            String result = data + "  " + diagnosis;
            names.add(result);
            data_array.add(data);
            diagnosis_array.add(diagnosis);
        }

        final ArrayList<String> final_data_array = data_array;
        final ArrayList<String> final_diagnosis_array = diagnosis_array;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int pos,
                                    long id) {
                Intent intent = new Intent(Analyzes.this, Analyzes_result.class);
                intent.putExtra(Analyzes_result.EXTRA_DIAGNOSIS_ID, diagnosis_array.get(pos));
                intent.putExtra(Analyzes_result.EXTRA_DATA_ID, data_array.get(pos));
                intent.putExtra(Analyzes_result.EXTRA_TEXT_ID,id_pat);
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);


    }

    public void onClick(View view){
        super.finish();
    }
}