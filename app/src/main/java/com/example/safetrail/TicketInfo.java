package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Shows a selected ticket's information to the user.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class TicketInfo extends AppCompatActivity {

    private Button tickets;
    private Button userInfo;
    private Button Search;
    private Button quickBuy;
    private Button cancelTicket;
    private Button myProfile;
    private String userName;
    private String ticketID;
    private TextInputEditText ticketIDText;
    private TextInputEditText seatText;
    private TextInputEditText destinationText;
    private TextInputEditText departureText;
    private TextInputEditText departureTimeText;
    private TextInputEditText travelDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_ticket_info);

        userName = getIntent().getStringExtra("User_Name");
        ticketID = getIntent().getStringExtra("Ticket_ID");
        ticketIDText = findViewById(R.id.nameText);
        seatText = findViewById(R.id.discountText);
        destinationText = findViewById(R.id.hesText);
        departureText = findViewById(R.id.departureCityText);
        departureTimeText = findViewById(R.id.userNameText);
        travelDateText = findViewById(R.id.travelDateText);
        cancelTicket = findViewById(R.id.cancelTicketButton);

        //Gets information from database and displays it.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ticketIDText.setText("Ticket ID: " + ticketID);
        seatText.setText("Seat: " + databaseAccess.getSeat(Integer.parseInt(ticketID)));
        destinationText.setText("Destination City: " + databaseAccess.getDestinationCity(Integer.parseInt(ticketID)));
        departureText.setText("Departure City: " + databaseAccess.getDepartureCity(Integer.parseInt(ticketID)));
        departureTimeText.setText("Departure Time: " + databaseAccess.getDepartureTime(Integer.parseInt(ticketID)));
        travelDateText.setText("Travel Date: " + databaseAccess.getDepartureDate(Integer.parseInt(ticketID)));

        ticketIDText.setFocusable(false);
        seatText.setFocusable(false);
        destinationText.setFocusable(false);
        departureText.setFocusable(false);
        travelDateText.setFocusable(false);
        departureTimeText.setFocusable(false);

        databaseAccess.close();

        //Goes back to the previous menu, where all tickets were shown.
        tickets = findViewById(R.id.TicketInformationMenuButton);
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTicketIntent = new Intent(TicketInfo.this, Tickets.class);
                goToTicketIntent.putExtra("User_Name",userName);
                startActivity(goToTicketIntent);
            }
        });

        //Cancels a chosen ticket.
        cancelTicket = findViewById(R.id.cancelTicketButton);
        cancelTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyProfile = new Intent(TicketInfo.this, UserInfo.class);
                goToMyProfile.putExtra("User_Name",userName);
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                databaseAccess.deleteTicket(ticketID);
                if (ticketID.equals((databaseAccess.getLastTicket(userName)))) {
                    System.out.println("last ticket");
                    databaseAccess.deleteLastTicket(userName);
                }
                if (ticketID.equals(databaseAccess.getFavTicket(userName))) {
                    databaseAccess.deleteFavTicket(userName);
                }
                databaseAccess.close();
                startActivity(goToMyProfile);
            }
        });

        //Goes to the user information page.
        userInfo = findViewById(R.id.UserInformationMenuButton);
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyProfile = new Intent(TicketInfo.this, UserInfo.class);
                goToMyProfile.putExtra("User_Name",userName);
                startActivity(goToMyProfile);
            }
        });

        //Bottom navigation bar buttons.
        myProfile = findViewById(R.id.MyProfileButton);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyProfile = new Intent(TicketInfo.this, UserInfo.class);
                goToMyProfile.putExtra("User_Name",userName);
                startActivity(goToMyProfile);
            }
        });

        quickBuy = findViewById(R.id.QuickBuyButton);
        quickBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToQuickBuy = new Intent(TicketInfo.this, QuickBuy.class);
                goToQuickBuy.putExtra("User_Name",userName);
                startActivity(goToQuickBuy);
            }
        });

        Search = findViewById(R.id.SearchButton);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearch = new Intent(TicketInfo.this, Search.class);
                goToSearch.putExtra("User_Name",userName);
                startActivity(goToSearch);
            }
        });
    }
}