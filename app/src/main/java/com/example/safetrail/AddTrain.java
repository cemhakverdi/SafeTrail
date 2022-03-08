package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Adds trains with given specifications and displays them
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 30.04.2021
 */
public class AddTrain extends AppCompatActivity {

    //Instances
    private Button add_train, view_trains;
    private EditText add_ID;
    private Spinner add_year;
    private Spinner add_month;
    private Spinner add_day;
    private Spinner add_time;
    private Spinner add_line;
    private Spinner add_direction;
    private ArrayAdapter adapter;
    private ArrayList<String> listText;
    private ListView all_trains;
    private ArrayList<String> spinnerTextTime;
    private ArrayList<String> spinnerTextDay;
    private ArrayList<String> spinnerTextMonth;
    private ArrayList<String> spinnerTextYear;
    private ArrayList<String> spinnerTextDirection;
    private ArrayList<String> getSpinnerTextLine;
    private ArrayAdapter<String> listAdapterTime;
    private ArrayAdapter<String> listAdapterDay;
    private ArrayAdapter<String> listAdapterMonth;
    private ArrayAdapter<String> listAdapterYear;
    private ArrayAdapter<String> listAdapterDirection;
    private ArrayAdapter<String> listAdapterLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        //Initialization of the instances
        all_trains = findViewById(R.id.list_view_add_train);
        add_train = (Button) findViewById(R.id.add_train);
        view_trains = (Button) findViewById(R.id.view_trains);
        add_ID = findViewById(R.id.add_id);
        add_year = findViewById(R.id.add_year);
        add_month = findViewById(R.id.add_month);
        add_day = findViewById(R.id.add_day);
        add_time = findViewById(R.id.add_time);
        add_line = findViewById(R.id.add_line);
        add_direction = findViewById(R.id.add_direction);

        spinnerTextDay = new ArrayList<>();
        spinnerTextDay.add("Day");
        for(int i = 1; i < 32; i++){
            spinnerTextDay.add(String.valueOf(i));
        }

        spinnerTextMonth = new ArrayList<>();
        spinnerTextMonth.add("Month");
        for(int i = 1; i < 13; i++){
            spinnerTextMonth.add(String.valueOf(i));
        }

        spinnerTextYear = new ArrayList<>();
        spinnerTextYear.add("Year");
        spinnerTextYear.add("2021");
        spinnerTextYear.add("2022");
        spinnerTextYear.add("2023");

        spinnerTextTime = new ArrayList<>();
        spinnerTextTime.add("Time");
        spinnerTextTime.add("09.45");
        spinnerTextTime.add("10.45");
        spinnerTextTime.add("11.45");
        spinnerTextTime.add("12.45");
        spinnerTextTime.add("13.45");
        spinnerTextTime.add("14.45");
        spinnerTextTime.add("15.45");
        spinnerTextTime.add("16.45");
        spinnerTextTime.add("17.45");
        spinnerTextTime.add("18.45");
        spinnerTextTime.add("19.45");

        spinnerTextDirection = new ArrayList<>();
        spinnerTextDirection.add("Direction");
        spinnerTextDirection.add("0");
        spinnerTextDirection.add("1");

        listAdapterTime = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerTextTime);
        add_time.setAdapter(listAdapterTime);

        listAdapterDay = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerTextDay);
        add_day.setAdapter(listAdapterDay);

        listAdapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerTextMonth);
        add_month.setAdapter(listAdapterMonth);

        listAdapterYear= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerTextYear);
        add_year.setAdapter(listAdapterYear);

        listAdapterDirection= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerTextDirection);
        add_direction.setAdapter(listAdapterDirection);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        databaseAccess.open();
        listAdapterLine = new ArrayAdapter<String>(AddTrain.this, android.R.layout.simple_spinner_item, databaseAccess.getAllLines());
        listAdapterLine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_line.setAdapter(listAdapterLine);
        databaseAccess.close();

        // When the "add train" button is clicked, the train with the specified features is added if the train with that id does not already exists
         // If the train already exists, the user gets a message saying the train already exists
        add_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(add_ID.equals("") || add_direction.getSelectedItem().toString().equals("Direction") || add_line.getSelectedItem().toString().equals("Select Lines") || add_year.getSelectedItem().toString().equals("Year") || add_month.getSelectedItem().toString().equals("Month") || add_day.getSelectedItem().toString().equals("Day") || add_time.getSelectedItem().toString().equals("Time"))) {
                    //The instances gets casted to integer or double depending on the type of the parameter in the method for database
                    double time = Double.parseDouble(add_time.getSelectedItem().toString());
                    int id = Integer.parseInt(add_ID.getText().toString());
                    int direction = Integer.parseInt(add_direction.getSelectedItem().toString());
                    int line = Integer.parseInt(add_line.getSelectedItem().toString());
                    int year = Integer.parseInt(add_year.getSelectedItem().toString());
                    int month = Integer.parseInt(add_month.getSelectedItem().toString());
                    int day = Integer.parseInt(add_day.getSelectedItem().toString());

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    if (!databaseAccess.isTrainExist(add_ID.getText().toString())) {
                        Toast.makeText(AddTrain.this, "Train Added", Toast.LENGTH_SHORT).show();
                        databaseAccess.insertDataToTrainTable(id, line, time, year, month, day, direction, 0);
                        databaseAccess.close();
                        add_ID.setText("");       //The texts gets cleared for the next train addition

                    } else {
                        Toast.makeText(AddTrain.this, "Train Already Exists", Toast.LENGTH_SHORT).show();
                        databaseAccess.close();
                    }
                }


            }
        });


         // When "view trains" method is clicked, all the trains in database are displayed
        view_trains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                listText = databaseAccess.viewAllTrains();
                adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listText);
                all_trains.setAdapter(adapter);
                databaseAccess.close();
            }
        });
    }
}


