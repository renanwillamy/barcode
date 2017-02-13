package com.sample.barcode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.sample.utils.AppConfiguration;
import com.sample.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class ScanBarCodeActivity extends Activity implements SurfaceHolder.Callback,
        Runnable {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String BARCODE = "barcode";
    private SurfaceView mCameraPreview;
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceHolder mHolderTransparent;
    private SoundPool mSoundPool;
    private int mSoundID;
    private boolean mSoundLoaded;
    private Thread mThread;
    private GraphicOverload mGraphicOverload;
    private AppConfiguration mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_bar_code);

        mCameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        SurfaceView mTransparentView = (SurfaceView) findViewById(R.id.TransparentView);

        mHolderTransparent = mTransparentView.getHolder();
        mHolderTransparent.setFormat(PixelFormat.TRANSPARENT);
        mGraphicOverload = new GraphicOverload();
        mConfig = Utils.loadConfiguration(this);

    }

    private void createCameraSource() {
        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(mConfig.getFormats())
                .build();

        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();
        mCameraPreview.getHolder().addCallback(this);
    }

    private void stopCameraSource() {
        if (mBarcodeDetector != null) {
            mBarcodeDetector.release();
        }
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    public void startBarcodeDetection() {
        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                ArrayList<String> list = new ArrayList<>();
                if (barcodes.size() > 0) {
                    Intent intent = new Intent();
                    for (int i = 0; i < barcodes.size(); i++) {
                        list.add(barcodes.valueAt(i).rawValue);
                    }
                    intent.putExtra(BARCODE, list);
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    playSound();
                    finish();
                }
            }
        });
    }

    private void loadSound() {
        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .build();
        mSoundID = mSoundPool.load(ScanBarCodeActivity.this, R.raw.beep, 1);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mSoundLoaded = true;
            }
        });
    }

    private void playSound() {
        if (mSoundLoaded) {
            mSoundPool.play(mSoundID, 0.5f, 0.5f, 1, 0, 0);
        }
    }

    private void startCameraSource() {
        if (ActivityCompat.checkSelfPermission(ScanBarCodeActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanBarCodeActivity.this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            return;
        }
        try {
            mCameraSource.start(mCameraPreview.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                startCameraSource();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startCameraSource();
        startThread();
        startBarcodeDetection();
    }

    private void startThread() {
        mGraphicOverload.mRunning = true;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGraphicOverload.mSquareSize[0] = 0.80;
            mGraphicOverload.mSquareSize[1] = 0.40;
        } else {
            mGraphicOverload.mSquareSize[0] = 0.80;
            mGraphicOverload.mSquareSize[1] = 0.80;
        }
        mGraphicOverload.mScreenWidth = mCameraPreview.getWidth();
        mGraphicOverload.mScreenHeight = mCameraPreview.getHeight();
        mThread = new Thread(ScanBarCodeActivity.this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        Log.d("SurfaceTest", "Resume");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCameraSource.stop();
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSound();
        createCameraSource();
        if (mThread != null) {
            if (!mThread.isAlive()) {
                startThread();
            }
        }
    }

    @Override
    protected void onPause() {
        mGraphicOverload.mRunning = false;
        stopCameraSource();
        super.onPause();
    }

    @Override
    public void run() {
        while (mGraphicOverload.mRunning) {
            if (!mHolderTransparent.getSurface().isValid()) continue;
            mGraphicOverload.mCanvas = mHolderTransparent.lockCanvas();
            mGraphicOverload.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            mGraphicOverload.drawHorizontalLine();
            mGraphicOverload.drawCrossLine();
            mGraphicOverload.drawTransparentBackground();

            mHolderTransparent.unlockCanvasAndPost(mGraphicOverload.mCanvas);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void gotToConfig(View view) {
        startActivity(new Intent(this, ConfigActivity.class));
    }
}
