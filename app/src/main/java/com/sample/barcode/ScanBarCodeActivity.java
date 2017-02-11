package com.sample.barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

public class ScanBarCodeActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        Runnable {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String BARCODE = "barcode";
    public double mSquareSize = 0.80;
    private SurfaceView mCameraPreview;
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceHolder mHolderTransparent;
    private float mLineHeight;
    private Paint mPaint;
    private boolean mRunning;
    private Canvas mCanvas;
    private float mScreenWidth;
    private float mScreenHeight;
    private RectF[] mDarkRect;
    private SoundPool mSoundPool;
    private int mSoundID;
    private boolean mSoundLoaded;
    private Thread mThread;
    private boolean mReverse;
    private float mX1;
    private float mX2;
    private float mY1;
    private float mY2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_bar_code);

        mCameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        SurfaceView mTransparentView = (SurfaceView) findViewById(R.id.TransparentView);

        mHolderTransparent = mTransparentView.getHolder();
        mHolderTransparent.setFormat(PixelFormat.TRANSPARENT);
        mPaint = new Paint();
        loadSound();
        createCameraSource();
    }

    private void drawTransparentBackground() {
        final int TRANSPARENCY = 45;
        if (mDarkRect == null) {
            initDarkRect();
        }
        drawSquareBorders();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha((TRANSPARENCY));
        for (RectF r : mDarkRect) {
            mCanvas.drawRect(r, mPaint);
        }

    }

    private void drawSquareBorders() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawRect(mX1, mY1, mX2, mY2, mPaint);
    }

    private void initDarkRect() {
        int squareSize = (int) ((int) mScreenWidth * mSquareSize);
        mX1 = (mScreenWidth - squareSize) / 2;
        mX2 = mX1 + squareSize;
        mY1 = (mScreenHeight - squareSize) / 2;
        mY2 = mY1 + squareSize;
        mDarkRect = new RectF[]{
                new RectF(0, 0, mScreenWidth, mY1), new RectF(0, mY1, mX1, mY2), new RectF(mX2, mY1,
                mScreenWidth, mY2), new RectF(0, mY2, mScreenWidth, mScreenHeight)};
    }

    private void drawHorizontalLine() {
        if (mLineHeight <= mY1) {
            if (mReverse) {
                mReverse = false;
            } else {
                mLineHeight = mY1;
            }
        } else if (mLineHeight >= mY2) {
            if (!mReverse) mReverse = true;
        }
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mCanvas.drawLine(mX1, mLineHeight, mX2, mLineHeight, mPaint);
        if (mReverse) {
            mLineHeight -= 7;
        } else {
            mLineHeight += 7;
        }
    }

    private void drawCrossLine() {
        int squareSize = (int) ((int) mScreenWidth * mSquareSize);
        float y1, x1, x2;
        x1 = mX1 + (squareSize / 2);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mCanvas.drawLine(x1, mY1 + 20, x1, mY1 - 50, mPaint);
        mCanvas.drawLine(x1, mY2 + 50, x1, mY2 - 20, mPaint);
        y1 = (mY1 + (squareSize) / 2);
        x1 = (mScreenWidth - squareSize) / 2;
        x2 = mX1 + squareSize;
        mCanvas.drawLine(x1 - 50, y1, x1 + 20, y1, mPaint);
        mCanvas.drawLine(x2 - 20, y1, x2 + 50, y1, mPaint);
    }

    private void createCameraSource() {
        mBarcodeDetector = new BarcodeDetector.Builder(this).build();
        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();
        mCameraPreview.getHolder().addCallback(this);
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
        mRunning = true;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mSquareSize = 0.45;
        } else {
            mSquareSize = 0.8;
        }
        mScreenWidth = mCameraPreview.getWidth();
        mScreenHeight = mCameraPreview.getHeight();
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
        if (mThread != null) {
            if (!mThread.isAlive()) {
                startThread();
            }
        }

    }

    @Override
    protected void onPause() {
        mRunning = false;
        super.onPause();
    }

    @Override
    public void run() {
        while (mRunning) {
            if (!mHolderTransparent.getSurface().isValid()) continue;
            mCanvas = mHolderTransparent.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            drawHorizontalLine();
            drawCrossLine();
            drawTransparentBackground();

            mHolderTransparent.unlockCanvasAndPost(mCanvas);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
