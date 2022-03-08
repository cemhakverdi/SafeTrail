package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Menu for users to customise their tickets with information like wagon type, extra services, luggage type and wagon number.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class TicketOperator extends AppCompatActivity {

    private String trainID;
    private String userName;
    private String time;
    private String day;
    private String month;
    private String year;
    private String departureCity;
    private String destinationCity;
    private int isFirstClass;
    private Spinner spinnerES;
    private Spinner spinnerLT;
    private Spinner spinnerWC;
    private CheckBox firstClass;
    private CheckBox economy;
    private Button myProfileButton;
    private Button nextPage;
    private Button searchButton;
    private Button quickBuyButton;
    private final String[] extraServices = { "1. None", "2. Food"};
    private final String[] luggageType = { "Light (<10 KG)", "Heavy (>10 KG)"};
    private TextView trainIDTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_ticket_operator);

        //Saves the user's choices
        isFirstClass = 0;
        trainIDTV = findViewById(R.id.textView6);
        departureCity = getIntent().getStringExtra("Departure_City");
        destinationCity = getIntent().getStringExtra("Destination_City");
        trainID = getIntent().getStringExtra("Train_ID");
        userName = getIntent().getStringExtra("User_Name");
        time = getIntent().getStringExtra("Time");
        day = getIntent().getStringExtra("Day");
        month = getIntent().getStringExtra("Month");
        year = getIntent().getStringExtra("Year");
        trainIDTV.setText("Train ID: "+trainID);

        myProfileButton = findViewById(R.id.bottomProfile);
        firstClass = (CheckBox) findViewById(R.id.firstClassCheck);
        economy = (CheckBox) findViewById(R.id.economyCheck);
        economy.setChecked(true);
        spinnerES = (Spinner) findViewById(R.id.ExtraServices);
        spinnerLT = (Spinner) findViewById(R.id.LuggageType);
        spinnerWC = (Spinner) findViewById(R.id.WagonSelection);
        nextPage = (Button) findViewById(R.id.proceedToSeatSelection);

        //Gets wagons from database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ArrayList<String> wagonArrayList = databaseAccess.getWagonsOfTheTrain(trainID,0);

        ArrayAdapter<String> adapterES = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, extraServices);
        ArrayAdapter<String> adapterLT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, luggageType);
        ArrayAdapter<String> adapterWC= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wagonArrayList);
        ArrayList<String> wagonArrayListFC = databaseAccess.getWagonsOfTheTrain(trainID,1);
        databaseAccess.close();
        ArrayAdapter<String> adapterWCFC= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, wagonArrayListFC);
        spinnerES.setAdapter(adapterES);
        spinnerLT.setAdapter(adapterLT);
        spinnerWC.setAdapter(adapterWC);

        //Opens seat selection menu
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstClass == 0) {
                    openSeatSelectionEconomy();
                }
                else {
                    openSeatSelectionFirst();
                }
            }
        });

        //Checkbox for first class wagon
        firstClass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                economy.setChecked(false);
                spinnerWC.setAdapter(adapterWCFC);
                isFirstClass = 1;
            }
        });

        //Checkbox for economy wagon
        economy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                firstClass.setChecked(false);
                spinnerWC.setAdapter(adapterWC);
                isFirstClass = 0;
            }
        });

        //Bottom navigation bar buttons.
        searchButton = (Button) findViewById(R.id.bottomSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchMenu();
            }
        });

        quickBuyButton = (Button) findViewById(R.id.bottomQuickBuy);
        quickBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuickBuy();
            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    /**
     * Bottom navigation bar search menu button.
     */
    private void openSearchMenu() {
        Intent searchPage = new Intent(this, Search.class);
        searchPage.putExtra("User_Name",userName);
        startActivity(searchPage);
    }

    /**
     * Bottom navigation bar quick buy menu button.
     */
    private void openQuickBuy() {
        Intent quickBuyInt = new Intent(this, QuickBuy.class);
        quickBuyInt.putExtra("User_Name",userName);
        startActivity(quickBuyInt);
    }

    /**
     * Bottom navigation bar my profile menu button.
     */
    private void openMyProfile() {
        Intent myProfileInt = new Intent(this,UserInfo.class);
        myProfileInt.putExtra("User_Name",userName);
        startActivity(myProfileInt);
    }

    /**
     * Opens seat selection menu for economy wagons
     */
    public void openSeatSelectionEconomy(){
        Intent intent = new Intent(this, EconomySeatSelector.class);
        intent.putExtra("User_Name",userName);
        intent.putExtra("Wagon_No",spinnerWC.getSelectedItem().toString());
        intent.putExtra("Food",spinnerES.getSelectedItem().toString());
        intent.putExtra("Luggage",spinnerLT.getSelectedItem().toString());
        intent.putExtra("TrainID",trainID);
        intent.putExtra("Destination_City",destinationCity);
        intent.putExtra("Departure_City",departureCity);
        intent.putExtra("Time",time);
        intent.putExtra("Day",day);
        intent.putExtra("Month",month);
        intent.putExtra("Year",year);
        startActivity(intent);
    }

    /**
     * Opens seat selection menu for first class wagons
     */
    public void openSeatSelectionFirst(){
        Intent intent = new Intent(this, FirstClassSeatSelector.class);
        intent.putExtra("User_Name",userName);
        intent.putExtra("Wagon_No",spinnerWC.getSelectedItem().toString());
        intent.putExtra("Food",spinnerES.getSelectedItem().toString());
        intent.putExtra("Luggage",spinnerLT.getSelectedItem().toString());
        intent.putExtra("TrainID",trainID);
        intent.putExtra("Destination_City",destinationCity);
        intent.putExtra("Departure_City",departureCity);
        intent.putExtra("Time",time);
        intent.putExtra("Day",day);
        intent.putExtra("Month",month);
        intent.putExtra("Year",year);
        startActivity(intent);
    }
}