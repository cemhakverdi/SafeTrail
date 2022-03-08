package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Menu for admins to remove stations.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class RemoveStation extends AppCompatActivity {

    private Button remove_station;
    private Spinner add_Line_ID;
    private Spinner add_Station_ID;
    private ArrayAdapter<String> listAdapterLine;
    private ArrayAdapter<String> listAdapterStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_station);

        remove_station = (Button) findViewById(R.id.remove_station_button);
        add_Line_ID = findViewById(R.id.remove_line_station_id);
        add_Station_ID = findViewById(R.id.remove_station_id);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        listAdapterLine = new ArrayAdapter<String>(RemoveStation.this, android.R.layout.simple_spinner_item, databaseAccess.getAllLines());
        listAdapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_Line_ID.setAdapter(listAdapterLine);

        ArrayList<String> allCities = new ArrayList<String>();
        allCities = databaseAccess.viewAllCities();
        allCities.addAll(databaseAccess.viewAllCities());
        Set<String> set = new HashSet<>(allCities);
        allCities.clear();
        allCities.addAll(set);

        listAdapterStation = new ArrayAdapter<String>(RemoveStation.this, android.R.layout.simple_spinner_item, allCities);
        listAdapterStation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_Station_ID.setAdapter(listAdapterStation);

        databaseAccess.close();

        //Button to remove the stations with the wanted information.
        remove_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int line;
                String station;

                if( !add_Line_ID.getSelectedItem().toString().equals("Select Lines")){
                    line = Integer.parseInt(add_Line_ID.getSelectedItem().toString());
                    station = add_Station_ID.getSelectedItem().toString();
                    databaseAccess.open();

                    if(databaseAccess.isStationExist(add_Station_ID.getSelectedItem().toString(),add_Line_ID.getSelectedItem().toString()))
                    {
                        Toast.makeText(RemoveStation.this, "Station Removed", Toast.LENGTH_SHORT).show();
                        databaseAccess.deleteStation(line, station);
                    }
                    else
                    {
                        Toast.makeText(RemoveStation.this, "Station Does Not Exist On This Line", Toast.LENGTH_SHORT).show();
                    }
                    databaseAccess.close();
                }
                else{
                    Toast.makeText(RemoveStation.this, "Choose a station.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}