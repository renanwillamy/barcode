package com.sample.barcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sample.utils.AppConfiguration;
import com.sample.utils.Utils;

public class ConfigActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {
    private AppConfiguration mConfig;
    private CheckBox mCheckEan13, mCheckEan8, mCheckUpcA, mCheckUpcE,mCheckItf;
    private CheckBox mCheckCode39, mCheckCode93, mCheckCode128, mCheckCodeCodabar;
    private CheckBox mCheckQrCode, mCheckDataMatrix, mCheckPdf417, mCheckAztec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mConfig = Utils.loadConfiguration(this);
        mCheckEan8 = (CheckBox) findViewById(R.id.check_ean_8);
        mCheckEan13 = (CheckBox) findViewById(R.id.check_ean_13);
        mCheckUpcA = (CheckBox) findViewById(R.id.check_upc_a);
        mCheckUpcE = (CheckBox) findViewById(R.id.check_upc_e);
        mCheckItf = (CheckBox) findViewById(R.id.check_code_itf);
        mCheckCode39 = (CheckBox) findViewById(R.id.check_code_39);
        mCheckCode93 = (CheckBox) findViewById(R.id.check_code_93);
        mCheckCode128 = (CheckBox) findViewById(R.id.check_code_128);
        mCheckCodeCodabar = (CheckBox) findViewById(R.id.check_code_codabar);
        mCheckQrCode = (CheckBox) findViewById(R.id.check_qr_code);
        mCheckDataMatrix = (CheckBox) findViewById(R.id.check_data_matrix);
        mCheckPdf417 = (CheckBox) findViewById(R.id.check_pdf_417);
        mCheckAztec = (CheckBox) findViewById(R.id.check_aztec);

        mCheckEan8.setOnCheckedChangeListener(this);
        mCheckEan13.setOnCheckedChangeListener(this);
        mCheckUpcA.setOnCheckedChangeListener(this);
        mCheckUpcE.setOnCheckedChangeListener(this);
        mCheckItf.setOnCheckedChangeListener(this);
        mCheckCode39.setOnCheckedChangeListener(this);
        mCheckCode93.setOnCheckedChangeListener(this);
        mCheckCode128.setOnCheckedChangeListener(this);
        mCheckCodeCodabar.setOnCheckedChangeListener(this);
        mCheckQrCode.setOnCheckedChangeListener(this);
        mCheckDataMatrix.setOnCheckedChangeListener(this);
        mCheckPdf417.setOnCheckedChangeListener(this);
        mCheckAztec.setOnCheckedChangeListener(this);

        initCheckBox();
    }

    private void initCheckBox() {
        mCheckEan8.setChecked(mConfig.getEan8() == 1);
        mCheckEan13.setChecked(mConfig.getEan13() == 1);
        mCheckUpcA.setChecked(mConfig.getUpcA() == 1);
        mCheckUpcE.setChecked(mConfig.getUpcE() == 1);
        mCheckItf.setChecked(mConfig.getItf() == 1);
        mCheckCode39.setChecked(mConfig.getCode39() == 1);
        mCheckCode93.setChecked(mConfig.getCode93() == 1);
        mCheckCode128.setChecked(mConfig.getCode128() == 1);
        mCheckCodeCodabar.setChecked(mConfig.getCodabar() == 1);
        mCheckQrCode.setChecked(mConfig.getQrCode() == 1);
        mCheckDataMatrix.setChecked(mConfig.getDataMtrix() == 1);
        mCheckPdf417.setChecked(mConfig.getPdf417() == 1);
        mCheckAztec.setChecked(mConfig.getAztec() == 1);
    }

    private void saveConfig() {
        Utils.saveConfiguration(ConfigActivity.this, mConfig);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_ean_8:
                mCheckEan8.setChecked(mCheckEan8.isChecked());
                mConfig.setEan8((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_ean_13:
                mCheckEan13.setChecked(mCheckEan13.isChecked());
                mConfig.setEan13((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_upc_a:
                mCheckUpcA.setChecked(mCheckUpcA.isChecked());
                mConfig.setUpcA((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_upc_e:
                mCheckUpcE.setChecked(mCheckUpcE.isChecked());
                mConfig.setUpcE((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_code_itf:
                mCheckItf.setChecked(mCheckItf.isChecked());
                mConfig.setItf((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_code_39:
                mCheckCode39.setChecked(mCheckCode39.isChecked());
                mConfig.setCode39((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_code_codabar:
                mCheckCodeCodabar.setChecked(mCheckCodeCodabar.isChecked());
                mConfig.setCodabar((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_code_93:
                mCheckCode93.setChecked(mCheckCode93.isChecked());
                mConfig.setCode93((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_code_128:
                mCheckCode128.setChecked(mCheckCode128.isChecked());
                mConfig.setCode128((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_qr_code:
                mCheckQrCode.setChecked(mCheckQrCode.isChecked());
                mConfig.setQrCode((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_data_matrix:
                mCheckDataMatrix.setChecked(mCheckDataMatrix.isChecked());
                mConfig.setDataMtrix((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_pdf_417:
                mCheckPdf417.setChecked(mCheckPdf417.isChecked());
                mConfig.setPdf417((isChecked) ? 1 : 0);
                saveConfig();
                break;
            case R.id.check_aztec:
                mCheckAztec.setChecked(mCheckAztec.isChecked());
                mConfig.setAztec((isChecked) ? 1 : 0);
                saveConfig();
                break;
        }

    }
}
