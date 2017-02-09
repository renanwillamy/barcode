package com.sample.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;

import static com.sample.barcode.ScanBarCodeActivity.BARCODE;


public class MainActivity extends Activity {
    public static final int REQUEST_CODE_SCAN = 0;
    private ListView mLvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        mLvContent = (ListView) findViewById(R.id.lvContent);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanBarCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    ArrayList<String> barcodes = data.getStringArrayListExtra(BARCODE);
                    if(barcodes!=null) {
                        mLvContent.setAdapter(new ArrayAdapter<>(
                                MainActivity.this, android.R.layout.simple_list_item_1, barcodes));
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
