package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Menu for admins to remove lines.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class RemoveLine extends AppCompatActivity {

    private Button remove_line,view_lines;
    private Spinner remove_line_ID;

    private ArrayAdapter adapter;
    private ArrayList<String> listText;
    private ListView all_lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_line);

        remove_line_ID = findViewById(R.id.spinner_remove_line);
        remove_line =  (Button) findViewById(R.id.remove_line_button);
        view_lines = (Button) findViewById(R.id.view_lines_button2);
        all_lines = findViewById(R.id.list_view_remove_line);

        //Gets all lines from database.
        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        adapter = new ArrayAdapter<String>(RemoveLine.this, android.R.layout.simple_spinner_item, databaseAccess.getAllLines());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        remove_line_ID.setAdapter(adapter);
        databaseAccess.close();

        //Button to remove the line with the wanted information
        remove_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;

                if( ! remove_line_ID.getSelectedItem().toString().equals("Select Lines")) {
                    id = Integer.parseInt(remove_line_ID.getSelectedItem().toString());
                    Toast.makeText(RemoveLine.this,"Line Removed",Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.deleteLine(id);
                    databaseAccess.close();
                }
                else{
                    Toast.makeText(RemoveLine.this,"Choose a line",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Shows all available lines.
        view_lines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                listText = databaseAccess.viewAllLines();
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,listText);
                all_lines.setAdapter(adapter);
                databaseAccess.close();
            }
        });
    }
}