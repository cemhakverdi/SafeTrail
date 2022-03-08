package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Menu for admins to ban customers.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class BanCustomer extends AppCompatActivity {

    private EditText customer_id;
    private Button ban_button;
    private Button unban_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_customer);

        customer_id = findViewById(R.id.editText_ban_customers);
        ban_button = (Button) findViewById(R.id.button_ban_customers);
        unban_button = (Button) findViewById(R.id.button_ban_customers2);

        //Allows the admins to choose and ban a customer.
        ban_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(customer_id.getText().toString().equals(""))) {
                    String cust_num = customer_id.getText().toString();

                    Toast.makeText(BanCustomer.this, "Customer Banned", Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.banCustomer(cust_num);
                    databaseAccess.close();
                    customer_id.setText("");
                    ;
                }
            }
        });

        //Allows the admins to unban a customer.
        unban_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(customer_id.getText().toString().equals(""))) {
                    String cust_num = customer_id.getText().toString();

                    Toast.makeText(BanCustomer.this, "Customer Unbanned", Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.removeBan(cust_num);
                    databaseAccess.close();
                    customer_id.setText("");
                    ;
                }
            }
        });
    }
}