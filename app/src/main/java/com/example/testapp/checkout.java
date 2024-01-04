package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkout extends AppCompatActivity {

    EditText firstName,lastName,mobileNumber,emailAddress,address,city,province,country,postalCode,cardNumber,expiryDate,securityCode,cardHolderName;
    CheckBox termsNCondition;
    Button placeOrder;
    Boolean validTermsAndConditions, validFirstName, validLastName, validMobileNumber, validEmailAddress, validAddress, validCity, validProvince, validCountry, validPostalCode, validCardNumber, validExpiryDate, validSecurityCode,validCardHolderName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        mobileNumber = findViewById(R.id.mobileNumber);
        emailAddress = findViewById(R.id.emailAddress);

        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        country = findViewById(R.id.country);
        postalCode = findViewById(R.id.postalCode);

        cardNumber = findViewById(R.id.cardNumber);
        expiryDate = findViewById(R.id.expiryDate);
        securityCode = findViewById(R.id.securityCode);
        cardHolderName = findViewById(R.id.cardHolderName);

        termsNCondition = findViewById(R.id.termNconditions);

        placeOrder = findViewById(R.id.placeOrder);


        placeOrder.setOnClickListener(view -> {
            if(firstName.getText().length() == 0)
            {
                firstName.requestFocus();
                firstName.setError("First name cannot be empty...");
                validFirstName = false;
            }
            else if(!firstName.getText().toString().matches("[a-zA-Z]+")){
                firstName.requestFocus();
                firstName.setError("Please enter only alphabets...");
                validFirstName = false;
            }
            else {
                validFirstName = true;
            }

            if(lastName.getText().length() == 0)
            {
                lastName.requestFocus();
                lastName.setError("Last name cannot be empty...");
                validLastName = false;
            }
            else if(!lastName.getText().toString().matches("[a-zA-Z]+")){
                lastName.requestFocus();
                lastName.setError("Please enter only alphabets...");
                validLastName = false;
            }
            else {
                validLastName = true;
            }

            String mobile = mobileNumber.getText().toString();
            if(mobile.length() == 0)
            {
                mobileNumber.requestFocus();
                mobileNumber.setError("Mobile Number cannot be empty...");
                validMobileNumber = false;
            }
            else if(!mobile.matches("^(\\+?1\\s?)?(\\()?\\d{3}(\\))?(-|\\s)?\\d{3}(-|\\s)\\d{4}$")){
                mobileNumber.requestFocus();
                mobileNumber.setError("Please enter a valid mobile number...");
                validMobileNumber = false;
            }
            else {
                validMobileNumber = true;
            }

            if(emailAddress.getText().length() == 0)
            {
                emailAddress.requestFocus();
                emailAddress.setError("Email cannot be empty...");
                validEmailAddress = false;
            }
            else if(!emailAddress.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                emailAddress.requestFocus();
                emailAddress.setError("Please enter a valid email address...");
                validEmailAddress = false;
            }
            else {
                validEmailAddress = true;
            }



            if(address.getText().length() == 0)
            {
                address.requestFocus();
                address.setError("Address cannot be empty...");
                validAddress = false;
            }
            else if(!address.getText().toString().matches("\\d+\\s+[a-zA-Z]+\\s+[a-zA-Z]+"))
            {
                address.requestFocus();
                address.setError("Please enter valid address");
                validAddress = false;
            }
            else
            {
                validAddress = true;
            }

            if(city.getText().length() == 0)
            {
                city.requestFocus();
                city.setError("City cannot be empty...");
                validCity = false;
            }
            else if(!city.getText().toString().matches("[a-zA-Z]+")){
                city.requestFocus();
                city.setError("City cannot contain digits...");
                validCity = false;
            }
            else {
                validCity = true;
            }

            if(province.getText().length() == 0)
            {
                province.requestFocus();
                province.setError("Province cannot be empty...");
                validProvince = false;
            }
            else if(!province.getText().toString().matches("[a-zA-Z]+")){
                province.requestFocus();
                province.setError("Province cannot contain digits...");
                validProvince = false;
            }
            else {
                validProvince = true;
            }

            if(country.getText().length() == 0)
            {
                country.requestFocus();
                country.setError("Country cannot be empty...");
                validCountry = false;
            }
            else if(!country.getText().toString().matches("[a-zA-Z]+")){
                country.requestFocus();
                country.setError("Country cannot contain digits...");
                validCountry = false;
            }
            else {
                validCountry = true;
            }

            if(postalCode.getText().length() == 0)
            {
                postalCode.requestFocus();
                postalCode.setError("Postal code cannot be empty...");
                validPostalCode = false;
            }
            else if(!postalCode.getText().toString().matches("[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d")){
                postalCode.requestFocus();
                postalCode.setError("Please enter a valid postal code...");
                validPostalCode = false;
            }
            else {
                validPostalCode = true;
            }


            if(cardNumber.getText().length() == 0)
            {
                cardNumber.requestFocus();
                cardNumber.setError("Card number cannot be empty...");
                validCardNumber = false;
            }
            else if(!cardNumber.getText().toString().matches("\\d{16}")){
                cardNumber.requestFocus();
                cardNumber.setError("Please enter a valid card number of 16 digits...");
                validCardNumber = false;
            }
            else {
                validCardNumber = true;
            }

            if(expiryDate.getText().length() == 0)
            {
                expiryDate.requestFocus();
                expiryDate.setError("Expiry date cannot be empty...");
                validExpiryDate = false;
            }
            else if(!expiryDate.getText().toString().matches("(0[1-9]|1[0-2])/(\\d{2})")){
                expiryDate.requestFocus();
                expiryDate.setError("Please enter a valid expiry date in MM/YY format...");
                validExpiryDate = false;
            }
            else {
                validExpiryDate = true;
            }

            if(securityCode.getText().length() == 0)
            {
                securityCode.requestFocus();
                securityCode.setError("Security Code cannot be empty...");
                validSecurityCode = false;
            }
            else if(!securityCode.getText().toString().matches("\\d{3}")){
                securityCode.requestFocus();
                securityCode.setError("Please enter a valid security code in 3 digits...");
                validSecurityCode = false;
            }
            else {
                validSecurityCode = true;
            }

            if(cardHolderName.getText().length() == 0)
            {
                cardHolderName.requestFocus();
                cardHolderName.setError("Cardholder name cannot be empty...");
                validCardHolderName = false;
            }
            else if(cardHolderName.getText().toString().matches("[a-zA-Z]")){
                cardHolderName.requestFocus();
                cardHolderName.setError("Card holder name cannot contain number...");
                validCardHolderName = false;
            }
            else {
                validCardHolderName = true;
            }

            if(!termsNCondition.isChecked())
            {
                Toast.makeText(getApplicationContext(), "Please Accept Terms and Conditions....", Toast.LENGTH_LONG).show();
                validTermsAndConditions = false;
            }
            else
            {
                validTermsAndConditions = true;
            }

            SharedPreferences sp = getSharedPreferences("Session",MODE_PRIVATE);
            String userName = sp.getString("userName","Null");

            if(validFirstName && validMobileNumber && validEmailAddress && validAddress && validCity
                    && validCountry && validPostalCode && validProvince && validCardNumber && validExpiryDate
                    && validSecurityCode && validCardHolderName && validTermsAndConditions) {
                AlertDialog.Builder builder = new AlertDialog.Builder(checkout.this);
                builder.setTitle("Order Summary");
                builder.setMessage("Are you sure you want to place your order?");

                builder.setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(checkout.this);
                        builder2.setTitle("Order Placed");
                        builder2.setMessage("Congratulations! Your order has been placed.");
                        builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent toMenu = new Intent(checkout.this,menuActivity.class);
                                startActivity(toMenu);
                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("cart")
                                .child(userName)
                                .removeValue();

                        AlertDialog dialog2 = builder2.create();
                        dialog2.show();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this,"Order Cancelled...",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
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
            Intent toLogin = new Intent(checkout.this,login.class);
            startActivity(toLogin);
            Toast.makeText(getApplicationContext(),"Logged Out Successfully....",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}