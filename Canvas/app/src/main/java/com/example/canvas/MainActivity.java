package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //initial offset for rectangle
    private static final int OFFSET = 120;
    // Multiplier for random colour selection
    private static final int MULTIPLIER = 100;

    // Canvas object - stores what to draw onto associated bitmap
    private Canvas mCanvas;

    //paint object stores how to draw
    private Paint mPaint = new Paint();

    // create object for underlining text
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    // bitmap - pixels to be displayed
    private Bitmap mBitmap;

    // layout
    private ImageView mImageView;

    // rectangle object
    private Rect mRect = new Rect();
    // distance of rectangle edge to canvas
    private int mOffset = OFFSET;
    // rounding box for text
    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.myimageview);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);

        mPaint.setColor(mColorBackground);

        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);


        // You cannot create the Canvas in onCreate(),
        // because the views have not been
        // laid out, so their final size is not available.
    }


    /**
     * Click handler that responds to user taps by drawing an increasingly
     * smaller rectangle until it runs out of room. Then it draws a circle
     * with the text "Done!". Demonstrates basics of drawing on canvas.
     *   1. Create bitmap.
     *   2. Associate bitmap with view.
     *   3. Create canvas with bitmap.
     *   4. Draw on canvas.
     *   5. Invalidate the view to force redraw.
     *
     * @param view The view in which we are drawing.
     */
    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        // Only do this first time view is clicked after it has been created.
        if (mOffset == OFFSET) {
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;

        } else {
            // Draw in response to user action.
            // As this happens on the UI thread, there is a limit to complexity.

            if (mOffset < halfWidth && mOffset < halfHeight) {
                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                mRect.set(
                        mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                // Increase the indent.
                mOffset += OFFSET;

            } else {
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, mPaint);
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);

            }
        }
     //invalidate view so it is redrawn
        view.invalidate();
    }
}
