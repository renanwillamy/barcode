package com.sample.utilities;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;
import java.util.ArrayList;

public class AppConfiguration implements Serializable {
    private int mEan13;
    private int mEan8;
    private int mUpcA;
    private int mUpcE;
    private int mCode39;
    private int mCode93;
    private int mCode128;
    private int mItf;
    private int mCodabar;
    private int mQrCode;
    private int mDataMtrix;
    private int mPdf417;
    private int mAztec;

    AppConfiguration() {
        mEan13 = 1;
        mEan8 = 1;
        mUpcA = 1;
        mUpcE = 1;
        mCode39 = 1;
        mCode93 = 1;
        mCode128 = 1;
        mItf = 1;
        mCodabar = 1;
        mQrCode = 1;
        mDataMtrix = 1;
        mPdf417 = 1;
        mAztec = 1;
    }

    public int getEan13() {
        return mEan13;
    }

    public void setEan13(int mEan13) {
        this.mEan13 = mEan13;
    }

    public int getEan8() {
        return mEan8;
    }

    public void setEan8(int mEan8) {
        this.mEan8 = mEan8;
    }

    public int getUpcA() {
        return mUpcA;
    }

    public void setUpcA(int mUpcA) {
        this.mUpcA = mUpcA;
    }

    public int getUpcE() {
        return mUpcE;
    }

    public void setUpcE(int mUpcE) {
        this.mUpcE = mUpcE;
    }

    public int getCode39() {
        return mCode39;
    }

    public void setCode39(int mCode39) {
        this.mCode39 = mCode39;
    }

    public int getCode93() {
        return mCode93;
    }

    public void setCode93(int mCode93) {
        this.mCode93 = mCode93;
    }

    public int getCode128() {
        return mCode128;
    }

    public void setCode128(int mCode128) {
        this.mCode128 = mCode128;
    }

    public int getItf() {
        return mItf;
    }

    public void setItf(int mItf) {
        this.mItf = mItf;
    }

    public int getCodabar() {
        return mCodabar;
    }

    public void setCodabar(int mCodabar) {
        this.mCodabar = mCodabar;
    }

    public int getQrCode() {
        return mQrCode;
    }

    public void setQrCode(int mCqrCode) {
        this.mQrCode = mCqrCode;
    }

    public int getDataMtrix() {
        return mDataMtrix;
    }

    public void setDataMtrix(int mDataMtrix) {
        this.mDataMtrix = mDataMtrix;
    }

    public int getPdf417() {
        return mPdf417;
    }

    public void setPdf417(int mPdf417) {
        this.mPdf417 = mPdf417;
    }

    public int getAztec() {
        return mAztec;
    }

    public void setAztec(int mAztec) {
        this.mAztec = mAztec;
    }

    public int getFormats() {
        ArrayList<Integer> list = new ArrayList<>();
        int value = 0;
        if (mEan8 == 1) list.add(Barcode.EAN_8);
        if (mEan13 == 1) list.add(Barcode.EAN_13);
        if (mUpcA == 1) list.add(Barcode.UPC_A);
        if (mUpcE == 1) list.add(Barcode.UPC_E);
        if (mCode39 == 1) list.add(Barcode.CODE_39);
        if (mCode93 == 1) list.add(Barcode.CODE_93);
        if (mCode128 == 1) list.add(Barcode.CODE_128);
        if (mItf == 1) list.add(Barcode.ITF);
        if (mCodabar == 1) list.add(Barcode.CODABAR);
        if (mQrCode == 1) list.add(Barcode.QR_CODE);
        if (mDataMtrix == 1) list.add(Barcode.DATA_MATRIX);
        if (mPdf417 == 1) list.add(Barcode.PDF417);
        if (mAztec == 1) list.add(Barcode.AZTEC);

        if (list.size() == 12) {
            return Barcode.ALL_FORMATS;
        }
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size()) {
                if (value == 0) {
                    value = list.get(i) | list.get(i + 1);
                } else {
                    value = value | list.get(i) | list.get(i + 1);
                }
            } else {
                value = value | list.get(i);
            }
        }
        return value;
    }
}
