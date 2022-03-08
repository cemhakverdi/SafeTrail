package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Menu confirming the ticket purchase.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 29.04.2021
 */
public class PurchaseSuccessful extends AppCompatActivity {
    private String userName;
    private String ticketID;
    private Button searchButton;
    private Button quickBuyButton;
    private Button myProfileButton;
    private Button saveTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_purchase_successful);

        ticketID = getIntent().getStringExtra("Ticket_ID");
        userName = getIntent().getStringExtra("User_Name");
        searchButton = (Button) findViewById(R.id.bottomSearch);
        saveTicket = findViewById(R.id.button33);

        //Button to save ticket as favorite for easier use later.
        saveTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                databaseAccess.setFavTicket(ticketID,userName);
                databaseAccess.close();
                Toast.makeText(PurchaseSuccessful.this,"Ticket added to Favorite Tickets Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        //Bottom navigation bar buttons.
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

        myProfileButton = (Button) findViewById(R.id.bottomProfile);
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
    private void openSearchMenu(){
        Intent searchPage = new Intent(this, Search.class);
        searchPage.putExtra("User_Name",userName);
        startActivity(searchPage);
    }

    /**
     * Bottom navigation bar quick buy menu button.
     */
    private void openQuickBuy(){
        Intent quickBuyInt= new Intent(this, QuickBuy.class);
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