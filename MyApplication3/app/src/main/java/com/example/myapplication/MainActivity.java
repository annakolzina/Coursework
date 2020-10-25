package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        //получаем значение введенное пользователем
        EditText text = (EditText) findViewById(R.id.editTextNumber2);
        Intent intent = new Intent(MainActivity.this, Authorization.class);
        intent.putExtra(Authorization.EXTRA_TEXT_ID, text.getText().toString());
        startActivity(intent);



    }

}