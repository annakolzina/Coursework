package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Documents_Result_Activity extends AppCompatActivity {
    public static String EXTRA_TEXT_ID = "id";
    public static String EXTRA_DIAGNOSIS_ID = "diagnosis";
    public static String EXTRA_DATA_ID = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents__result_);
        String id_pat = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        String data = getIntent().getExtras().getString(EXTRA_DATA_ID);
        String diagnosis = getIntent().getExtras().getString(EXTRA_DIAGNOSIS_ID);

        TextView textViewRef = (TextView) findViewById(R.id.textViewDoc);
        TextView textViewResult = (TextView) findViewById(R.id.textViewDoc2);
    }

    /*public void onClick(View view){
        super.finish();
    }*/
}