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

public class Documents extends AppCompatActivity {
    public static String EXTRA_TEXT_ID = "id";
    private DatabaseHelper db;
    private SQLiteDatabase mdb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        String id = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        ListView listView = (ListView) findViewById(R.id.list_document);
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
        String query =  "SELECT data, diagnosis"+
                " FROM documents, patients "+
                "WHERE documents.id_patient = patients.id_patient AND patients.code = '" + id + "'";


        Cursor cursor = mdb.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String data = cursor.getString(0);
            String diagnosis = cursor.getString(1);
            String result = data + "  " + diagnosis;
            names.add(result);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);


    }


    /*public void onClick(View view){
        super.finish();
    }*/
}