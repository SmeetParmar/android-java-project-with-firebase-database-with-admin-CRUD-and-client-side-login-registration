package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class menuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    menuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        Integer orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        FirebaseRecyclerOptions<menuModel> options =
                new FirebaseRecyclerOptions.Builder<menuModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), menuModel.class)
                        .build();

        menuAdapter = new menuAdapter(options,getApplicationContext());
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menuAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        menuAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        if(id == R.id.optionMenu)
        {
            Intent toMenu = new Intent(this,menuActivity.class);
            startActivity(toMenu);
        }

        if(id == R.id.optionCart)
        {
            Intent toCart = new Intent(this,cartActivity.class);
            startActivity(toCart);
        }

        if(id == R.id.optionCheckout)
        {
            Intent toCheckout = new Intent(this,checkout.class);
            startActivity(toCheckout);
        }

        if(id == R.id.optionLogout)
        {
            Intent toLogin = new Intent(menuActivity.this,login.class);
            startActivity(toLogin);
            Toast.makeText(getApplicationContext(),"Logged Out Successfully....",Toast.LENGTH_SHORT).show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//            builder.setTitle("Logout");
//            builder.setMessage("Are you sure you want to Logout?");
//
//            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent toLogin = new Intent(menuActivity.this,register.class);
//                    startActivity(toLogin);
//                    Toast.makeText(getApplicationContext(),"Logged Out Successfully....",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(),"Opration Cancelled....",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }
        return true;
    }
}