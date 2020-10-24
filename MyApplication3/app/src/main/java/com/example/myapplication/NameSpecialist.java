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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class NameSpecialist extends AppCompatActivity {


    public final static String EXTRA_NAME= "name";
    public static String EXTRA_TEXT_ID = "id";
    public static final String[] specialty = {"кардиолог", "стоматолог","терапевт"};
    private DatabaseHelper db;
    private SQLiteDatabase mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_specialist);
        String text = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        int position = getIntent().getExtras().getInt(EXTRA_NAME);
        ArrayList<String> names = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.list_names);
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


        String query =  "SELECT first_name, last_name " +
                "FROM personal, doctors " +
                "WHERE  personal.id_personal = doctors.id_doctors AND type = " + specialty[position];


        Cursor cursor = mdb.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            names.add(name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int pos,
                                    long id) {
                Intent intent = new Intent(NameSpecialist.this, Visit.class);
                intent.putExtra(Visit.EXTRA_SPECIALIST,pos);
                intent.putExtra(Visit.EXTRA_TEXT_ID,id);
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);

        cursor.close();
        db.close();

        /*SQLiteOpenHelper clinicDatabaseHelper = new DatabaseEmpHelper(this);
        try {
            db = clinicDatabaseHelper.getReadableDatabase();
            //получить талицу из всех специалистов выбранного типа
            cursor = db.query("EMPLOYEES",
                    new String[]{"LAST_NAME", "FIRST_NAME"},
                    "TYPE = ?",
                    new String[]{specialty[position]},
                    null, null, null);


            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                names.add(name);
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, names);
            listView.setAdapter(adapter);


            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> listView,
                                        View itemView,
                                        int pos,
                                        long id) {
                    Intent intent = new Intent(NameSpecialist.this, Visit.class);
                    intent.putExtra(Visit.EXTRA_SPECIALIST,pos);
                    intent.putExtra(Visit.EXTRA_TEXT_ID,id);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(itemClickListener);


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }


    /*public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();    */
}