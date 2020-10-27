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
        final String id_pat = getIntent().getExtras().getString(EXTRA_TEXT_ID);
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
        ArrayList<String> data_array = new ArrayList<>();
        ArrayList<String> diagnosis_array = new ArrayList<>();
        String query =  "SELECT data, diagnosis"+
                " FROM documents, patients "+
                "WHERE documents.id_patient = patients.id_patient AND patients.code = '" + id_pat + "'";


        final ArrayList<String> data_array_final = new ArrayList<>();
        final ArrayList<String> diagnosis_array_final = new ArrayList<>();


        Cursor cursor = mdb.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String data = cursor.getString(0);
            String diagnosis = cursor.getString(1);
            String result = data + "  " + diagnosis;
            data_array.add(data);
            diagnosis_array.add(diagnosis);
            names.add(result);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int pos,
                                    long id) {
                Intent intent = new Intent(Documents.this, Documents_Result_Activity.class);
                intent.putExtra(Analyzes_result.EXTRA_DIAGNOSIS_ID, diagnosis_array_final.get(pos));
                intent.putExtra(Analyzes_result.EXTRA_DATA_ID, data_array_final.get(pos));
                intent.putExtra(Analyzes_result.EXTRA_TEXT_ID,id_pat);
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