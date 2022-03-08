package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Menu for admins to remove wagons.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class RemoveWagon extends AppCompatActivity {

    private Button remove_wagon;
    private EditText wagon_number;
    private EditText train_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_wagon);

        wagon_number = findViewById(R.id.remove_wagon_number);
        train_id =  findViewById(R.id.remove_seat_amount);
        remove_wagon = (Button) findViewById(R.id.remove_wagon);

        //Button to remove the chosen wagon
        remove_wagon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(wagon_number.getText().toString().equals("") || train_id.getText().toString().equals(""))) {
                    int wag_num = Integer.parseInt(wagon_number.getText().toString());
                    int id_train = Integer.parseInt(train_id.getText().toString());

                    Toast.makeText(RemoveWagon.this, "Wagon Removed", Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.deleteWagon(id_train, wag_num);
                    databaseAccess.close();
                    wagon_number.setText("");
                    ;
                    train_id.setText("");
                }
            }
        });
    }
}