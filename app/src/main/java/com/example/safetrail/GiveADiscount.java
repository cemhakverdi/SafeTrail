package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Menu for admins to apply discounts to certain passengers.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class GiveADiscount extends AppCompatActivity {

    private EditText customer_id;
    private EditText discount_amount;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_a_discount);

        customer_id = findViewById(R.id.editTextPricing_discount2);
        discount_amount = findViewById(R.id.editTextPricing_discount1);
        button = (Button) findViewById(R.id.button_proceed_discount);

        //Button to apply the discount.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount;

                amount = discount_amount.getText().toString();
                Toast.makeText(GiveADiscount.this,"Discount is given",Toast.LENGTH_SHORT).show();

                //Applies discount to account in database
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                databaseAccess.giveDiscount(customer_id.getText().toString(),amount);
                databaseAccess.close();

                customer_id.setText("");;
                discount_amount.setText("");
            }
        });
    }
}