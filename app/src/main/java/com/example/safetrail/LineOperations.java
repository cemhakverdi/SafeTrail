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
 * Menu for admins to change information about lines.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class LineOperations extends AppCompatActivity {
    private ListView listview3;
    private ArrayList<String> listText3;
    private ArrayAdapter<String> listAdapter3;
    private String clickedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_operations);

        listview3 = (ListView) findViewById(R.id.list_view3);
        listText3 = new ArrayList<>();
        listText3.add("Add Line");
        listText3.add("Remove Line");
        listText3.add("Station Operations");
        listText3.add("View Lines");
        listAdapter3 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listText3);
        listview3.setAdapter(listAdapter3);

        //Allows admins to choose the wanted operation.
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();
                if ("Add Line".equals(clickedText)) {
                    openMainActivity();
                } else if ("Remove Line".equals(clickedText)) {
                    openTrainActivity2();
                } else if ("Station Operations".equals(clickedText)) {
                    openTrainActivity3();
                } else if ("View Lines".equals(clickedText)) {
                    openTrainActivity4();
                }
            }
        });
    }

    /**
     * Opens the add line menu
     */
    public void openMainActivity(){
        Intent intent = new Intent(this, AddLine.class);
        startActivity(intent);
    }

    /**
     * Opens the remove line menu
     */
    public void openTrainActivity2(){
        Intent intent = new Intent(this, RemoveLine.class);
        startActivity(intent);
    }

    /**
     * Opens the change line menu
     */
    public void openTrainActivity3(){
        Intent intent = new Intent(this, ChangeLine.class);
        startActivity(intent);
    }

    /**
     * Opens the menu that shows all lines.
     */
    public void openTrainActivity4(){
        Intent intent = new Intent(this, ViewLines.class);
        startActivity(intent);
    }
}