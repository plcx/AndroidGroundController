package com.example.webviewdemo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;

public class RockerViewMid extends RockerView{
    public RockerViewMid(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        // 获取bitmap
        mBmpRockerBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocker_bg);
        mBmpRockerBtn = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocker_btn);

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            // 调用该方法时可以获取view实际的宽getWidth()和高getHeight()
            @Override
            public boolean onPreDraw() {
                // TODO Auto-generated method stub
                getViewTreeObserver().removeOnPreDrawListener(this);

                Log.e("RockerView", getWidth() + "/" +  getHeight());
                mCenterPoint = new PointF(getWidth() / 2, getHeight() / 2);
                mRockerBg_X = mCenterPoint.x;
                mRockerBg_Y = mCenterPoint.y;

                mRockerBtn_X = mCenterPoint.x;
                mRockerBtn_Y = mCenterPoint.y;

                float tmp_f = mBmpRockerBg.getWidth() / (float)(mBmpRockerBg.getWidth() + mBmpRockerBtn.getWidth());
                mRockerBg_R = tmp_f * getWidth() / 2;
                mRockerBtn_R = (1.0f - tmp_f)* getWidth() / 2;

                return true;
            }
        });


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){

                    //系统调用onDraw方法刷新画面
                    RockerViewMid.this.postInvalidate();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            // 当触屏区域不在活动范围内
            if (Math.sqrt(Math.pow((mRockerBg_X - (int) event.getX()), 2) + Math.pow((mRockerBg_Y - (int) event.getY()), 2)) >= mRockerBg_R) {
                //得到摇杆与触屏点所形成的角度
                double tempRad = getRad(mRockerBg_X, mRockerBg_Y, event.getX(), event.getY());
                //保证内部小圆运动的长度限制
                getXY(mRockerBg_X, mRockerBg_Y, mRockerBg_R, tempRad);
            } else {//如果小球中心点小于活动区域则随着用户触屏点移动即可
                mRockerBtn_X = (int) event.getX();
                mRockerBtn_Y = (int) event.getY();
            }
            if(mRockerChangeListener != null) {
                mRockerChangeListener.report(mRockerBtn_X - mCenterPoint.x, mRockerBtn_Y - mCenterPoint.y);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            //当释放按键时摇杆要恢复摇杆的位置为初始位置
            mRockerBtn_X = mCenterPoint.x;
            mRockerBtn_Y = mCenterPoint.y;

            if(mRockerChangeListener != null) {
                mRockerChangeListener.report(0, 0);
            }


        }
        return super.onTouchEvent(event);
    }
}
