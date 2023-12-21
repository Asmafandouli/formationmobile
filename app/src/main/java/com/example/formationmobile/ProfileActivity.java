package com.example.formationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
 private Button btnedit,btnlogout;
 private EditText fullName,email,cin,phone;
 private FirebaseAuth firebaseAuth;
 private FirebaseDatabase firebaseDatabase;
 private FirebaseUser loggedUser;
 private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullName=findViewById(R.id.fullnameP);
        email=findViewById(R.id.emailP);
        cin=findViewById(R.id.cinP);
        phone=findViewById(R.id.telP);
        btnedit=findViewById(R.id.btnEdit);
        btnlogout=findViewById(R.id.btnLogOut);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        loggedUser=firebaseAuth.getCurrentUser();
        reference=firebaseDatabase.getReference().child("users").child(loggedUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String nameS=snapshot.child("fullName").getValue().toString();
                    String emailS=snapshot.child("email").getValue().toString();
                    String cinS=snapshot.child("cin").getValue().toString();
                    String phoneS=snapshot.child("tel").getValue().toString();

                    fullName.setText(nameS);
                    email.setText(emailS);
                    cin.setText(cinS);
                    phone.setText(phoneS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        btnlogout.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this,SignInActivity.class));
            SharedPreferences shard =getSharedPreferences("checkBox",MODE_PRIVATE);
            SharedPreferences.Editor editor=shard.edit();
            editor.putBoolean("remember",false);
            editor.apply();
        });

    }
}