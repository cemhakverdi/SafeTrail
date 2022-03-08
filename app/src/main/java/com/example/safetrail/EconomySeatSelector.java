package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Seat selection for economy wagons.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class EconomySeatSelector extends AppCompatActivity {
    private String time;
    private String userName;
    private String trainID;
    private String wagonNo;
    private String food;
    private String luggage;
    private String departureCity;
    private String destinationCity;
    private Button proceed;
    private Button searchButton;
    private Button quickBuyButton;
    private Button myProfileButton;
    private Button pressedButton;
    private ArrayList<Button> buttonArray;
    private ArrayList<String> occupiedSeats;
    private Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14,button15,button16,button17,button18,button19,button20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_seat_selector);

        //Gets the user's choices from the previous menu.
        food = getIntent().getStringExtra("Food");
        luggage = getIntent().getStringExtra("Luggage");
        userName = getIntent().getStringExtra("User_Name");
        trainID = getIntent().getStringExtra("TrainID");
        wagonNo = getIntent().getStringExtra("Wagon_No");
        departureCity = getIntent().getStringExtra("Departure_City");
        destinationCity = getIntent().getStringExtra("Destination_City");
        time = getIntent().getStringExtra("Time");

        //Get occupied seats to recolor them.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        occupiedSeats = databaseAccess.getOccupiedSeats(wagonNo);
        databaseAccess.close();

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button10 = (Button)findViewById(R.id.button10);
        button11 = (Button)findViewById(R.id.button11);
        button12 = (Button)findViewById(R.id.button12);
        button13 = (Button)findViewById(R.id.button13);
        button14 = (Button)findViewById(R.id.button14);
        button15 = (Button)findViewById(R.id.button15);
        button16 = (Button)findViewById(R.id.button16);
        button17 = (Button)findViewById(R.id.button17);
        button18 = (Button)findViewById(R.id.button18);
        button19 = (Button)findViewById(R.id.button19);
        button20 = (Button)findViewById(R.id.button20);
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
        buttonArray.add(button11);
        buttonArray.add(button12);
        buttonArray.add(button13);
        buttonArray.add(button14);
        buttonArray.add(button15);
        buttonArray.add(button16);
        buttonArray.add(button17);
        buttonArray.add(button18);
        buttonArray.add(button19);
        buttonArray.add(button20);

        //Changes the display color of taken seats to red and disables them for user convenience.
        for (String a:occupiedSeats) {
            Button currentButton = buttonArray.get(Integer.parseInt(a)-1);
            currentButton.setBackgroundColor(Color.RED);
            currentButton.setClickable(false);
        }

        //Displays the user's choice in a different color for user convenience.
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

        //Button to proceed to checkout menu.
        proceed = (Button) findViewById(R.id.proceedCheckout);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pressedButton == null) {
                    //Making sure the user has selected a seat.
                    Toast.makeText(EconomySeatSelector.this,"Please Select a Seat", Toast.LENGTH_SHORT).show();
                }
                else {
                    openCheckout();
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
     * Proceed to the checkout button.
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
        intent.putExtra("First_Class",false);
        intent.putExtra("Food",food);
        intent.putExtra("Luggage",luggage);
        intent.putExtra("Time",time);
        startActivity(intent);
    }
}