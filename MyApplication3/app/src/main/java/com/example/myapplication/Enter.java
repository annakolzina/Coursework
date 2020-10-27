package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Enter extends AppCompatActivity {

    private DatabaseHelper db;
    private Cursor cursor;
    public final static String EXTRA_TEXT_DATA = "name";
    public static String EXTRA_TEXT_ID = "id";
    public static String EXTRA_TEXT_TIME = "id_time";
    public final static String EXTRA_SPECIALIST_ID = "specialist_id";
    private SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        TextView textView = (TextView) findViewById(R.id.textEnter);


        String id = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        String id_sp = getIntent().getExtras().getString(EXTRA_SPECIALIST_ID);
        String data = getIntent().getExtras().getString(EXTRA_TEXT_DATA);
        String time = getIntent().getExtras().getString(EXTRA_TEXT_TIME);




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


        String query =  "INSERT INTO visits(code_patient, id_doctors, id_data, id_time) "+
                        "VALUES(" + id + ", " + id_sp  + ", '" + data + "', '" + time + "');";



        mdb.execSQL(query);
        textView.setText("Вы успешно записались на " + data + "  в  " + time);


        String query2 = "SELECT * " +
                        "FROM visits;";


        Cursor cursor = mdb.rawQuery(query2, null);

        cursor.moveToFirst();
        while (cursor.moveToNext())
            System.out.println(
                    cursor.getString(0) + " " + cursor.getString(1) + " " +
                    cursor.getString(2) + "  "+ cursor.getString(3) +"  " + cursor.getString(4));



        cursor.close();
        mdb.close();
        db.close();
    }


}