package com.sample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sample.barcode.R;
import com.sample.list.ListDataActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToScan(View view) {
        startActivity(new Intent(this, BarcodeActivity.class));
    }

    public void goToListData(View view) {
        startActivity(new Intent(this, ListDataActivity.class));
    }
}
