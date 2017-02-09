package com.sample.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import static com.sample.barcode.ScanBarCodeActivity.BARCODE;


public class MainActivity extends Activity {
    public static final int REQUEST_CODE_SCAN = 0;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.txtContent);

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
                    Barcode barcode = data.getParcelableExtra(BARCODE);
                    mTextView.setText(getString(R.string.barcode_result, barcode.displayValue));

                } else {
                    mTextView.setText(getString(R.string.barcode_no_found));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
