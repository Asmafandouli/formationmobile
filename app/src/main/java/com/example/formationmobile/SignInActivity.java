package com.example.formationmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView gotosignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        gotosignup=findViewById(R.id.gotosignup);
        gotosignup.setOnClickListener(v->{ startActivity(new Intent(SignInActivity.this,MainActivity.class));});

    }
}