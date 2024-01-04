package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add_item extends AppCompatActivity {

    EditText itemName,itemImage,itemDetails,itemPrice;
    Button addData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.formItemName);
        itemPrice = findViewById(R.id.formItemPrice);
        itemDetails = findViewById(R.id.formItemDetails);
        itemImage = findViewById(R.id.formItemImage);

        addData = findViewById(R.id.formAddButton);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("name",itemName.getText().toString());
                map.put("price",itemPrice.getText().toString());
                map.put("details",itemDetails.getText().toString());
                map.put("image",itemImage.getText().toString());

                // https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvW1bpCMUWgSYQj5JZ8H0_UHHjjDKc66gRjQ&usqp=CAU
                // Biryani is a flavorful and aromatic dish originating from the Indian subcontinent, renowned for its fragrant rice and blend of spices. It's a complete meal in itself, combining meat, rice, and a myriad of seasonings. The dish boasts regional variations across India, each offering a unique taste. Typically, it involves layering marinated meat, such as chicken or mutton, with parboiled rice, herbs, and saffron-infused milk, then slow-cooking it. Biryani's rich history dates back centuries, believed to have been introduced by Persian merchants to the Indian royalty. It has evolved into an integral part of celebrations and gatherings, cherished for its complexity of flavors and textures. The cooking process involves a delicate balance of spices like cardamom, cloves, and cinnamon, elevating it to a symphony of tastes. Biryani is often accompanied by cooling accompaniments like raita or yogurt to complement its spiciness. Its popularity transcends borders, embraced worldwide for its irresistible blend of savory goodness.

                FirebaseDatabase.getInstance().getReference().child("products").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(add_item.this,"Data inserted successfully...",Toast.LENGTH_LONG).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(add_item.this,"Error Inserting Data...",Toast.LENGTH_LONG).show();
                            }
                        });
                itemImage.setText("");
                itemPrice.setText("");
                itemDetails.setText("");
                itemName.setText("");
            }
        });
    }
}