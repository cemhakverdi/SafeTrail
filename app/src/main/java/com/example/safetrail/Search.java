package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Menu for users to search trains with their wanted time and place.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class Search extends AppCompatActivity {

    private Button myProfileButton;
    private Button findTrainsButton;
    private Button trainMapButton;
    private Button coronaMapButton;
    private Button quickBuyButton;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Spinner departureCitySpinner;
    private Spinner destinationCitySpinner;
    private ArrayList<String> cityList;
    private final String[] dayList = {"1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private final String[] monthList = {"1", "2", "3","4","5","6","7","8","9","10","11","12"};
    private final String [] yearList = {"2021","2022","2023"};
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_search);

        userName = getIntent().getStringExtra("User_Name");
        myProfileButton = findViewById(R.id.MyProfileButton);
        daySpinner = findViewById(R.id.daySpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        departureCitySpinner = findViewById(R.id.departureCitySpinner);
        destinationCitySpinner = findViewById(R.id.destinationCitySpinner);
        findTrainsButton = findViewById(R.id.FindTrainsButton);
        trainMapButton = findViewById(R.id.TrainMapButton);
        coronaMapButton = findViewById(R.id.CoronaMapButton);

        //Gets available cities from database.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        cityList = new ArrayList<String>();
        cityList.addAll(databaseAccess.viewAllCities());
        Set<String> set = new HashSet<>(cityList);
        cityList.clear();
        cityList.addAll(set);
        databaseAccess.close();

        if (cityList.isEmpty()) {
            cityList.add("Empty");
        }

        //Allows the user to choose take-off and destination cities and the date.
        ArrayAdapter<String> adapterDS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityList);
        ArrayAdapter<String> adapterDP = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cityList);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dayList);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthList);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearList);

        yearSpinner.setAdapter(adapterYear);
        monthSpinner.setAdapter(adapterMonth);
        daySpinner.setAdapter(adapterDay);
        destinationCitySpinner.setAdapter(adapterDS);
        departureCitySpinner.setAdapter(adapterDP);

        //Shows all trains available with given information.
        findTrainsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findTrains();
            }
        });

        trainMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrainMap();
            }
        });

        coronaMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoronaMap();
            }
        });

        //Bottom navigation bar buttons.
        quickBuyButton = (Button) findViewById(R.id.QuickBuyButton);
        quickBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuickBuy();
            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfileIntent = new Intent(Search.this,UserInfo.class);
                myProfileIntent.putExtra("User_Name",userName);
                startActivity(myProfileIntent);
            }
        });
    }

    /**
     * Shows available trains with the given information.
     */
    private void findTrains() {
        Intent suitableTrainsIntent = new Intent(Search.this,SuitableTrains.class);
        suitableTrainsIntent.putExtra("Departure_City",departureCitySpinner.getSelectedItem().toString());
        suitableTrainsIntent.putExtra("Destination_City",destinationCitySpinner.getSelectedItem().toString());
        suitableTrainsIntent.putExtra("Day",daySpinner.getSelectedItem().toString());
        suitableTrainsIntent.putExtra("Month",monthSpinner.getSelectedItem().toString());
        suitableTrainsIntent.putExtra("Year",yearSpinner.getSelectedItem().toString());
        suitableTrainsIntent.putExtra("User_Name",userName);
        startActivity(suitableTrainsIntent);
    }

    /**
     * Bottom navigation bar quick buy menu.
     */
    private void openQuickBuy() {
        Intent quickBuyInt = new Intent(this, QuickBuy.class);
        quickBuyInt.putExtra("User_Name",userName);
        startActivity(quickBuyInt);
    }

    /**
     * Shows the train map
     */
    private void openTrainMap(){
        Intent trainMapIntent = new Intent(Search.this, TrainMap.class);
        trainMapIntent.putExtra("User_Name",userName);
        startActivity(trainMapIntent);
    }

    /**
     * Shows the coronavirus density map.
     */
    private void openCoronaMap(){
        Intent trainMapIntent = new Intent(Search.this, CoronaMap.class);
        trainMapIntent.putExtra("User_Name",userName);
        startActivity(trainMapIntent);
    }
}