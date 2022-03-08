package com.example.safetrail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Sign-in page.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class SignIn extends AppCompatActivity {
    private Button signIn;
    private Button signUp;
    private TextInputLayout addUserName;
    private TextInputLayout addPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_sign_in);

        signIn = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpButton);
        addUserName = findViewById(R.id.Username);
        addPassword = findViewById(R.id.Password);

        //The button confirming log-in request.
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gets the inputs from user.
                String userName = addUserName.getEditText().getText().toString();
                String password = addPassword.getEditText().getText().toString();

                //Making sure the fields aren't empty.
                if(userName.equals("") || password.equals(""))
                {
                    Toast.makeText(SignIn.this,"Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                //If an admin logs in, direct them to the admin page.
                else if(userName.equals("Admin") && password.equals("Admin")){
                    openMainActivity();
                }
                //If the user isn't an admin, check if their provided information exists on an account in the database.
                else
                {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    if(databaseAccess.checkUserExist(userName,password))
                    {
                        //Check whether the user is banned or not and inform them if so.
                        if (databaseAccess.checkUserBanned(userName)) {
                            addPassword.getEditText().setText("");
                            addUserName.getEditText().setText("");
                            Toast.makeText(SignIn.this, "Welcome", Toast.LENGTH_SHORT).show();
                            Intent searchPageIntent = new Intent(SignIn.this, Search.class);
                            searchPageIntent.putExtra("User_Name", userName);
                            startActivity(searchPageIntent);
                        }
                        else {
                            Toast.makeText(SignIn.this,"You are banned from the system, please contact administration", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SignIn.this,"User Name or Password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                    databaseAccess.close();
                }
            }
        });

        //Button to sign up if the user doesn't have an account yet.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpPageIntent = new Intent(SignIn.this,SignUp.class);
                startActivity(signUpPageIntent);
            }
        });
    }

    /**
     * Opens the admin page.
     */
    public void openMainActivity(){
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
    }
}