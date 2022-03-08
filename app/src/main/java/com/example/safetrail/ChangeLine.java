package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Menu for admins to change information of lines.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class ChangeLine extends AppCompatActivity {

    private ListView listview2;
    private ArrayList<String> listText2;
    private ArrayAdapter<String> listAdapter2;
    private String clickedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_line);

        listview2 = (ListView) findViewById(R.id.list_view_change_line);
        listText2 = new ArrayList<>();
        listText2.add("Add Station");
        listText2.add("Remove Station");
        listAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listText2);
        listview2.setAdapter(listAdapter2);

        //Allows the admins to choose a line and perform the operation they want.
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();
                if ("Add Station".equals(clickedText)) {
                    openMainActivity();
                } else if ("Remove Station".equals(clickedText)) {
                    openTrainActivity2();
                }
            }
        });
    }

    /**
     * Opens the add station menu.
     */
    public void openMainActivity(){
        Intent intent = new Intent(this, AddStation.class);
        startActivity(intent);
    }

    /**
     * Opens the remove station menu.
     */
    public void openTrainActivity2(){
        Intent intent = new Intent(this, RemoveStation.class);
        startActivity(intent);
    }
}