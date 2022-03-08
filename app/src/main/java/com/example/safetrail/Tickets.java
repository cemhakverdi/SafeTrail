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

/**
 * Menu to change information of trains
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 29.04.2021
 */
public class Tickets extends AppCompatActivity {

    private Button quickBuyButton;
    private Button searchButton;
    private Button myProfileButton;
    private ListView ticketList;
    private boolean ticketExists;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_tickets);

        ticketExists = false;
        quickBuyButton = findViewById(R.id.QuickBuyButton);
        searchButton = findViewById(R.id.SearchButton);
        myProfileButton = findViewById(R.id.MyProfileButton);
        ticketList = findViewById(R.id.ticketListView);
        userName = getIntent().getStringExtra("User_Name");

        //Get user's tickets from database.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ArrayList<String> ticketArray = databaseAccess.viewAllTickets(userName);
        ArrayList<String> ticketIDArray = databaseAccess.getTicketID(userName);
        ArrayList<String> ticketArrayList = new ArrayList<>();

        //If the user has no tickets display a warning message.
        if (ticketArray == null) {
            ticketExists = false;
            ticketArrayList.add("There are no tickets");
        }
        else {
            ticketExists = true;
            for (String j : ticketArray) {
                ticketArrayList.add(j);
            }
        }

        //Add all tickets to a dropdown menu.
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ticketArrayList);
        ticketList.setAdapter(arrayAdapter);
        databaseAccess.close();

        //If the user selects a ticket to see information about, redirect them to it.
        ticketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ticketExists) {
                    Intent goToTicketIntent = new Intent(Tickets.this, TicketInfo.class);
                    goToTicketIntent.putExtra("Ticket_ID", ticketIDArray.get(position));
                    goToTicketIntent.putExtra("User_Name",userName);
                    startActivity(goToTicketIntent);
                }
                else {
                    Toast.makeText(Tickets.this,"There are no tickets", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Bottom navigation bar buttons.
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearch = new Intent(Tickets.this, Search.class);
                goToSearch.putExtra("User_Name",userName);
                startActivity(goToSearch);
            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyProfile = new Intent(Tickets.this, UserInfo.class);
                goToMyProfile.putExtra("User_Name",userName);
                startActivity(goToMyProfile);
            }
        });

        quickBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToQuickBuy = new Intent(Tickets.this, QuickBuy.class);
                goToQuickBuy.putExtra("User_Name",userName);
                startActivity(goToQuickBuy);
            }
        });
    }
}