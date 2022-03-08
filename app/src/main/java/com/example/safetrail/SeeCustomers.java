package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Shows all customer accounts.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class SeeCustomers extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> listText;
    private ListView all_customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_customers);

        //Gets information from database and displays it.
        all_customers = findViewById(R.id.list_view_customers);
        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        listText = databaseAccess.viewAllCustomers();
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listText);
        all_customers.setAdapter(adapter);
        databaseAccess.close();

    }
}