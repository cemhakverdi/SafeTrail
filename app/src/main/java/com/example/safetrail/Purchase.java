package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Menu for users to input their credit card information.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class Purchase extends AppCompatActivity {

    private String userName;
    private String wagonNo;
    private String seatNo;
    private String departureCity;
    private String destinationCity;
    private String time;
    private String day;
    private String month;
    private String year;
    private String food;
    private String luggage;
    private Button button;
    private Button searchButton;
    private Button quickBuyButton;
    private Button myProfileButton;
    private TextView ticketPrice;
    private TextView extraPrice;
    private TextView totalPrice;
    private int stationCount;
    private int totalPriceInt;
    private boolean isFirstClass;
    private CheckBox useSavedCard;
    private CheckBox saveCard;
    private EditText cardNo;
    private EditText cardName;
    private EditText cardCVV;
    private EditText cardDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_purchase);

        int ticketPriceInt;
        int extraPriceInt;
        int discount;

        //Gets information from the previous pages.
        isFirstClass = getIntent().getBooleanExtra("First_Class",false);
        food = getIntent().getStringExtra("Food");
        luggage = getIntent().getStringExtra("Luggage");
        userName = getIntent().getStringExtra("User_Name");
        wagonNo = getIntent().getStringExtra("Wagon_No");
        seatNo = getIntent().getStringExtra("Seat_No");
        departureCity = getIntent().getStringExtra("Departure_City");
        destinationCity = getIntent().getStringExtra("Destination_City");
        time = getIntent().getStringExtra("Time");
        day = getIntent().getStringExtra("Day");
        month = getIntent().getStringExtra("Month");
        year = getIntent().getStringExtra("Year");

        saveCard = findViewById(R.id.saveCard);
        cardNo = findViewById(R.id.cardNumberInput2);
        cardName = findViewById(R.id.nameSurnameInput2);
        cardCVV = findViewById(R.id.CVVInput2);
        cardDate = findViewById(R.id.expirationDateInput2);
        useSavedCard = findViewById(R.id.useSavedCard2);
        ticketPrice = findViewById(R.id.ticketPrice2);
        extraPrice = findViewById(R.id.extraServicesPrice2);
        totalPrice = findViewById(R.id.totalPrice2);

        //Gets station count to calculate price.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        stationCount = Math.abs(Integer.parseInt(databaseAccess.getLineRank(wagonNo,departureCity))-Integer.parseInt(databaseAccess.getLineRank(wagonNo,destinationCity)));
        ticketPriceInt = stationCount * 100;
        extraPriceInt = 0;
        databaseAccess.close();

        //Adjusts the ticket price based on users' choices.
        if (isFirstClass) {
            ticketPriceInt = ticketPriceInt * 2;
        }
        if (food.equals("2. Food")) {
            extraPriceInt += 50;
        }
        if (luggage.equals("Heavy (>10 KG)")) {
            extraPriceInt += 50;
        }

        //Checks if the user has any discounts available
        DatabaseAccess databaseAccess1 = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess1.open();
        discount = Integer.parseInt(databaseAccess1.getDiscount(userName));
        databaseAccess1.close();

        //Calculates final price and displays it to the user.
        totalPriceInt = ticketPriceInt + extraPriceInt;
        totalPriceInt = totalPriceInt - (totalPriceInt * discount / 100);
        ticketPrice.setText("Ticket Price: "+ ticketPriceInt);
        extraPrice.setText("Extra Price: "+ extraPriceInt);
        totalPrice.setText("Total Price with discount: " + totalPriceInt);

        //Button to use the saved card
        useSavedCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                if (isChecked) {
                    if (!databaseAccess.getCreditCardFromUserTable(userName).equals("0")) {
                        cardName.setText(databaseAccess.getCustomerName(userName));
                        cardDate.setText(databaseAccess.getCreditCardDate(userName));
                        cardCVV.setText(databaseAccess.getCreditCardCvv(userName));
                        cardNo.setText(databaseAccess.getCreditCardNumber(userName));
                    } else {
                        Toast.makeText(Purchase.this, "There is no saved Credit Card", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    cardName.setText("");
                    cardDate.setText("");
                    cardCVV.setText("");
                    cardNo.setText("");
                }
                databaseAccess.close();
            }
        });

        //Button to confirm purchase
        button = (Button) findViewById(R.id.purchaseTicket2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Making sure the user has inputted their card information before proceeding.
                if (cardName.getText().toString().equals("") || cardDate.getText().toString().equals("") || cardCVV.getText().toString().equals("") || cardNo.getText().toString().equals("")) {
                    Toast.makeText(Purchase.this,"Please enter yor card information", Toast.LENGTH_SHORT).show();
                }
                else {
                    openSuccess();
                }
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

        myProfileButton = (Button) findViewById(R.id.bottomProfile);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });
    }

    /**
     * Opens the next menu, the confirmation of purchase.
     */
    private void openSuccess(){
        Intent intent = new Intent( this, PurchaseSuccessful.class);
        intent.putExtra("User_Name",userName);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Saves the card information to the database if the user wished to do so.
        if (saveCard.isChecked()) {
            if ( !useSavedCard.isChecked() && databaseAccess.checkTheCardNumber(cardNo.getText().toString()) == 0) {
                DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.insertIntoCreditCardTable(userName, cardNo.getText().toString(), cardDate.getText().toString(), Integer.parseInt(cardCVV.getText().toString()), cardName.getText().toString());
            }
        }

        //Gives the ticket a random ID.
        String ticketID = "" + (int) (Math.random() * 10000) + (int) (Math.random() * 10000);
        databaseAccess.createTicket(userName,ticketID,totalPriceInt,time,seatNo,departureCity,destinationCity,wagonNo);
        databaseAccess.occupyTheSeat(seatNo,wagonNo);
        databaseAccess.increaseCustomerAmount(wagonNo);
        databaseAccess.close();
        intent.putExtra("Ticket_ID",ticketID);
        startActivity(intent);
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
     * Bottom navigation bar my profile button.
     */
    private void openMyProfile() {
        Intent myProfileInt = new Intent(this,UserInfo.class);
        myProfileInt.putExtra("User_Name",userName);
        startActivity(myProfileInt);
    }
}