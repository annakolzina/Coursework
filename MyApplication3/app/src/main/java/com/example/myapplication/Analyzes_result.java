package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Analyzes_result extends AppCompatActivity {

    public static String EXTRA_TEXT_ID = "id_pat";
    public static String EXTRA_DATA_ID = "id_data";
    public static String EXTRA_DIAGNOSIS_ID = "id_diag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzes_result);
        String code = getIntent().getExtras().getString(EXTRA_TEXT_ID);
        String data = getIntent().getExtras().getString(EXTRA_DATA_ID);
        String diagnosis = getIntent().getExtras().getString(EXTRA_DIAGNOSIS_ID);

        String res = code +"  " + data + "  " + diagnosis + "  ";
        TextView textView = (TextView) findViewById(R.id.textResult);
        textView.setText(res);
    }
}