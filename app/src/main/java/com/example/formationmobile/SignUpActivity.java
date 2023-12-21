package com.example.formationmobile;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formationmobile.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private TextView gotosignin;
    private TextView goToSignIn;
    private EditText fullNameet, emailet, telet, cinet, passet;
    private Button buttonSignUp;
    private String fullNameS, emailS, teleS, cinS, passS;
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gotosignin = findViewById(R.id.gotosignin);
        gotosignin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        });


        fullNameet = findViewById(R.id.fullname);
        emailet = findViewById(R.id.email);
        cinet = findViewById(R.id.cin);
        telet = findViewById(R.id.tel);
        passet = findViewById(R.id.pass);

        buttonSignUp = findViewById(R.id.buttonSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        buttonSignUp.setOnClickListener(v -> {
            if (validate()) {
                progressDialog.setMessage("Please wait...!!");
                progressDialog.show();
                String emailUser = emailet.getText().toString().trim();
                String passwordUser = passet.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SendEmailverification();


                        } else {
                            Toast.makeText(SignUpActivity.this, "Register Failed !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }

        });

    }

    private void SendEmailverification() {
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser != null){
            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        //send user data
                        sendUserData();
                        Toast.makeText(SignUpActivity.this, "registration done please check your email", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity( new Intent(SignUpActivity.this,SignInActivity.class));
                    } else{
                        Toast.makeText(SignUpActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myref=firebaseDatabase.getReference("users");
        User u1=new User(fullNameS,emailS,cinS,teleS,passS);
        myref.child(""+firebaseAuth.getUid()).setValue(u1);
    }

    private boolean validate() {
        boolean result = false;
        fullNameS = fullNameet.getText().toString();
        emailS = emailet.getText().toString();
        cinS = cinet.getText().toString();
        teleS = telet.getText().toString();
        passS = passet.getText().toString();
        if (fullNameS.isEmpty() || fullNameS.length()<7){
            fullNameet.setError("Full Name is invalid!");
        } else if (!isValidEmail(emailS)) {
            emailet.setError("Email is invalid!");

        } else if (cinS.isEmpty() || cinS.length() != 8 ) {
            cinet.setError("Cin is invalid!");

        } else if (teleS.isEmpty() || teleS.length() != 8 ) {
            telet.setError("Phone is invalid!");
        } else if (passS.isEmpty() || passS.length()<7) {
            passet.setError("Password is invalid");
        } else {
            result = true ;
        }
        return result;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
