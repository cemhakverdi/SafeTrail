package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * List to show all trains available.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 27.04.2021
 */
public class ViewTrains extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> listText;
    private ListView all_trains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trains);

        all_trains = findViewById(R.id.list_view_add_train2);

        //Gets all trains from database and displays them.
        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        listText = databaseAccess.viewAllTrains();
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listText);
        all_trains.setAdapter(adapter);
        databaseAccess.close();
    }
}