package com.sample.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sample.adapters.RecyclerViewListAdapter;
import com.sample.barcode.R;

import java.util.ArrayList;
import java.util.Random;

public class ListDataActivity extends AppCompatActivity {
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> listData = getData();
        RecyclerViewListAdapter mAdapter = new RecyclerViewListAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<String> getData() {
        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            listData.add(i+" - "+getText());
        }
        return listData;
    }

    private String getText() {
        mRandom = new Random();
        switch (mRandom.nextInt(5)) {
            case 0:
                return "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
            case 1:
                return "Minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip";
            case 2:
                return "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
            case 3:
                return "Tex ea commodo consequat. Duis aute irure dolo";
            case 4:
                return "Excepteur sint occaecat cupidatat non proident, sunt in culp";
            default:
                return "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        }

    }
}
