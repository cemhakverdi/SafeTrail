package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Add Wagons with chosen type, and entered id, to the selected train
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class AddWagon extends AppCompatActivity {

    //Instances
    private Spinner wagonType, trainID;
    private Button add_wagon;
    private EditText wagon_number;
    private ArrayAdapter<String> listAdapter3, listAdapter_train;
    private ArrayList<String> spinnerText_wagonType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wagon);

        //Initialization of the instances
        wagon_number = findViewById(R.id.wagon_number);
        wagonType = findViewById(R.id.spinner_wagon_first_class);
        trainID = findViewById(R.id.spinner_wagon_train_id);
        add_wagon = (Button) findViewById(R.id.add_wagon);

        spinnerText_wagonType = new ArrayList<>();
        spinnerText_wagonType.add("Economy");
        spinnerText_wagonType.add("First Class");

        listAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerText_wagonType);
        wagonType.setAdapter(listAdapter3);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        listAdapter_train = new ArrayAdapter<String>(AddWagon.this, android.R.layout.simple_spinner_item, databaseAccess.getAllTrains());
        listAdapter_train.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainID.setAdapter(listAdapter_train);
        databaseAccess.close();


         // Add wagon with given number and type to the selected train if the wagon does not already exist
         // If the wagon already exists, the user gets a message saying the wagon already exists
        add_wagon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(wagon_number.getText().toString().equals(""))) {
                    int wag_num = Integer.parseInt(wagon_number.getText().toString());
                    int wagon_class = wagonType.getSelectedItemPosition();
                    int id_train = Integer.parseInt(trainID.getSelectedItem().toString());

                    databaseAccess.open();

                    if (!databaseAccess.isWagonExist(wagon_number.getText().toString())) {

                        if (wagon_class == 0) {
                            databaseAccess.insertDataToWagonTable(wag_num, 20, 0, wagon_class, id_train);
                            databaseAccess.createSeats(String.valueOf(wag_num), String.valueOf(wagon_class));
                        } else {
                            databaseAccess.insertDataToWagonTable(wag_num, 10, 0, wagon_class, id_train);
                            databaseAccess.createSeats(String.valueOf(wag_num), String.valueOf(wagon_class));
                        }

                        Toast.makeText(AddWagon.this, "Wagon Added", Toast.LENGTH_SHORT).show();
                        wagon_number.setText("");
                    } else {
                        Toast.makeText(AddWagon.this, "Wagon Already Exists", Toast.LENGTH_SHORT).show();
                        wagon_number.setText("");
                    }
                    databaseAccess.close();
                }
            }
        });
    }
}