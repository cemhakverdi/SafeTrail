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
import java.util.Calendar;

/**
 * Menu for users to choose from their saved tickets to re-buy quickly.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class QuickBuy extends AppCompatActivity {

    private ListView favouriteTicketListView;
    private ListView lastTicketListView;
    private Button searchButton;
    private Button myProfileButton;
    private String userName;
    private String day;
    private String month;
    private String year;
    private int favTicket;
    private int lastTicketID;
    private boolean favExists;
    private boolean lastExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_quick_buy);

        int currentYear;
        int currentMonth;
        int currentDay;

        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDay = cal.get(Calendar.DAY_OF_MONTH);

        favExists = false;
        lastExists = false;
        day = String.valueOf(currentDay);
        month = String.valueOf(currentMonth);
        year = String.valueOf(currentYear);

        userName = getIntent().getStringExtra("User_Name");
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Checks if the user has a favorite ticket.
        if (!databaseAccess.getFavTicket(userName).equals(null)) {
            favTicket = Integer.parseInt(databaseAccess.getFavTicket(userName));
            if (favTicket != 0) {
                favExists = true;
            }
        }

        //Checks if the user has a last ticket.
        lastTicketID = Integer.parseInt(databaseAccess.getLastTicket(userName));
        if (lastTicketID != 0) {
            lastExists = true;
        }

        favouriteTicketListView = (ListView) findViewById(R.id.favouriteTicketList);
        lastTicketListView = (ListView) findViewById(R.id.lastTicketList);
        ArrayList<String> favTicArray = new ArrayList<String>();
        ArrayList<String> lastTicArray = new ArrayList<String>();

        //Displays messages according to whether the user has favorite and last tickets.
        if (favExists) {
            favTicArray.add("" + databaseAccess.getDepartureCity(favTicket) + " to " + databaseAccess.getDestinationCity(favTicket));
        }
        else {
            favTicArray.add("No Favourite Ticket");
        }
        if (lastExists) {
            lastTicArray.add("" + databaseAccess.getDepartureCity(lastTicketID) + " to " + databaseAccess.getDestinationCity(lastTicketID));
        }
        else {
            lastTicArray.add("No Last Ticket");
        }
        databaseAccess.close();


        ArrayAdapter arrayAdapterFav = new ArrayAdapter(this, android.R.layout.simple_list_item_1,favTicArray);
        ArrayAdapter arrayAdapterLast = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lastTicArray);

        //Shows the user's favorite ticket.
        favouriteTicketListView.setAdapter(arrayAdapterFav);
        favouriteTicketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseAccess databaseAccess1 = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess1.open();
                if (favExists) {
                    Intent suitableTrainsIntent = new Intent(QuickBuy.this, SuitableTrains.class);
                    suitableTrainsIntent.putExtra("Departure_City", databaseAccess1.getDepartureCity(favTicket));
                    suitableTrainsIntent.putExtra("Destination_City", databaseAccess1.getDestinationCity(favTicket));
                    suitableTrainsIntent.putExtra("Day", day);
                    suitableTrainsIntent.putExtra("Month", month);
                    suitableTrainsIntent.putExtra("Year", year);
                    suitableTrainsIntent.putExtra("User_Name", userName);

                    startActivity(suitableTrainsIntent);
                }
                else {
                    Toast.makeText(QuickBuy.this, "There is not ticket", Toast.LENGTH_SHORT).show();
                }
                databaseAccess1.close();
            }
        });

        //Shows the last ticket the user bought.
        lastTicketListView.setAdapter(arrayAdapterLast);
        lastTicketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseAccess databaseAccess1 = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess1.open();
                if (lastExists) {
                    Intent suitableTrainsIntent = new Intent(QuickBuy.this, SuitableTrains.class);
                    suitableTrainsIntent.putExtra("Departure_City", databaseAccess1.getDepartureCity(lastTicketID));
                    suitableTrainsIntent.putExtra("Destination_City", databaseAccess1.getDestinationCity(lastTicketID));
                    suitableTrainsIntent.putExtra("Day", day);
                    suitableTrainsIntent.putExtra("Month", month);
                    suitableTrainsIntent.putExtra("Year", year);
                    suitableTrainsIntent.putExtra("User_Name", userName);
                    startActivity(suitableTrainsIntent);
                }
                else {
                    Toast.makeText(QuickBuy.this, "There is not ticket", Toast.LENGTH_SHORT).show();
                }
                databaseAccess1.close();
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

        myProfileButton = (Button) findViewById(R.id.MyProfileButton);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    /**
     * Bottom navigation bar search menu.
     */
    private void openSearchMenu(){
        Intent searchPage = new Intent(this, Search.class);
        searchPage.putExtra("User_Name",userName);
        startActivity(searchPage);
    }

    /**
     * Bottom navigation bar my profile menu.
     */
    private void openMyProfile() {
        Intent myProfileInt = new Intent(this,UserInfo.class);
        myProfileInt.putExtra("User_Name",userName);
        startActivity(myProfileInt);
    }
}