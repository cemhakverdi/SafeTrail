package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Seat selection for first class wagons
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class FirstClassSeatSelector extends AppCompatActivity {
    private String time;
    private String userName;
    private String trainID;
    private String wagonNo;
    private String food,luggage;
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    private ArrayList<Button> buttonArray;
    private Button search;
    private Button quickBuy;
    private Button myProfile;
    private Button proceedCheckout;
    private Button pressedButton;
    private ArrayList<String> occupiedSeats;
    private String departureCity;
    private String destinationCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_first_class);

        //Gets the user's choices from the previous menu.
        food = getIntent().getStringExtra("Food");
        luggage = getIntent().getStringExtra("Luggage");
        userName = getIntent().getStringExtra("User_Name");
        trainID = getIntent().getStringExtra("TrainID");
        wagonNo = getIntent().getStringExtra("Wagon_No");
        departureCity = getIntent().getStringExtra("Departure_City");
        destinationCity = getIntent().getStringExtra("Destination_City");
        time = getIntent().getStringExtra("Time");

        //Gets the occupied seats to recolor them.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        occupiedSeats = databaseAccess.getOccupiedSeats(wagonNo);
        databaseAccess.close();

        proceedCheckout = findViewById(R.id.proceedCheckout);
        search = findViewById(R.id.bottomSearch);
        quickBuy = findViewById(R.id.bottomQuickBuy);
        myProfile = findViewById(R.id.bottomProfile);
        button1 = (Button) findViewById(R.id.button1_first);
        button2 = (Button) findViewById(R.id.button2_first);
        button3 = (Button) findViewById(R.id.button3_first);
        button4 = (Button) findViewById(R.id.button4_first);
        button5 = (Button) findViewById(R.id.button5_first);
        button6 = (Button) findViewById(R.id.button6_first);
        button7 = (Button) findViewById(R.id.button7_first);
        button8 = (Button) findViewById(R.id.button8_first);
        button9 = (Button) findViewById(R.id.button9_first);
        button10 = (Button) findViewById(R.id.button10_first);

        buttonArray = new ArrayList<Button>();
        buttonArray.add(button1);
        buttonArray.add(button2);
        buttonArray.add(button3);
        buttonArray.add(button4);
        buttonArray.add(button5);
        buttonArray.add(button6);
        buttonArray.add(button7);
        buttonArray.add(button8);
        buttonArray.add(button9);
        buttonArray.add(button10);

        //Colors all occupied seats to red.
        for (String a:occupiedSeats) {
            Button currentButton = buttonArray.get(Integer.parseInt(a)-1);
            currentButton.setBackgroundColor(Color.RED);
            currentButton.setClickable(false);
        }

        //Paints the selected button to a different color for user convenience.
        for (Button button: buttonArray) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pressedButton = button;
                    for (Button otherButtons: buttonArray) {
                        otherButtons.setBackgroundColor(Color.rgb(163, 161, 245));

                    }
                    pressedButton.setBackgroundColor(Color.rgb(156, 217, 219));
                    for (String a:occupiedSeats) {
                        Button currentButton = buttonArray.get(Integer.parseInt(a)-1);
                        currentButton.setBackgroundColor(Color.RED);
                        currentButton.setClickable(false);
                    }
                }
            });
        }

        //Goes to the checkout page
        proceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckout();
            }
        });

        //Bottom navigation bar buttons.
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchMenu();
            }
        });

        quickBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuickBuy();
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
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
     * Opens the purchase page.
     */
    public void openCheckout(){
        Intent intent = new Intent( this, Purchase.class);
        intent.putExtra("User_Name",userName);
        int seatNoInt = buttonArray.indexOf(pressedButton);
        String seatNo = String.valueOf(seatNoInt+1);
        intent.putExtra("Seat_No",seatNo);
        intent.putExtra("Wagon_No",wagonNo);
        intent.putExtra("Destination_City",destinationCity);
        intent.putExtra("Departure_City",departureCity);
        intent.putExtra("First_Class",true);
        intent.putExtra("Food",food);
        intent.putExtra("Luggage",luggage);
        intent.putExtra("Time",time);
        startActivity(intent);
    }
}