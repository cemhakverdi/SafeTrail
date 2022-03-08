package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Menu for admins to remove trains.
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 02.05.2021
 */
public class RemoveTrain extends AppCompatActivity {

    private Button remove_train;
    private EditText remove_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_train);

        remove_train = (Button) findViewById(R.id.remove_train);
        remove_ID = findViewById(R.id.remove_train_id);

        //Button to remove the train with the wanted information.
        remove_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(remove_ID.getText().toString().equals(""))) {
                    int id = Integer.parseInt(remove_ID.getText().toString());

                    Toast.makeText(RemoveTrain.this, "Train Removed", Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.deleteTrain(id);
                    databaseAccess.close();
                    remove_ID.setText("");
                }
            }
        });
    }
}