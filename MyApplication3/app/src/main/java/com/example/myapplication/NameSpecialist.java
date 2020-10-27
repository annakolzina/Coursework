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
    private DatabaseHelper db;
    private SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_specialist);
        String id_pat = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        int position = getIntent().getExtras().getInt(EXTRA_NAME);
        ListView listView = (ListView) findViewById(R.id.list_names);
        String[] strings = new String[]{"кардиолог", "стоматолог", "терапевт"};
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
        String query =  "SELECT id_doctors, last_name, first_name, middle_name " +
                "FROM personal, doctors " +
                "WHERE  personal.id_personal = doctors.id_doctors AND type = '" + strings[position] + "'" ;

        Cursor cursor = mdb.rawQuery(query, null);
        ArrayList<String> id_doctors = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id_doctor = cursor.getString(0);
            String lname = cursor.getString(1);
            String fname = cursor.getString(2);
            String mname = cursor.getString(3);
            String result = lname + "  " + fname + "  " + mname;
            names.add(result);
            id_doctors.add(id_doctor);
        }
        final ArrayList<String> f_doctors = id_doctors;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        final String id_pat2 = id_pat;

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int pos,
                                    long id) {
                Intent intent = new Intent(NameSpecialist.this, Visit.class);
                intent.putExtra(Visit.EXTRA_SPECIALIST,pos);
                intent.putExtra(Visit.EXTRA_SPECIALIST_ID, f_doctors.get(pos));
                intent.putExtra(Visit.EXTRA_TEXT_ID,id_pat2);
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