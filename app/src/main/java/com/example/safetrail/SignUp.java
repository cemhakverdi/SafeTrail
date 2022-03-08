package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Sign-up page.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class SignUp extends AppCompatActivity {
    private Button signUp;
    private TextInputLayout addName;
    private TextInputLayout addUserName;
    private TextInputLayout addPassword;
    private TextInputLayout addHesCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.signButton);
        addName = findViewById(R.id.Name);
        addUserName = findViewById(R.id.UserNameSignUp);
        addPassword = findViewById(R.id.PasswordSignUp);
        addHesCode = findViewById(R.id.HesCode);

        //The button to confirm the sign-up.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                //Gets the inputs from user.
                String name = addName.getEditText().getText().toString();
                String userName = addUserName.getEditText().getText().toString();
                String password = addPassword.getEditText().getText().toString();
                String hesCode = addHesCode.getEditText().getText().toString();

                //Making sure the fields are filled.
                if(name.equals("") || userName.equals("") || password.equals("") || hesCode.equals(""))
                {
                    Toast.makeText(SignUp.this,"Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                //If the account already exists, let the user know.
                else if (databaseAccess.checkUserNameExist(userName)) {
                    Toast.makeText(SignUp.this,"Your are already registered!", Toast.LENGTH_SHORT).show();
                }
                //Send data to database, default main screen to the train search page.
                else
                {
                    databaseAccess.insertDataToUserTable(userName,password,name,0,0,0,0,hesCode,0);
                    Toast.makeText(SignUp.this,"Registered!", Toast.LENGTH_SHORT).show();
                    Intent searchPageIntent = new Intent(SignUp.this,Search.class);
                    searchPageIntent.putExtra("User_Name",userName);
                    startActivity(searchPageIntent);
                }
                databaseAccess.close();
            }
        });
    }
}