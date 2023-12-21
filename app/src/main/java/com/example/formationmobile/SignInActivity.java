package com.example.formationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private TextView gotosignup,forget;
    private EditText email,password;
    private Button btnsignin;
    private CheckBox remember;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        email=findViewById(R.id.emailSignIn);
        password=findViewById(R.id.passwordSignIn);
        btnsignin=findViewById(R.id.btnSignIn);

        gotosignup=findViewById(R.id.gotosignup);
        forget=findViewById(R.id.forget);
        remember=findViewById(R.id.remember);
        firebaseAuth=FirebaseAuth.getInstance();
        gotosignup.setOnClickListener(v->{ startActivity(new Intent(SignInActivity.this, SignUpActivity.class));});
        forget.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgetMotPasseActivity.class));
        });


        SharedPreferences shard =getSharedPreferences("checkBox",MODE_PRIVATE);
        boolean isRemember=shard.getBoolean("remember",false);
        if(isRemember){
            startActivity(new Intent(SignInActivity.this,ProfileActivity.class) );
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if( buttonView.isChecked()){
                   SharedPreferences shard =getSharedPreferences("checkBox",MODE_PRIVATE);
                   SharedPreferences.Editor editor=shard.edit();
                   editor.putBoolean("remember",true);
                   editor.apply();
               } else {
                   SharedPreferences shard =getSharedPreferences("checkBox",MODE_PRIVATE);
                   SharedPreferences.Editor editor=shard.edit();
                   editor.putBoolean("remember",false);
                   editor.apply();
               }
            }
        });

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
            startActivity(new Intent(SignInActivity.this, ProfileActivity.class));

        } else{
            Toast.makeText(this, "please check your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}