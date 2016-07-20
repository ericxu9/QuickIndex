package com.xuyongjun.quickindex.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ============================================================
 * 作 者 : XYJ
 * 版 本 ： 1.0
 * 创建日期 ： 2016/7/19 15:22
 * 描 述 ：字母索引栏，根据索引定位到指定位置
 * 修订历史 ：
 * ============================================================
 **/
public class QuickIndexBar extends View {

    /*
    画笔
     */
    private Paint mPaint;

    /*
    字母
     */
    private String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H"
            , "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
            , "U", "V", "W", "X", "Y", "Z", "#"};
    /*
    View宽高
     */
    private int mWidth;
    private int mHeight;

    /*
    触摸的字母的Index
     */
    private int mTouchIndex = -1;

    public QuickIndexBar(Context context)
    {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化 Paint等
     */
    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setColor(Color.GRAY);//白色
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);//加粗
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight() / mLetters.length;

        //设置文字大小  等于宽的一半
        mPaint.setTextSize(mWidth * 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        for (int i = 0; i < mLetters.length; i++) {
            //测量字母的宽
            float textWidth = mPaint.measureText(mLetters[i]);

            //获取字母的高
            Rect rect = new Rect();
            mPaint.getTextBounds(mLetters[i], 0, mLetters[i].length(), rect);
            float textHeight = rect.height();

            //计算字母绘制的x、y坐标
            float x = mWidth / 2.0f - textWidth / 2.0f;
            float y = mHeight / 2.0f + textHeight / 2.0f + mHeight * i;

            mPaint.setColor(i == mTouchIndex? Color.RED : Color.GRAY) ;

            //绘制A-Z的字母
            canvas.drawText(mLetters[i], x, y, mPaint);

        }
    }

    /**
     * 处理字母Touch事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int index = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取当前的Y坐标/每个字母的高度，就能得到按下的是哪个字母索引
                index = (int) (event.getY() / mHeight);
                // 判断滑动的距离
                if (index >= 0 && index < mLetters.length) {
                    if (mTouchIndex != index) {
                        if (mListener != null) {
                            mListener.onLetterUpdate(mLetters[index]);
                        }
                    }
                    mTouchIndex = index;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取当前的Y坐标/每个字母的高度，就能得到按下的是哪个字母索引
                index = (int) (event.getY() / mHeight);
                // 判断滑动的距离
                if (index >= 0 && index < mLetters.length) {
                    // 只有当前的Index和之前的不一致  才会调用；否则会一直调用该方法
                    if (mTouchIndex != index) {
                        if (mTouchIndex != index) {
                            if (mListener != null) {
                                mListener.onLetterUpdate(mLetters[index]);
                            }
                        }
                    }
                    mTouchIndex = index;
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouchIndex = -1;//重置数字
                break;
        }
        invalidate();
        return true;
    }

    /*
     设置回调接口
     */
    private OnLetterUpdateListener mListener;

    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);
    }

    /**
     * 设置字母更新
     *
     * @param listener
     */
    public void setOnLetterUpdateListener(OnLetterUpdateListener listener)
    {
        this.mListener = listener;
    }
}
