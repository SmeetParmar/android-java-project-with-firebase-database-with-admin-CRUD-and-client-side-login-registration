package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class individualActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cart");
    TextView itemName;
    TextView itemPrice;
    TextView itemDetails;
    ImageView itemImage;
    Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);

        itemName=findViewById(R.id.itemName);
        itemPrice= findViewById(R.id.itemPrice);
        itemDetails = findViewById(R.id.itemDetails);
        itemImage = findViewById(R.id.itemImage);

        itemName.setText(getIntent().getStringExtra("itemName"));
        itemPrice.setText(getIntent().getStringExtra("itemPrice"));
        itemDetails.setText(getIntent().getStringExtra("itemDetails"));

        Glide.with(this)
                .load(getIntent().getStringExtra("itemImage"))
                .into(itemImage);

        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(v->{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                String productId = "";
                Long products;

                Boolean isExist = false;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    SharedPreferences sp = getSharedPreferences("Session",MODE_PRIVATE);
                    String userName = sp.getString("userName","Null");

                    Long productQuantity = snapshot.child(userName).getChildrenCount();
                    if(productQuantity>0)
                    {
                        for(Integer i=1; i<=productQuantity;i++)
                        {
                            productId = "Product"+i;
//                        DatabaseReference individualProduct = databaseReference.child(productId);
//                        Log.i("Id",productId.toString());
//                        Log.i("Name",individualProduct.child("productName").getRef().toString());
//                        Log.i("Image",individualProduct.child("productImage").get().toString());
//                        Log.i("Price",individualProduct.child("productPrice").toString());
//                        Log.i("Name",snapshot.child(userName).child(productId).child("productName").getValue().toString());
//                        Log.i("Intent Name",getIntent().getStringExtra("itemName"));
//                        Log.i("Image",snapshot.child(userName).child(productId).child("productImage").getValue().toString());
//                        Log.i("Intent Image",getIntent().getStringExtra("itemImage"));
//                        Log.i("Price",snapshot.child(userName).child(productId).child("productPrice").getValue().toString());
//                        Log.i("Intent Price",getIntent().getStringExtra("itemPrice"));
                            if(snapshot.child(userName).child(productId).child("productName").getValue().toString().equals(getIntent().getStringExtra("itemName"))
                                    && snapshot.child(userName).child(productId).child("productImage").getValue().toString().equals(getIntent().getStringExtra("itemImage"))
                                    && snapshot.child(userName).child(productId).child("productPrice").getValue().toString().equals(getIntent().getStringExtra("itemPrice")) )
                            {
                                isExist = true;
                                break;
                            }
                            else
                            {
                                isExist = false;
                            }
                        }
                    }


                    if(isExist)
                    {
                        Toast.makeText(getApplicationContext(),"Item is already in your cart...",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        products = snapshot.child(userName).getChildrenCount() + 1;
                        productId = "Product"+products;

                        databaseReference.child(userName).child(productId).child("productName").setValue(getIntent().getStringExtra("itemName"));
                        databaseReference.child(userName).child(productId).child("productImage").setValue(getIntent().getStringExtra("itemImage"));
                        databaseReference.child(userName).child(productId).child("productQuantity").setValue(1);
                        databaseReference.child(userName).child(productId).child("productPrice").setValue(getIntent().getStringExtra("itemPrice"));

                        Toast.makeText(getApplicationContext(),"Item added to cart successfully...",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

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
            Intent toLogin = new Intent(individualActivity.this,login.class);
            startActivity(toLogin);
            Toast.makeText(getApplicationContext(),"Logged Out Successfully....",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

// https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyFEpb9JRl4vec-wq9OoNJOaA18KkiWQxInQ&usqp=CAU
// Soup is a versatile and comforting dish enjoyed worldwide. It typically comprises a flavorful liquid base enriched with various ingredients. Its variations are endless, from clear broths to thick, hearty stews. Generally, soups consist of a combination of vegetables, meats, or legumes simmered together to meld their flavors. They're often seasoned with herbs, spices, and aromatics to enhance taste and aroma. A fundamental aspect of soup-making is the broth or stock, acting as the foundation, providing depth and richness. Vegetarian soups often use vegetable broth, while meat-based soups derive their essence from simmering bones or meats for extended periods.Soups come in diverse forms, from consommés, which are clarified and delicate, to creamy bisques and chunky chowders. Some iconic examples include minestrone, chicken noodle, pho, gazpacho, and lentil soup, each with its distinct ingredients and cultural significance.
