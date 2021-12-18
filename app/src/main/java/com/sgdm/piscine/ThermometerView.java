package com.sgdm.piscine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ThermometerView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Paint paint;
    public ThermometerView(Context context) {
        super ( context );
        init ( null, 0 );
    }

    public ThermometerView(Context context, AttributeSet attrs) {

        super ( context, attrs );
        init ( attrs, 0 );
    }

    public ThermometerView(Context context, AttributeSet attrs, int defStyle) {
        super ( context, attrs, defStyle );
        init ( attrs, defStyle );
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext ().obtainStyledAttributes (
                attrs, R.styleable.viewThermometer, defStyle, 0 );
/*
        mExampleString = a.getString (
                R.styleable.viewThermometer_exampleString );
        mExampleColor = a.getColor (
                R.styleable.viewThermometer_exampleColor,
                mExampleColor );
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension (
                R.styleable.viewThermometer_exampleDimension,
                mExampleDimension );

        if (a.hasValue ( R.styleable.viewThermometer_exampleDrawable )) {
            mExampleDrawable = a.getDrawable (
                    R.styleable.viewThermometer_exampleDrawable );
            mExampleDrawable.setCallback ( this );
        }

        a.recycle ();


 */
        // Set up a default TextPaint object
        mTextPaint = new TextPaint ();
        mTextPaint.setFlags ( Paint.ANTI_ALIAS_FLAG );
        mTextPaint.setTextAlign ( Paint.Align.LEFT );

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements ();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize ( mExampleDimension );
        mTextPaint.setColor ( mExampleColor );
        mTextWidth = mTextPaint.measureText ( mExampleString );

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics ();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw ( canvas );


        float radius = 600f;
        float mX =1000;
        float mY=200;
        RectF oval = new RectF(mX - radius, mY - radius, mX + radius, mY + radius);
        canvas.drawArc(oval, -90, 90, false, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(oval, -90, 89, false, paint);

    }

   }