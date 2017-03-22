package com.sample.models.barcode;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class GraphicOverload {
    private float mLineHeight;
    private Paint mPaint;
    public double[] mSquareSize = {0.80, 0.80};
    public Canvas mCanvas;
    public float mScreenWidth;
    public float mScreenHeight;
    public boolean mRunning;
    private RectF[] mDarkRect;
    private boolean mReverse;
    private float mX1;
    private float mX2;
    private float mY1;
    private float mY2;

    public GraphicOverload() {
        mPaint = new Paint();
    }

    public void drawTransparentBackground() {
        final int TRANSPARENCY = 55;
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
        int squareSizeWidth = (int) ((int) mScreenWidth * mSquareSize[0]);
        int squareSizeHeight = (int) ((int) mScreenWidth * mSquareSize[1]);
        mX1 = (mScreenWidth - squareSizeWidth) / 2;
        mX2 = mX1 + squareSizeWidth;
        mY1 = (mScreenHeight - squareSizeHeight) / 2;
        mY2 = mY1 + squareSizeHeight;
        mDarkRect = new RectF[]{
                new RectF(0, 0, mScreenWidth, mY1), new RectF(0, mY1, mX1, mY2), new RectF(mX2, mY1,
                mScreenWidth, mY2), new RectF(0, mY2, mScreenWidth, mScreenHeight)};
    }

    public void drawHorizontalLine() {
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

    public void drawCrossLine() {
        int squareSizeWidth = (int) ((int) mScreenWidth * mSquareSize[0]);
        int squareSizeHeight = (int) ((int) mScreenWidth * mSquareSize[1]);
        float y1, x1, x2;
        x1 = mX1 + (squareSizeWidth / 2);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mCanvas.drawLine(x1, mY1 + 20, x1, mY1 - 50, mPaint);
        mCanvas.drawLine(x1, mY2 + 50, x1, mY2 - 20, mPaint);
        y1 = (mY1 + (squareSizeHeight) / 2);
        x1 = (mScreenWidth - squareSizeWidth) / 2;
        x2 = mX1 + squareSizeWidth;
        mCanvas.drawLine(x1 - 50, y1, x1 + 20, y1, mPaint);
        mCanvas.drawLine(x2 - 20, y1, x2 + 50, y1, mPaint);
    }
}
