package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChooseSpecialist extends AppCompatActivity {

    public static String EXTRA_TEXT_ID = "id";
    public String id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_specialist);
        String text = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        id = text;
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int position,
                                    long id) {
                    Intent intent = new Intent(ChooseSpecialist.this, NameSpecialist.class);
                    intent.putExtra(NameSpecialist.EXTRA_NAME,position);
                    intent.putExtra(NameSpecialist.EXTRA_TEXT_ID,id);
                    startActivity(intent);
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    public void onClick(View view){
        super.finish();
    }
}