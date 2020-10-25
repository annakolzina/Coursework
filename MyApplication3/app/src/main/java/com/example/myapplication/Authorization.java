package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Authorization extends AppCompatActivity {

    public static String EXTRA_TEXT_ID = "id";
    private DatabaseHelper db;
    private SQLiteDatabase mdb;
    public String id = "";
    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        id = text;
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_authorization);
        Button button = (Button) findViewById(R.id.buttonAuthorization);
        TextView textView = (TextView) findViewById(R.id.textView);

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


        String query =  "SELECT first_name " +
                        "FROM patients " +
                        "WHERE code = " + id;

        Cursor cursor = mdb.rawQuery(query, null);
        cursor.moveToFirst();
        String res;
        if (!cursor.isAfterLast()) {
            res = cursor.getString(0);
        } else
            res = "";
        cursor.close();
        db.close();

        if(!res.isEmpty()){
            button.setText("Продолжить");
            textView.setText(String.format("%s%s", res, " , Вы успешно авторизованы в системе"));
            status = true;
        }
        else {
            button.setText("Назад");
            textView.setText("Пользователь не найден");
        }
    }
    public void onClick(View view){
        if(status) {
            Intent intent = new Intent(Authorization.this, Choose.class);
            intent.putExtra(Choose.EXTRA_TEXT_ID, id);
            startActivity(intent);
        }else
            super.finish();
    }
}


