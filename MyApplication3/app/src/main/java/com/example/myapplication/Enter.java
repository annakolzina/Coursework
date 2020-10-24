package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Enter extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    private TextView textView = (TextView) findViewById(R.id.textView5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        try {
            //получить талицу из всех специалистов выбранного типа
            cursor = db.query("VISITS",
                    new String[]{"DATA"},
                    "_id=?",
                    new String[]{Integer.toString(1)},
                    null, null, null);


            if(cursor.moveToFirst()){
                String aa = cursor.getString(0);
                textView.setText(aa);
            }



        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        cursor.close();
        db.close();
    }
}