package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Page that shows information about the user.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 27.04.2021
 */
public class UserInfo extends AppCompatActivity {

    private String userName;
    private Button tickets;
    private Button LogOut;
    private Button searchButton;
    private Button quickBuyButton;
    private TextInputEditText userNameText;
    private TextInputEditText nameText;
    private TextInputEditText discountText;
    private TextInputEditText hesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_user_info);

        userNameText = findViewById(R.id.userNameText);
        nameText = findViewById(R.id.nameText);
        discountText = findViewById(R.id.discountText);
        hesText = findViewById(R.id.hesText);
        userName = getIntent().getStringExtra("User_Name");

        //Gets information from database and displays it.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        userNameText.setText("Username: "+userName);
        nameText.setText("Name: "+ databaseAccess.getName(userName));
        discountText.setText("Discount: "+ databaseAccess.getDiscount(userName));
        hesText.setText("Hes Code: "+ databaseAccess.getHesCode(userName));
        databaseAccess.close();

        userNameText.setFocusable(false);
        nameText.setFocusable(false);
        discountText.setFocusable(false);
        hesText.setFocusable(false);

        //Button to show user's tickets.
        tickets = findViewById(R.id.TicketInformationMenuButton);
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tickets = new Intent(UserInfo.this,Tickets.class);
                tickets.putExtra("User_Name",userName);
                startActivity(tickets);
            }
        });

        //Button to log out of account
        LogOut = findViewById(R.id.LogOutButton);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(UserInfo.this,SignIn.class);
                startActivity(logout);
            }
        });

        //Bottom navigation bar buttons.
        searchButton = findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

        quickBuyButton = findViewById(R.id.QuickBuyButton);
        quickBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuickBuy();
            }
        });

    }

    /**
     * Bottom navigation bar quick buy menu button.
     */
    private void openQuickBuy() {
        Intent myProfileInt = new Intent(this, QuickBuy.class);
        myProfileInt.putExtra("User_Name",userName);
        startActivity(myProfileInt);
    }

    /**
     * Bottom navigation bar search menu button.
     */
    private void openSearch() {
        Intent myProfileInt = new Intent(this,Search.class);
        myProfileInt.putExtra("User_Name",userName);
        startActivity(myProfileInt);
    }
}