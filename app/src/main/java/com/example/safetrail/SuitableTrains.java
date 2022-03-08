package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Shows all available trains with the required information.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 01.05.2021
 */
public class SuitableTrains extends AppCompatActivity {
    private ListView trainList;
    private Button quickBuyButton;
    private Button searchButton;
    private Button myProfileButton;
    private boolean trainExists;
    private String userName;
    private String departureCity;
    private String destinationCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_suitable_trains);

        String day;
        String month;
        String year;

        //Gets the user's requests.
        trainExists = false;
        departureCity = getIntent().getStringExtra("Departure_City");
        destinationCity = getIntent().getStringExtra("Destination_City");
        day = getIntent().getStringExtra("Day");
        month = getIntent().getStringExtra("Month");
        year = getIntent().getStringExtra("Year");
        userName = getIntent().getStringExtra("User_Name");

        trainList = (ListView)findViewById(R.id.ticketListView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Checks database for trains that match the user's requirements.
        ArrayList<String> trainIDArray = databaseAccess.getSuitableTrains(destinationCity,departureCity,day,month,year);
        ArrayList<String> trainHourArray = new ArrayList<>();
        ArrayList<String> trainArray = new ArrayList<>();

        //If there is none, shows a warning message.
        if (trainIDArray.isEmpty() || trainIDArray.get(0).equals("There are no suitable trains")) {
            trainHourArray.add("There are no suitable trains");
            trainExists = false;
        }

        //Adds all available trains to the menu.
        else {
            for (String j: trainIDArray) {
                if (!databaseAccess.isDeparted(j)) {
                    trainArray.add(j);
                }
            }
            Set<String> set = new HashSet<>(trainArray);
            trainArray.clear();
            trainArray.addAll(set);
            for (String j : trainArray) {
                trainHourArray.add("Train Departure Time: " + databaseAccess.getTrainTime(j) + " ID: " + j);
            }
            trainExists = true;
        }
        if (trainHourArray.isEmpty()) {
            trainHourArray.add("There are no suitable trains ");
            trainExists = false;
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, trainHourArray);
        trainList.setAdapter(arrayAdapter);
        databaseAccess.close();

        //Menu that shows all trains available, choosing one lets the user buy a ticket from the train.
        trainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (trainExists) {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    Intent goToTicketIntent = new Intent(SuitableTrains.this, TicketOperator.class);
                    goToTicketIntent.putExtra("Train_ID", trainArray.get(position));
                    goToTicketIntent.putExtra("User_Name",userName);
                    goToTicketIntent.putExtra("Destination_City",destinationCity);
                    goToTicketIntent.putExtra("Departure_City",departureCity);
                    goToTicketIntent.putExtra("Time",databaseAccess.getTrainTime(trainIDArray.get(position)));
                    goToTicketIntent.putExtra("Day",day);
                    goToTicketIntent.putExtra("Month",month);
                    goToTicketIntent.putExtra("Year",year);
                    databaseAccess.close();
                    startActivity(goToTicketIntent);
                }
                else {
                    Toast.makeText(SuitableTrains.this,"There are no suitable trains", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Bottom navigation bar buttons.
        searchButton = (Button) findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchMenu();
            }
        });

        quickBuyButton = (Button) findViewById(R.id.QuickBuyButton);
        quickBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuickBuy();
            }
        });


        myProfileButton = (Button) findViewById(R.id.MyProfileButton);
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
}