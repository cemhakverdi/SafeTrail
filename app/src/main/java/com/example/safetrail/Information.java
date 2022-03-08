package com.example.safetrail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Tabbed-activity which displays the daily customer number transacting, total number of tickets sold and number of trains departed
 * Extends fragment class in order to have its own activity different from other tab pages
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 29.04.2021
 */
public class Information extends Fragment {

    //Instances
    private View mRootView;
    private EditText all_customers;
    private EditText all_tickets;
    private EditText all_trains;
    private Button refresh;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_information, container, false);

        //Initialization of the instances
        all_customers = mRootView.findViewById(R.id.list_view_info_customer);
        all_tickets = mRootView.findViewById(R.id.list_view_info_ticket);
        all_trains = mRootView.findViewById(R.id.list_view_info_train);
        refresh = mRootView.findViewById(R.id.refresh);



         // If refresh button is clicked, the information in each section is updated
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                databaseAccess.open();
                String number = databaseAccess.getNumbersOfCustomers();
                if (number == null)
                {
                    number = "0";
                }
                all_customers.setText(number);
                all_tickets.setText(databaseAccess.getAllTickets());
                all_trains.setText(databaseAccess.getNumberOfTrainsDeparted());
                databaseAccess.close();
            }
        });

        return mRootView;    //Root view of the information fragment
    }


}
