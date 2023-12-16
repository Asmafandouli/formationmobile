package com.example.formationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private TextView gotosignup;
    private EditText email,password;
    private Button btnsignin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        email=findViewById(R.id.emailSignIn);
        password=findViewById(R.id.passwordSignIn);
        btnsignin=findViewById(R.id.btnSignIn);

        gotosignup=findViewById(R.id.gotosignup);
        firebaseAuth=FirebaseAuth.getInstance();
        gotosignup.setOnClickListener(v->{ startActivity(new Intent(SignInActivity.this,MainActivity.class));});
       btnsignin.setOnClickListener(v -> {
           firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       checkEmailVerification();
                   }
                else{
                       Toast.makeText(SignInActivity.this, "please verify that your data is correct", Toast.LENGTH_SHORT).show();
                   }
           };
       });
    });}

    private void checkEmailVerification() {
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser.isEmailVerified()){
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));

        } else{
            Toast.makeText(this, "please check your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}