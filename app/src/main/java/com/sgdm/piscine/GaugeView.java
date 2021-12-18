package com.sgdm.piscine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;


public class GaugeView extends View {
public float mCL,mPH,mTE, mTX;
    private Paint arcPaint;

    public GaugeView(Context context) {
        super(context);
        initialize();
    }

    public GaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.piscine,
                0, 0);

        try {
            mCL = a.getFloat (R.styleable.piscine_cl, 7.3f);
            mPH = a.getFloat (R.styleable.piscine_ph, 6.8f);
            mTE = a.getFloat (R.styleable.piscine_te, 22f);
            mTX = a.getFloat (R.styleable.piscine_tx, 19f);

        } finally {
            a.recycle();
        }
        initialize();
    }

    public GaugeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arcPaint.setStrokeWidth(40f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth ();
        int height = getHeight ();
        float arcCenterX = width / 4;
        float arcCenterY = 300;
        int largeur = 200;
        final RectF arcBounds = new RectF ( arcCenterX - largeur, arcCenterY - largeur, arcCenterX + largeur, arcCenterY + largeur );


        // Draw the arc
        arcPaint.setStrokeWidth ( 50f );
        arcPaint.setStyle ( Paint.Style.STROKE );
        arcPaint.setStrokeCap ( Paint.Cap.ROUND );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_red_600 ) );
        canvas.drawArc ( arcBounds, 180f, 20f, false, arcPaint );
        arcPaint.setStrokeCap ( Paint.Cap.BUTT );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_yellow_500 ) );
        canvas.drawArc ( arcBounds, 200f, 40f, false, arcPaint );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_green_400 ) );
        canvas.drawArc ( arcBounds, 240f, 60f, false, arcPaint );


        arcPaint.setStrokeCap ( Paint.Cap.ROUND );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_red_600 ) );
        canvas.drawArc ( arcBounds, 340f, 20f, false, arcPaint );
        arcPaint.setStrokeCap ( Paint.Cap.BUTT );

        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_yellow_500 ) );
        canvas.drawArc ( arcBounds, 300f, 40f, false, arcPaint );

        canvas.save ();
        arcPaint.setStyle ( Paint.Style.FILL_AND_STROKE );
        arcPaint.setStrokeWidth ( 5f );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_black_1000 ) );
        float rotation = (mPH - 6) * 90;
        canvas.rotate ( rotation, arcCenterX, arcCenterY );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_blue_100 ) );
        canvas.drawCircle ( arcCenterX - 200, arcCenterY, 15, arcPaint );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_blue_900 ) );
        canvas.drawCircle ( arcCenterX - 200, arcCenterY, 5, arcPaint );

        canvas.restore ();
        arcPaint.setTextSize ( 60 );
        //String test = String.format("%.02f", f);
        String tmp = String.format ( "%.02f", mCL ) + "\n CL";
        arcPaint.setTextAlign ( Paint.Align.CENTER );
        canvas.drawText ( tmp, arcCenterX, arcCenterY, arcPaint );


        // PH Arc circle
        arcCenterX = width * .75f;
        final RectF arcBounds2 = new RectF ( arcCenterX - largeur, arcCenterY - largeur, arcCenterX + largeur, arcCenterY + largeur );
        //canvas.drawRect ( arcBounds2,arcPaint );
        arcPaint.setStrokeWidth ( 50f );
        arcPaint.setStyle ( Paint.Style.STROKE );

        arcPaint.setStrokeCap ( Paint.Cap.ROUND );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_red_600 ) );
        canvas.drawArc ( arcBounds2, 180f, 20f, false, arcPaint );

        arcPaint.setStrokeCap ( Paint.Cap.BUTT );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_yellow_500 ) );
        canvas.drawArc ( arcBounds2, 200f, 40f, false, arcPaint );

        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_green_400 ) );
        canvas.drawArc ( arcBounds2, 240f, 60f, false, arcPaint );

        arcPaint.setStrokeCap ( Paint.Cap.ROUND );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_red_600 ) );
        canvas.drawArc ( arcBounds2, 340f, 20f, false, arcPaint );

        arcPaint.setStrokeCap ( Paint.Cap.BUTT );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_yellow_500 ) );
        canvas.drawArc ( arcBounds2, 300f, 40f, false, arcPaint );

        canvas.save ();
        arcPaint.setStyle ( Paint.Style.FILL_AND_STROKE );
        arcPaint.setStrokeWidth ( 5f );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_black_1000 ) );
        rotation = (mCL - 6) * 90;
        canvas.rotate ( rotation, arcCenterX, arcCenterY );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_blue_100 ) );
        canvas.drawCircle ( arcCenterX - 200, arcCenterY, 15, arcPaint );
        arcPaint.setColor ( ContextCompat.getColor ( getContext (), R.color.md_blue_900 ) );
        canvas.drawCircle ( arcCenterX - 200, arcCenterY, 5, arcPaint );
        canvas.restore ();
        arcPaint.setTextSize ( 60 );
        tmp = String.format ( "%.02f", mPH ) + "\n P.H. " ;

        arcPaint.setTextAlign ( Paint.Align.CENTER );
        canvas.drawText ( tmp, arcCenterX, arcCenterY, arcPaint );

/*
        arcCenterX = width/10*4;
        arcCenterY = height/2;



        //// Quart de cercle Temperature eau
        float ecart =320;
        final RectF RectTempEau = new RectF(arcCenterX - ecart, arcCenterY - ecart, arcCenterX+ecart , arcCenterY + ecart);







        Shader shader = new SweepGradient (arcCenterX - ecart,arcCenterY + ecart, Color.YELLOW, Color.RED);
        arcPaint.setShader ( shader );


        // Draw the arc
        arcPaint.setStrokeWidth ( 40f );
        canvas.drawArc(RectTempEau, 180f, 90f, false, arcPaint);
        //arcPaint.setColor(Color.DKGRAY);
        //canvas.drawArc(RectTempEau, 200f, 40f, false, arcPaint);
        //arcPaint.setColor(Color.GRAY);
        //canvas.drawArc(RectTempEau, 2400f, 30f, false, arcPaint);

        // Draw the pointers
        arcPaint.setShader ( null );
        final int totalNoOfPointers = 20;
        final int pointerMaxHeight = 25;
        final int pointerMinHeight = 15;

        float startX = arcCenterX - 360;
        float  startY = arcCenterY;
        arcPaint.setStrokeWidth(5f);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        int pointerHeight;
        for (int i = 0; i <= totalNoOfPointers; i++) {
            if(i%5 == 0){
                pointerHeight = pointerMaxHeight;
            }else{
                pointerHeight = pointerMinHeight;
            }
            canvas.drawLine(startX, startY, startX - pointerHeight, startY, arcPaint);
            canvas.rotate(90f/totalNoOfPointers, arcCenterX, arcCenterY);
        }



        }*/
    }
    }
