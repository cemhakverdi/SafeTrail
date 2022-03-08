package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Starts a train's journey.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class StartTheRide extends AppCompatActivity {
    private Spinner spinner1;
    private ArrayAdapter<String> listAdapter3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_the_ride);

        spinner1 = (Spinner) findViewById(R.id.spinner_ride);
        button = (Button) findViewById(R.id.button_ride);

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Gets all stationary trains from database and adds them to the menu.
        listAdapter3 = new ArrayAdapter<String>(StartTheRide.this, android.R.layout.simple_spinner_item, databaseAccess.getUndeparturedTrains());
        listAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(listAdapter3);
        databaseAccess.close();

        //Starts the chosen train.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(spinner1.getSelectedItem().toString().equals("Select Train"))) {
                    Toast.makeText(StartTheRide.this, "The Train Departed", Toast.LENGTH_SHORT).show();
                    databaseAccess.open();
                    databaseAccess.departTheTrain(Integer.parseInt(spinner1.getSelectedItem().toString()));
                    databaseAccess.close();
                }
            }
        });
    }
}