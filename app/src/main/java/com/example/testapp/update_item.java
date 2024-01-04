package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class update_item extends AppCompatActivity {

    EditText formItemName,formItemPrice,formItemDetails,formItemImage;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        formItemName = findViewById(R.id.formItemName);
        formItemPrice = findViewById(R.id.formItemPrice);
        formItemDetails = findViewById(R.id.formItemDetails);
        formItemImage = findViewById(R.id.formItemImage);

        btnUpdate = findViewById(R.id.formUpdateButton);

        formItemName.setText(getIntent().getStringExtra("itemName"));
        formItemPrice.setText(getIntent().getStringExtra("itemPrice"));
        formItemDetails.setText(getIntent().getStringExtra("itemDetails"));
        formItemImage.setText(getIntent().getStringExtra("itemImage"));

        btnUpdate.setOnClickListener(v -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name",formItemName.getText().toString());
            map.put("details",formItemDetails.getText().toString());
            map.put("price",formItemPrice.getText().toString());
            map.put("image",formItemImage.getText().toString());


            FirebaseDatabase.getInstance().getReferenceFromUrl(getIntent().getStringExtra("childId"))
                    .updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(update_item.this,"Data Updated Successfully.",Toast.LENGTH_SHORT).show();
                            Intent toAdminMenu = new Intent(update_item.this,adminMenuActivity.class);
                            startActivity(toAdminMenu);
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Error while updating data.",Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}