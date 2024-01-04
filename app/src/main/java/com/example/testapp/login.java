package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    EditText userName,password;
    Button login;
    TextView toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        toRegister = findViewById(R.id.toRegister);

        toRegister.setOnClickListener(v->{
            Intent toRegisterPage = new Intent(this, register.class);
            startActivity(toRegisterPage);
        });

//        TextView testInt = findViewById(R.id.testInt);
//        Button testBtn = findViewById(R.id.testBtn);
//
//        final Integer[] val = {Integer.parseInt(testInt.getText().toString())};
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                val[0]++;
//                testInt.setText(val[0].toString());
//            }
//        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        String idName;
                        Integer i;
                        Boolean same = false,isAdmin = false;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(i=1;i<=snapshot.getChildrenCount();i++)
                            {
                                String newId = "User" + i;
                                if(snapshot.child(newId).child("username").getValue().equals(userName.getText().toString()) && snapshot.child(newId).child("password").getValue().equals(password.getText().toString()))
                                {
                                    if(snapshot.child(newId).child("username").getValue().equals("Smeet") && snapshot.child(newId).child("password").getValue().equals("111"))
                                    {
                                        isAdmin = true;
                                    }
                                    same = true;
                                }
                            }

                            if(same)
                            {
                                Intent toView;
                                if(isAdmin)
                                {
                                    toView = new Intent(login.this, adminMenuActivity.class);
                                    Toast.makeText(getApplicationContext(), "Welcome Admin...", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    SharedPreferences sharedPreferences = getSharedPreferences("Session",MODE_PRIVATE);
                                    SharedPreferences.Editor data = sharedPreferences.edit();
                                    data.putString("userName",userName.getText().toString());
                                    data.apply();
                                    toView = new Intent(login.this, menuActivity.class);
                                    Toast.makeText(getApplicationContext(), "Welcome " + userName.getText().toString() + "...", Toast.LENGTH_LONG).show();
                                }
                                startActivity(toView);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Username or Password is wrong...",Toast.LENGTH_LONG).show();
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