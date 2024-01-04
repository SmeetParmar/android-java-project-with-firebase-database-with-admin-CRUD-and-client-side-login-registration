package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class adminMenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    adminMenuAdapter adminMenuAdapter;

    Button logoutAdmin;
    FloatingActionButton addData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        recyclerView = findViewById(R.id.recyclerView);

        Integer orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }
        logoutAdmin = findViewById(R.id.logoutAdmin);

        logoutAdmin.setOnClickListener(v->{
            Intent toLogin = new Intent(this,login.class);
            startActivity(toLogin);
            Toast.makeText(getApplicationContext(),"Logged out successfully...",Toast.LENGTH_SHORT).show();
        });

        FirebaseRecyclerOptions<adminMenuModel> options =
                new FirebaseRecyclerOptions.Builder<adminMenuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), adminMenuModel.class)
                        .build();

        adminMenuAdapter = new adminMenuAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adminMenuAdapter);

        addData = findViewById(R.id.addData);

        addData.setOnClickListener(v->{
            Intent toAddItem = new Intent(this,add_item.class);
            startActivity(toAddItem);

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adminMenuAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminMenuAdapter.stopListening();
    }
}