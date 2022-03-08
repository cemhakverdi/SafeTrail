package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * List to show all lines available.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 27.04.2021
 */
public class ViewLines extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> listText,newlistText;
    private ListView all_lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lines);

        all_lines = findViewById(R.id.list_view_lines2);

        //Gets all lines from database and displays them.
        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        listText = databaseAccess.viewAllLines();
        newlistText = new ArrayList<String>();
        newlistText = listText;
        newlistText.remove(0);
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,newlistText);
        all_lines.setAdapter(adapter);
        databaseAccess.close();
    }
}