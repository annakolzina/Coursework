package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choose extends AppCompatActivity {

    public static String EXTRA_TEXT_ID = "id";
    public String id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        String text = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        id = text;
    }

    public void onClick(View view){
        super.finish();
    }

    //выбор врача
    public void onClick2(View view){
        Intent intent = new Intent(Choose.this, ChooseSpecialist.class);
        intent.putExtra(Choose.EXTRA_TEXT_ID,id);
        startActivity(intent);
    }
    //требуется экстренная помощь
    public void onClick3(View view){
        Intent intent = new Intent(Choose.this, Help.class);
        startActivity(intent);
    }

}