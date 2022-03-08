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
 * Menu for admins to change information of trains.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class ChangeTrain extends AppCompatActivity {

    private ListView listview2;
    private ArrayList<String> listText2;
    private ArrayAdapter<String> listAdapter2;
    private String clickedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_train);

        listview2 = (ListView) findViewById(R.id.list_view_change_train);
        listText2 = new ArrayList<>();
        listText2.add("Add Wagon");
        listText2.add("Remove Wagon");
        listAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listText2);
        listview2.setAdapter(listAdapter2);

        //Allows the admins to choose a train and perform the operation they want.
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();
                if ("Add Wagon".equals(clickedText)) {
                    openMainActivity();
                } else if ("Remove Wagon".equals(clickedText)) {
                    openTrainActivity2();
                }
            }
        });
    }

    /**
     * Opens the add wagon menu.
     */
    public void openMainActivity(){
        Intent intent = new Intent(this, AddWagon.class);
        startActivity(intent);
    }

    /**
     * Opens the remove wagon menu.
     */
    public void openTrainActivity2(){
        Intent intent = new Intent(this, RemoveWagon.class);
        startActivity(intent);
    }
}