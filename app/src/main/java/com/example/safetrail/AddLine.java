/**
 * Adds lines with the specified line id and length
 */
package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Adds lines with specified line id and length
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class AddLine extends AppCompatActivity {

    //Instances
    private Button add_line;
    private EditText add_line_ID;
    private EditText add_line_length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);

        add_line_ID = findViewById(R.id.add_line_id);
        add_line_length = findViewById(R.id.add_line_length);
        add_line = (Button) findViewById(R.id.add_line_button);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());



         // When the add line button is pressed the line is added to database if it does not already exist there
         //If the entered line id already exists, the user receives a message saying the line exists
        add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(add_line_ID.getText().toString().equals("") || add_line_length.getText().toString().equals(""))) {

                    int id = Integer.parseInt(add_line_ID.getText().toString());
                    int length = Integer.parseInt(add_line_length.getText().toString());
                    databaseAccess.open();

                    if (!databaseAccess.isLineExist(add_line_ID.getText().toString())) {
                        Toast.makeText(AddLine.this, "Line Added", Toast.LENGTH_SHORT).show();
                        databaseAccess.insertDataToLineTable(id, length);
                        databaseAccess.close();
                        add_line_ID.setText("");         //Remove the entered text when the "add line" button is clicked
                        add_line_length.setText("");
                    } else {
                        Toast.makeText(AddLine.this, "Line Already Exists", Toast.LENGTH_SHORT).show();
                        add_line_ID.setText("");
                        add_line_length.setText("");
                        databaseAccess.close();

                    }
                }
            }
        });
    }
}