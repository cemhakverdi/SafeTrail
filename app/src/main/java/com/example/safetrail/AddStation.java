package com.example.safetrail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Add Stations with specified id,name and line rank to the chosen line
 * The stations of the chosen line can be displayed
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class AddStation extends AppCompatActivity {

    //Instances
    private Button add_station, see_stations;
    private EditText add_Station_ID;
    private EditText add_Station_Name;
    private Spinner add_Line_ID;
    private EditText add_Line_Rank;
    private ArrayAdapter<String> listAdapter;
    private ListView viewStations;
    private ArrayList<String> allStations;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        //Instance initialization
        add_station = (Button) findViewById(R.id.add_station_button);
        see_stations = (Button) findViewById(R.id.add_station_button2);
        add_Station_ID = findViewById(R.id.add_station_id);
        add_Station_Name = findViewById(R.id.add_station_name);
        add_Line_ID = findViewById(R.id.add_station_line_id);
        add_Line_Rank = findViewById(R.id.add_station_line_rank);
        viewStations = findViewById(R.id.list_view_add_station);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        //Gets the existing lines and puts them into the spinner
        listAdapter = new ArrayAdapter<String>(AddStation.this, android.R.layout.simple_spinner_item, databaseAccess.getAllLines());
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_Line_ID.setAdapter(listAdapter);
        databaseAccess.close();



        // When any lines selected from the spinner, "view all stations of the line" gets visible
        // When the "view all stations of the line" is pressed, all the stations of the chosen line is displayed on the screen
        add_Line_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().toString().equals("Select Lines")) {
                    see_stations.setVisibility(View.VISIBLE);
                    see_stations.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseAccess.open();
                            allStations = databaseAccess.viewStationsOfTheLine(add_Line_ID.getSelectedItem().toString());
                            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, allStations);
                            viewStations.setAdapter(adapter);
                            databaseAccess.close();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // When "add station" button is clicked the station with given specification gets added to the chosen line
        // When the station is added successfully, the user gets a message about it
        add_station.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int id;
                String name;
                int rank;

                if( !add_Station_ID.getText().toString().equals("Select Lines")){
                    id = Integer.parseInt(add_Station_ID.getText().toString());
                    name = add_Station_Name.getText().toString();
                    rank = Integer.parseInt(add_Line_Rank.getText().toString());

                    databaseAccess.open();
                    if(!databaseAccess.isStationExist(name,add_Line_ID.getSelectedItem().toString()))
                    {
                        Toast.makeText(AddStation.this, "Station Added", Toast.LENGTH_SHORT).show();
                        databaseAccess.insertDataToStationTable(id, name, Integer.parseInt(add_Line_ID.getSelectedItem().toString()), rank);
                        databaseAccess.close();
                        add_Station_ID.setText("");  //Given specifications are cleared for the next addition
                        add_Station_Name.setText("");
                        add_Line_Rank.setText("");
                    }
                    else
                    {
                        Toast.makeText(AddStation.this, "Station Already Exists On This Line", Toast.LENGTH_SHORT).show();
                        databaseAccess.close();
                    }
                }
                else{
                    Toast.makeText(AddStation.this, "Choose line.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}