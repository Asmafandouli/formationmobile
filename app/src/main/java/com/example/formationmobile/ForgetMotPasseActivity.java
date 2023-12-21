package com.example.formationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetMotPasseActivity extends AppCompatActivity {
    private Button btnReset,btnBack;
  private EditText email;
  private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_mot_passe);


        btnReset=findViewById(R.id.btnReset);
        btnBack=findViewById(R.id.backToMain);
        email=findViewById(R.id.emailReset);
        firebaseAuth=FirebaseAuth.getInstance();

        btnBack.setOnClickListener(v -> {
              startActivity(new Intent(ForgetMotPasseActivity.this,SignInActivity.class));
        });

        btnReset.setOnClickListener(v -> {
            firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(ForgetMotPasseActivity.this,SignInActivity.class));
                        Toast.makeText(ForgetMotPasseActivity.this, "password reset email sent", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        Toast.makeText(ForgetMotPasseActivity.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

}