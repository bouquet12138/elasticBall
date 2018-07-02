package com.example.bezierball.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.bezierball.R;
import com.example.bezierball.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class BallProperty {


    private float mBallRadius;
    private int mBallNumber;
    private int mBallColor;
    private int mCurrentSelected;
    private boolean mShowBottomCircle;
    private int mBottomCircleFill;
    private int mBottomCircleStroke;
    private float mBottomCircleStrokeWidth;
    private float mCircleTouchPadding;
    private List<Integer> mColorList;//颜色列表
    private float mExtensionRatio;


    public void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BezierBall);

        mBallRadius = typedArray.getDimension(R.styleable.BezierBall_ballRadius, DensityUtil.dipToPx(context, 20));
        mBallNumber = typedArray.getInt(R.styleable.BezierBall_ballNumber, 1);
        mBallColor = typedArray.getColor(R.styleable.BezierBall_ballColor, 0xfffdc14c);

        mCurrentSelected = typedArray.getInt(R.styleable.BezierBall_currentSelected, 0);

        mShowBottomCircle = typedArray.getBoolean(R.styleable.BezierBall_showBottomCircle, true);
        mBottomCircleFill = typedArray.getColor(R.styleable.BezierBall_bottomCircleFill, 0x88ffffff);
        mBottomCircleStroke = typedArray.getColor(R.styleable.BezierBall_bottomCircleStroke, 0xffcccccc);
        mBottomCircleStrokeWidth = typedArray.getDimension(R.styleable.BezierBall_bottomCircleStrokeWidth, DensityUtil.dipToPx(context, 1));

        mCircleTouchPadding = typedArray.getDimension(R.styleable.BezierBall_circleTouchPadding, DensityUtil.dipToPx(context, 10));
        mExtensionRatio = typedArray.getFloat(R.styleable.BezierBall_extensionRatio, 1);

        mColorList = new ArrayList<>();//存放小球颜色的集合

        for (int i = 0; i < mBallNumber; i++) {//添加小球的颜色
            mColorList.add(mBallColor);
        }

        typedArray.recycle();
    }

    public float getBallRadius() {
        return mBallRadius;
    }

    public void setBallRadius(float ballRadius) {
        mBallRadius = ballRadius;
    }

    public int getBallNumber() {
        return mBallNumber;
    }

    public void setBallNumber(int ballNumber) {
        mBallNumber = ballNumber;
    }

    public int getBallColor() {
        return mBallColor;
    }

    public void setBallColor(int ballColor) {
        mBallColor = ballColor;
    }

    public int getCurrentSelected() {
        return mCurrentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        mCurrentSelected = currentSelected;
    }

    public boolean isShowBottomCircle() {
        return mShowBottomCircle;
    }

    public void setShowBottomCircle(boolean showBottomCircle) {
        mShowBottomCircle = showBottomCircle;
    }

    public int getBottomCircleFill() {
        return mBottomCircleFill;
    }

    public void setBottomCircleFill(int bottomCircleFill) {
        mBottomCircleFill = bottomCircleFill;
    }

    public int getBottomCircleStroke() {
        return mBottomCircleStroke;
    }

    public void setBottomCircleStroke(int bottomCircleStroke) {
        mBottomCircleStroke = bottomCircleStroke;
    }

    public float getBottomCircleStrokeWidth() {
        return mBottomCircleStrokeWidth;
    }

    public void setBottomCircleStrokeWidth(float bottomCircleStrokeWidth) {
        mBottomCircleStrokeWidth = bottomCircleStrokeWidth;
    }

    public float getCircleTouchPadding() {
        return mCircleTouchPadding;
    }

    public void setCircleTouchPadding(float circleTouchPadding) {
        mCircleTouchPadding = circleTouchPadding;
    }

    public List<Integer> getColorList() {
        return mColorList;
    }

    public void setColorList(List<Integer> colorList) {
        mColorList = colorList;
    }

    /**
     * 根据索引得到颜色
     *
     * @param position
     * @return
     */
    public int getColor(int position) {
        return mColorList.get(position % mColorList.size());
    }

    public float getExtensionRatio() {
        return mExtensionRatio;
    }

    public void setExtensionRatio(float extensionRatio) {
        mExtensionRatio = extensionRatio;
    }
}
