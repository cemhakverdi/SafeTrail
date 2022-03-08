package com.example.safetrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Tabbed-activity which offers sub-options menu and navigates the user depending on the option selected
 * Extends fragment class in order to have its own activity different from other tab pages
 * @version 27.04.2021
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 */
public class CustomerManagement extends Fragment {

    //Instances
    private View mRootView;
    private RelativeLayout coordinatorLayout;
    private ListView listview;
    private ArrayList<String> listText;
    private ArrayAdapter<String> listAdapter;
    private String clickedText;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_customer, container, false);
        initView();
        return mRootView;     //Root view of the customer management
    }

    /**
     * Adds the customer management sub-options in the list view and starts activities depending on the clicked text
     */
    private void initView() {
        coordinatorLayout = (RelativeLayout) getActivity().findViewById(R.id.main_layout);
        listview = (ListView) mRootView.findViewById(R.id.list_view_customer);
        listText = new ArrayList<>();
        listText.add("See Customers");
        listText.add("Ban Customers");
        listText.add("Give a Discount");
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listText);
        listview.setAdapter(listAdapter);


        //Starts activities depending on the clicked text
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();
                if ("See Customers".equals(clickedText)) {
                    openCustomerActivity();
                } else if ("Ban Customers".equals(clickedText)) {
                    openCustomerActivity2();
                } else if ("Give a Discount".equals(clickedText)) {
                    openCustomerActivity3();
                }
            }
        });
    }

    /**
     * Starts "See Customers" activity
     */
    public void openCustomerActivity() {
        Intent intent = new Intent(getActivity(), SeeCustomers.class);
        startActivity(intent);
    }

    /**
     * Starts "Ban Customers" activity
     */
    public void openCustomerActivity2() {
        Intent intent = new Intent(getActivity(), BanCustomer.class);
        startActivity(intent);
    }

    /**
     * Starts "Give a Discount" activity
     */
    public void openCustomerActivity3() {
        Intent intent = new Intent(getActivity(), GiveADiscount.class);
        startActivity(intent);
    }


}
