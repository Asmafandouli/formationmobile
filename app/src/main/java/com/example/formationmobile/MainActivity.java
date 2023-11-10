package com.example.formationmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView gotosignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotosignin=findViewById(R.id.gotosignin);
        gotosignin.setOnClickListener(v->{ startActivity(new Intent(MainActivity.this,SignInActivity.class));});

    }
}