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
import android.widget.TextView;
import android.widget.Toast;

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
        int _idPat = getIntent().getExtras().getInt(EXTRA_TEXT_ID);
        int _idEmp = getIntent().getExtras().getInt(EXTRA_SPECIALIST_ID);

    }

    public void onClick44(View view) {
        int _idPat = getIntent().getExtras().getInt(EXTRA_TEXT_ID);
        int _idEmp = getIntent().getExtras().getInt(EXTRA_SPECIALIST_ID);
        EditText editText = (EditText) findViewById(R.id.editTextDate);

        mdb.execSQL(ROW1);
        insertVisits(db, _idPat, _idEmp, editText.toString());
        TextView textView = (TextView) findViewById(R.id.textView4);
        Intent intent = new Intent(Visit.this, Enter.class);
        startActivity(intent);

    }

        public static void insertVisits (SQLiteDatabase db,int idp, int idd, String data){
            ContentValues empValues = new ContentValues();
            empValues.put("idPATIENT", idp);
            empValues.put("idDOCTOR", idd);
            empValues.put("DATA", data);
            db.insert("VISITS", null, empValues);
        }
    }