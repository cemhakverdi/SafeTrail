package com.example.safetrail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Sets the schedule of the selected train
 * @version 27.04.2021
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 */
public class Schedule extends AppCompatActivity {

    //Instances
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;
    private ArrayList<String> spinnerText2;
    private ArrayList<String> spinnerText3;
    private ArrayList<String> spinnerText4;
    private ArrayList<String> spinnerText5;
    private ArrayAdapter<String> listAdapter3;
    private ArrayAdapter<String> listAdapter3_2;
    private ArrayAdapter<String> listAdapter3_3;
    private ArrayAdapter<String> listAdapter3_4;
    private ArrayAdapter<String> listAdapter3_5;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        button = (Button) findViewById(R.id.button_proceed_schedule);
        spinner1 = (Spinner) findViewById(R.id.spinner_schedule);

        //Add texts to spinners
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinnerText2 = new ArrayList<>();
        spinnerText2.add("Day");
        for(int i = 1; i < 32; i++){
            spinnerText2.add(String.valueOf(i));
        }

        spinner4 = (Spinner) findViewById(R.id.spinner21);
        spinnerText4 = new ArrayList<>();
        spinnerText4.add("Month");
        for(int i = 1; i < 13; i++){
            spinnerText4.add(String.valueOf(i));
        }

        spinner5 = (Spinner) findViewById(R.id.spinner22);
        spinnerText5 = new ArrayList<>();
        spinnerText5.add("Year");
        spinnerText5.add("2021");
        spinnerText5.add("2022");
        spinnerText5.add("2023");

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinnerText3 = new ArrayList<>();
        spinnerText3.add("Time");
        spinnerText3.add("09.45");
        spinnerText3.add("10.45");
        spinnerText3.add("11.45");
        spinnerText3.add("12.45");
        spinnerText3.add("13.45");
        spinnerText3.add("14.45");
        spinnerText3.add("15.45");
        spinnerText3.add("16.45");
        spinnerText3.add("17.45");
        spinnerText3.add("18.45");
        spinnerText3.add("19.45");


        listAdapter3_2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerText2);
        spinner2.setAdapter(listAdapter3_2);

        listAdapter3_3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerText3);
        spinner3.setAdapter(listAdapter3_3);

        listAdapter3_4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerText4);
        spinner4.setAdapter(listAdapter3_4);

        listAdapter3_5 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerText5);
        spinner5.setAdapter(listAdapter3_5);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Add the available trains to the train spinner
        listAdapter3 = new ArrayAdapter<String>(Schedule.this, android.R.layout.simple_spinner_item, databaseAccess.getDeparturedTrains());
        listAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(listAdapter3);

        databaseAccess.close();
        //When the button is clicked the schedule of the selected train is set
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(spinner1.getSelectedItem().toString().equals("Select Train") ||spinner3.getSelectedItem().toString().equals("Time") ||spinner5.getSelectedItem().toString().equals("Year") ||spinner4.getSelectedItem().toString().equals("Month") ||spinner2.getSelectedItem().toString().equals("Day"))) {
                    Toast.makeText(Schedule.this, "Schedule Is Set", Toast.LENGTH_SHORT).show();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    databaseAccess.setSchedule(Integer.parseInt(spinner1.getSelectedItem().toString()), Double.parseDouble(spinner3.getSelectedItem().toString()), Integer.parseInt(spinner5.getSelectedItem().toString()), Integer.parseInt(spinner4.getSelectedItem().toString()), Integer.parseInt(spinner2.getSelectedItem().toString()));
                    databaseAccess.close();
                }
            }
        });
    }
}