package com.guiying.module.ui.activity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.guiying.module.main.R;
import com.guiying.module.ui.activity.callback.ITouchEventListener;

import java.util.ArrayList;
import java.util.List;

public class AudioRecordView extends View {
    /** 扩散圆圈颜色 */
    private int mColor = getResources().getColor(R.color.image_corecolor);
    /** 圆圈中心颜色 */
    private int mCoreColor = getResources().getColor(R.color.image_corecolor);
    /** 圆圈中心图片 */
    private Bitmap mBitmap ;
    /** 中心圆半径 */
    private float mCoreRadius = 150;
    /** 扩散圆宽度 */
    private int mDiffuseWidth = 3;
    /** 最大宽度 */
    private Integer mMaxWidth = 255;
    /** 扩散速度 */
    private int mDiffuseSpeed = 5;
    /** 是否正在扩散中 */
    private boolean mIsDiffuse = false;
    // 透明度集合
    private List<Integer> mAlphas = new ArrayList<>();
    // 扩散圆半径集合
    private List<Integer> mWidths = new ArrayList<>();
    private Paint mPaint;

    private int mLastMotionX, mLastMotionY;
    private boolean isMoved;
    private Runnable mLongPressRunnable;
    private static final int TOUCH_SLOP = 20;
    private ITouchEventListener mITouchEventListener;

    private String mPromptMsg;
    //绘制文本sp与px换算关系
    private final float fontScale ;
    //绘制dp与sp关系
    private final float mScale;

    public AudioRecordView(Context context) {
        this(context, null);
    }

    public AudioRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AudioRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        mScale = context.getResources().getDisplayMetrics().density;
        init();
        initRunnable();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiffuseView, defStyleAttr, 0);
        mColor = a.getColor(R.styleable.DiffuseView_diffuse_color, mColor);
        mCoreColor = a.getColor(R.styleable.DiffuseView_diffuse_coreColor, mCoreColor);
        mCoreRadius = a.getFloat(R.styleable.DiffuseView_diffuse_coreRadius, mCoreRadius);
        mDiffuseWidth = a.getInt(R.styleable.DiffuseView_diffuse_width, mDiffuseWidth);
        mMaxWidth = a.getInt(R.styleable.DiffuseView_diffuse_maxWidth, mMaxWidth);
        mDiffuseSpeed = a.getInt(R.styleable.DiffuseView_diffuse_speed, mDiffuseSpeed);
        int imageId = a.getResourceId(R.styleable.DiffuseView_diffuse_coreImage, -1);
        if(imageId != -1) mBitmap = BitmapFactory.decodeResource(getResources(), imageId);
        a.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mAlphas.add(255);
        mWidths.add(0);
    }

    @Override
    public void invalidate() {
        if(hasWindowFocus()){
            super.invalidate();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            invalidate();
        }
    }


    private void initRunnable() {
        mLongPressRunnable = new Runnable() {

            @Override
            public void run() {
                if(mITouchEventListener != null) {
                    mITouchEventListener.onLongTap();
                }
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                isMoved = false;
                if(x > (getWidth() / 2 - mBitmap.getWidth() / 2) && x < (getWidth() / 2 + mBitmap.getWidth() / 2) && y>(getHeight()  - mBitmap.getHeight()-(46*mScale + 0.5f)) && y < (getHeight() -(46*mScale + 0.5f))) {
                    postDelayed(mLongPressRunnable, ViewConfiguration.getLongPressTimeout());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isMoved) break;
                if (x < (getWidth() / 2 +  mBitmap.getWidth() / 2) && x > (getWidth() / 2 - mBitmap.getWidth() / 2) &&  y>(getHeight()  - mBitmap.getHeight()-(80*mScale + 0.5f))&&
                        (Math.abs(mLastMotionX - x) < mBitmap.getWidth() / 2)  && (( mLastMotionY -y) > mBitmap.getHeight() / 2)) {
                    isMoved = true;
                    if(mITouchEventListener!= null) {
                        mITouchEventListener.onMoveCancel();
                    }
                    removeCallbacks(mLongPressRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mITouchEventListener != null) {
                    mITouchEventListener.onMoveUp();
                }
                removeCallbacks(mLongPressRunnable);
                break;
        }
        return true;
    }

    public void setAudioViewTouchListener(ITouchEventListener iTouchEventCallback){
        mITouchEventListener = iTouchEventCallback;
    }
    @Override
    public void onDraw(Canvas canvas) {
        // 绘制扩散圆
        mPaint.setColor(mColor);
        for (int i = 0; i < mAlphas.size(); i ++) {
            // 设置透明度
            Integer alpha = mAlphas.get(i);
            mPaint.setAlpha(alpha);
            // 绘制扩散圆
            Integer width = mWidths.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight()- mBitmap.getHeight()/2 -(46*mScale + 0.5f), mCoreRadius + width, mPaint);

            if(alpha > 0 && width < mMaxWidth){
                mAlphas.set(i, alpha - mDiffuseSpeed > 0 ? alpha - mDiffuseSpeed : 1);
                mWidths.set(i, width + mDiffuseSpeed);
            }
        }
        // 判断当扩散圆扩散到指定宽度时添加新扩散圆
        if (mWidths.get(mWidths.size() - 1) >= mMaxWidth / mDiffuseWidth) {
            mAlphas.add(255);
            mWidths.add(0);
        }
        // 超过10个扩散圆，删除最外层
        if(mWidths.size() >= 10){
            mWidths.remove(0);
            mAlphas.remove(0);
        }

        // 绘制中心圆及图片
        mPaint.setAlpha(255);
//        mPaint.setColor(mCoreColor);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCoreRadius, mPaint);

        if(mBitmap != null){
            canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2
                    , getHeight()  - mBitmap.getHeight()-(46*mScale + 0.5f) , mPaint);
        }

        //绘制提示文本
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setTextSize(15* fontScale + 0.5f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mPromptMsg,getWidth() / 2,getHeight()  - mBitmap.getHeight()-(46*mScale + 0.5f)-(20*mScale + 0.5f),mPaint);


        if(mIsDiffuse){
            invalidate();
        }
    }

    /**
     * 开始扩散
     */
    public void start() {
        mIsDiffuse = true;
        invalidate();
    }

    /**
     * 停止扩散
     */
    public void stop() {
        mIsDiffuse = false;
        mWidths.clear();
        mAlphas.clear();
        mAlphas.add(255);
        mWidths.add(0);
        invalidate();
    }

    /**
     * 是否扩散中
     */
    public boolean isDiffuse(){
        return mIsDiffuse;
    }

    /**
     * 设置扩散圆颜色
     */
    public void setColor(int colorId){
        mColor = colorId;
    }

    /**
     * 设置中心圆颜色
     */
    public void setCoreColor(int colorId){
        mCoreColor = colorId;
    }

    /**
     * 设置中心圆图片
     */
    public void setCoreImage(int imageId){
        mBitmap = BitmapFactory.decodeResource(getResources(), imageId);
    }

    /**
     * 设置中心圆半径
     */
    public void setCoreRadius(int radius){
        mCoreRadius = radius;
    }

    /**
     * 设置扩散圆宽度(值越小宽度越大)
     */
    public void setDiffuseWidth(int width){
        mDiffuseWidth = width;
    }

    /**
     * 设置最大宽度
     */
    public void setMaxWidth(int maxWidth){
        mMaxWidth = maxWidth;
    }

    /**
     * 设置扩散速度，值越大速度越快
     */
    public void setDiffuseSpeed(int speed){
        mDiffuseSpeed = speed;
    }

    public void setPromptMsg(int id) {
        mPromptMsg = getResources().getString(id);
        invalidate();

    }


    public void releasedAudioRecordView(){
        if(mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
        mITouchEventListener = null;

    }
}
