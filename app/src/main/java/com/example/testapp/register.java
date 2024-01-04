package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    EditText username,password,confirmPassword;
    Button register;
    TextView toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.register);
        toLogin = findViewById(R.id.toLogin);

        toLogin.setOnClickListener(v->{
            Intent toLoginPage = new Intent(this, login.class);
            startActivity(toLoginPage);
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(username.getText().toString().equals("") || password.getText().toString().equals("") || confirmPassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty...", Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    confirmPassword.requestFocus();
                    confirmPassword.setError("Password and Conform Password must be same...");
                }
                else
                {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        Long id;
                        String idName;
                        Integer i;
                        Boolean same = false;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            id = snapshot.getChildrenCount() + 1;
                            idName = "User" + id;

                            for(i=1;i<=snapshot.getChildrenCount();i++)
                            {
                                String newId = "User" + i;
                                if(snapshot.child(newId).child("username").getValue().equals(username.getText().toString()) && snapshot.child(newId).child("password").getValue().equals(password.getText().toString()))
                                {
                                    same = true;
                                }
                            }

                            if(same)
                            {
                                Toast.makeText(getApplicationContext(),"You are already regsitered, Try Loggin In...",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                databaseReference.child(idName).child("username").setValue(username.getText().toString().trim());
                                databaseReference.child(idName).child("password").setValue(password.getText().toString().trim());
                                Toast.makeText(getApplicationContext(),"Registered Successfully, Please Login...",Toast.LENGTH_LONG).show();
                                Intent toLoginPage = new Intent(getApplicationContext(), login.class);
                                startActivity(toLoginPage);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}