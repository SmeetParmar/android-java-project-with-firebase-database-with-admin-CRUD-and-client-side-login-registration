package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cartActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView cartRecyclerView;

    cartAdapter cartAdapter;
    Button toCheckOutPage;

    TextView total;

    Double gTotal = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toCheckOutPage = findViewById(R.id.toCheckOutPage);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        total = findViewById(R.id.total);

        SharedPreferences sp = getSharedPreferences("Session",MODE_PRIVATE);
        String userName = sp.getString("userName","Null");

        FirebaseRecyclerOptions<cartModel> options =
                new FirebaseRecyclerOptions.Builder<cartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart").child(userName),cartModel.class)
                        .build();

        cartAdapter = new cartAdapter(options);
        cartRecyclerView.setAdapter(cartAdapter);

        toCheckOutPage.setOnClickListener(v->{
            Intent toCheckout = new Intent(this,checkout.class);
            startActivity(toCheckout);
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("cart").child(userName);
        //Log.i("Data",databaseReference.toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            Integer i;
            String idName,stringPrice,price;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getChildrenCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"Your cart is empty...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    for(i=1;i<=snapshot.getChildrenCount();i++)
                    {
                        idName = "Product" + i;
                        stringPrice = snapshot.child(idName).child("productPrice").getValue().toString();
                        price = stringPrice.substring(0,stringPrice.indexOf("$"));
                        //Log.i("Price",price);
                        gTotal += Double.parseDouble(price);
                        //indiTotal = Double.parseDouble(price);
                        // gTotal += indiTotal;
                        //Log.i("Total",""+gTotal);
                    }
                }
                total.setText("Total : "+gTotal+"$");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //total.setText("Total : "+gTotal+"$");

    }

    @Override
    protected void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
            Intent toLogin = new Intent(cartActivity.this,login.class);
            startActivity(toLogin);
            Toast.makeText(getApplicationContext(),"Logged Out Successfully....",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}