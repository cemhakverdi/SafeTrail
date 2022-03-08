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
 * Menu to change information of trains
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 29.04.2021
 */
public class TrainOperations extends AppCompatActivity {


    private ListView listview2;
    private ArrayList<String> listText2;
    private ArrayAdapter<String> listAdapter2;
    private String clickedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_operations);


        listview2 = (ListView) findViewById(R.id.list_view2);
        listText2 = new ArrayList<>();
        listText2.add("Add Train");
        listText2.add("Remove Train");
        listText2.add("Wagon Operations");
        listText2.add("Start The Ride");
        listText2.add("View Trains");
        listAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listText2);
        listview2.setAdapter(listAdapter2);


        //Main spinner that shows possible operations.
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();

                //Checking which operation the admin wants and calling the required method.
                if ("Add Train".equals(clickedText)) {
                    openMainActivity();
                } else if ("Remove Train".equals(clickedText)) {
                    openTrainActivity2();
                } else if ("Wagon Operations".equals(clickedText)) {
                    openTrainActivity3();
                } else if ("Start The Ride".equals(clickedText)) {
                    openTrainActivity4();
                } else if ("View Trains".equals(clickedText)) {
                    openTrainActivity5();
                }
            }
        });
    }

    /**
     * Opens the add train menu.
     */
    public void openMainActivity(){
        Intent intent = new Intent(this, AddTrain.class);
        startActivity(intent);
    }

    /**
     * Opens the remove train menu.
     */
    public void openTrainActivity2(){
        Intent intent = new Intent(this, RemoveTrain.class);
        startActivity(intent);
    }

    /**
     * Changes wanted information of a train.
     */
    public void openTrainActivity3(){
        Intent intent = new Intent(this, ChangeTrain.class);
        startActivity(intent);
    }

    /**
     * Starts a train's trip.
     */
    public void openTrainActivity4(){
        Intent intent = new Intent(this, StartTheRide.class);
        startActivity(intent);
    }

    /**
     * Shows all trains available.
     */
    public void openTrainActivity5(){
        Intent intent = new Intent(this, ViewTrains.class);
        startActivity(intent);
    }
}