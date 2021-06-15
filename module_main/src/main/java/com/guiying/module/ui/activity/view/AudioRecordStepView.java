package com.guiying.module.ui.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AudioRecordStepView extends View {
    Paint paint = new Paint();
    //字体sp与px关系(sp * fontScale + 0.5f)
    private final float fontScale = getResources().getDisplayMetrics().scaledDensity;;
    //绘制dp与px关系(px = dp * scale + 0.5f)
    private final float mScale = getResources().getDisplayMetrics().density;

    protected int mWidth;
    protected int mHeight;
    protected int mNumber = 3;
    protected int mStep = 0;
    protected float mLineLength = 54* mScale;
    protected float mCircleRadius = 17*mScale;
    float textSize = 22 *fontScale;
    float lineWidth = 1*mScale;
    float totalLength ;
    float textBaseLineY;
    float centreX;
    float leftX;
    float leftFirstCentrePointX;
    float leftFirstLineStartX;
    float mCentrePointX;
    float mLinePointX;




    public AudioRecordStepView(Context context) {
        super(context);
    }

    public AudioRecordStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioRecordStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textBaseLineY = (mHeight/2 + textSize/3);
        centreX = mWidth/2;
        totalLength = (mNumber * mCircleRadius*2 + (mNumber -1)*mLineLength);
        leftX = (centreX - totalLength /2);
        leftFirstCentrePointX = leftX + mCircleRadius;
        leftFirstLineStartX = leftX + mCircleRadius *2;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        mCentrePointX = leftFirstCentrePointX;
        mLinePointX = leftFirstLineStartX;
        // draw Circle
        for(int i = 0; i < mNumber;i++) {
            if(i < mStep){
                paint.setColor(Color.parseColor("#FF0076FF"));
            }else {
                paint.setColor(Color.parseColor("#FFC6D7FF"));
            }
            canvas.drawCircle(mCentrePointX, mHeight/2, mCircleRadius, paint);
            mCentrePointX += mCircleRadius *2 + mLineLength;
        }

        mCentrePointX = leftFirstCentrePointX;
        // draw text
        for(int i = 0; i < mNumber;i++) {

            if(i < mStep){
                paint.setColor(Color.parseColor("#ffffff"));
            }else {
                paint.setColor(Color.parseColor("#FF0058FF"));
            }
            paint.setStrokeWidth(1.3f);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawText(i+1+"", mCentrePointX,textBaseLineY , paint);
            mCentrePointX += mCircleRadius *2 + mLineLength;
        }


        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setStrokeWidth(lineWidth);
        // draw line
        for(int i = 0; i < mNumber -1;i++) {
            paint.setPathEffect(new DashPathEffect(new float[] {7, 7}, 0));
            canvas.drawLine( mLinePointX,mHeight/2 ,mLinePointX + mLineLength,mHeight/2, paint);
            mLinePointX += mCircleRadius *2 + mLineLength;
        }

    }

    public void setRecordNumber(int recordNumber,int recordStep){
        mNumber = recordNumber;
        mStep = recordStep;
        invalidate();
    }
}
