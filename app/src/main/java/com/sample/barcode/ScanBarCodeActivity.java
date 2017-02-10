package com.sample.barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
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

public class ScanBarCodeActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String BARCODE = "barcode";
    private static final String TAG = "ScanBArCodeActivity";
    private SurfaceView mCameraPreview;
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceHolder mHolderTransparent;
    private Paint mPaint;
    private boolean mRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_bar_code);

        mCameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        SurfaceView mTransparentView = (SurfaceView) findViewById(R.id.TransparentView);


        mHolderTransparent = mTransparentView.getHolder();
        mHolderTransparent.setFormat(PixelFormat.TRANSPARENT);
        mPaint = new Paint();
        createCameraSource();
    }

    private void drawTransparentBackground() {
        final int TRANSPARENCY = 50;
        if (mHolderTransparent.getSurface().isValid()) {
            Canvas mCanvas = mHolderTransparent.lockCanvas();
            float screenWidth = mCameraPreview.getWidth();
            float screenHeight = mCameraPreview.getHeight();
            int squareSize = (int) ((int) screenWidth * (0.8));
            float x1, x2, y1, y2;
            x1 = (screenWidth - squareSize) / 2;
            x2 = x1 + squareSize;
            y1 = (screenHeight - squareSize) / 2;
            y2 = y1 + squareSize;
            RectF[] dark = new RectF[]{
                    new RectF(0, 0, screenWidth, y1), new RectF(0, y1, x1, y2), new RectF(x2, y1,
                    screenWidth, y2), new RectF(0, y2, screenWidth, screenHeight)};
            mPaint = new Paint();
            mPaint.setColor(Color.GREEN);
            mPaint.setAlpha((TRANSPARENCY));
            for (RectF r : dark) {
                mCanvas.drawRect(r, mPaint);
            }
            drawHorizontalLine(mCanvas);

            mHolderTransparent.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawHorizontalLine(Canvas mCanvas) {
        float screenWidth = mCameraPreview.getWidth();
        float screenHeight = mCameraPreview.getHeight();
        int squareSize = (int) ((int) screenWidth * (0.8));
        float x1, x2, y1, y2;
        x1 = (screenWidth - squareSize) / 2;
        x2 = x1 + squareSize;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        int height = mCanvas.getHeight() / 2;
        mCanvas.drawLine(x1, height, x2, height, mPaint);
    }

    private void createCameraSource() {
        mBarcodeDetector = new BarcodeDetector.Builder(this).build();
        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();
        mCameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mRunning = true;
            /*    Thread thread = new Thread(ScanBarCodeActivity.this);
               // thread.start();*/
           //     drawTransparentBackground();
                startBarcodeDetection();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mCameraSource.stop();
            }
        });
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
                    // Load the sounds
                    SoundPool soundPool = new SoundPool.Builder()
                            .setMaxStreams(10)
                            .build();
                    final int soundID = soundPool.load(ScanBarCodeActivity.this, R.raw.beep, 1);
                    soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                        @Override
                        public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                            soundPool.play(soundID, 0.5f, 0.5f, 1, 0, 1);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void startCameraSource() {
        if (ActivityCompat.checkSelfPermission(ScanBarCodeActivity.this
                , Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ScanBarCodeActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
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
        tryDrawing(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
        tryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void tryDrawing(SurfaceHolder holder) {
        Log.i(TAG, "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto the mCanvas as it's null");
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMyStuff(final Canvas canvas) {
        Log.i(TAG, "Drawing...");
        canvas.drawRGB(255, 128, 128);
    }

    @Override
    protected void onPause() {
        mRunning = false;
        super.onPause();
    }

    /*@Override
    public void run() {
        while (mRunning) {

           // drawTransparentBackground();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
}
