package com.example.safetrail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Tabbed-activity which offers sub-options menu and navigates the user depending on the option selected
 * Extends fragment class in order to have its own activity different from other tab pages
 * @authors Göktuğ Kuşcu, Murat Güney Kemal, Cem Hakverdi, İpek Tüfekcioğlu, Furkan Yıldırım
 * @version 29.04.2021
 *
 */
public class TrainManagement extends Fragment {

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
        mRootView = inflater.inflate(R.layout.fragment_train, container, false);
        initView();
        return mRootView;   //Root view of the train management fragment
    }

    /**
     * Adds the train management sub-options in the list view and starts activities depending on the clicked text
     */
    private void initView() {
        coordinatorLayout = (RelativeLayout) getActivity().findViewById(R.id.main_layout);
        listview = (ListView) mRootView.findViewById(R.id.list_view_train);
        listText = new ArrayList<>();
        listText.add("Train Operations");
        listText.add("Line Operations");
        listText.add("Schedule");
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listText);
        listview.setAdapter(listAdapter);


         // Starts activities depending on the clicked text
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedText = parent.getItemAtPosition(position).toString();
                if ("Train Operations".equals(clickedText)) {
                    openMainActivity();
                } else if ("Line Operations".equals(clickedText)) {
                    openTrainActivity2();
                } else if ("Schedule".equals(clickedText)) {
                    openTrainActivity3();
                }
            }
        });
    }

    /**
     * Starts "Train Operations" activity
     */
    public void openMainActivity() {
        Intent intent = new Intent(getActivity(), TrainOperations.class);
        startActivity(intent);
    }

    /**
     * Starts "Line Operations" activity
     */
    public void openTrainActivity2() {
        Intent intent = new Intent(getActivity(), LineOperations.class);
        startActivity(intent);
    }

    /**
     * Starts "Schedule" activity
     */
    public void openTrainActivity3() {
        Intent intent = new Intent(getActivity(), Schedule.class);
        startActivity(intent);
    }

}

