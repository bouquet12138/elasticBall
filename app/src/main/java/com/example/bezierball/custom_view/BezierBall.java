package com.example.bezierball.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.bezierball.bean.BallProperty;
import com.example.bezierball.util.ColorUtil;
import com.example.bezierball.util.DensityUtil;

import java.util.List;

public class BezierBall extends View {

    private static final String TAG = "BezierBall";

    private Paint mPaint;//画笔
    private Paint mBottomCirclePaint;//底部圆画笔
    private Paint mBottomStrokePaint;//底部圆描边画笔
    private Path mPath = new Path();//路径

    private float mViewX, mViewY;//view的图形中心

    private float mViewWidth, mViewHeight;//view的宽
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;

    private float mX1, mY1, mX2, mY2, mX3, mY3, mX4, mY4;//圆上下左右四个点

    private float mX12, mY12, mX23, mY23, mX34, mY34, mX41, mY41;//圆形左上右下四个点之间的渐变点

    private BallProperty mBallProperty;

    private float mSpringBack, mNormalTime;//回弹用时和正常用时

    public BezierBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化一下
     */
    private void init(Context context, @Nullable AttributeSet attrs) {

        mBallProperty = new BallProperty();
        mBallProperty.getAttrs(context, attrs);//得到各个属性

        mSpringBack = 0.1f;
        mNormalTime = (1 - mSpringBack) / 4;

        mPaint = new Paint();
        mPaint.setColor(mBallProperty.getBallColor());
        mPaint.setAntiAlias(true);//抗锯齿

        mBottomCirclePaint = new Paint();
        mBottomCirclePaint.setColor(mBallProperty.getBottomCircleFill());
        mBottomCirclePaint.setAntiAlias(true);//抗锯齿
        mBottomCirclePaint.setStyle(Paint.Style.FILL);//填充

        mBottomStrokePaint = new Paint();
        mBottomStrokePaint.setStrokeWidth(mBallProperty.getBottomCircleStrokeWidth());
        mBottomStrokePaint.setColor(mBallProperty.getBottomCircleStroke());
        mBottomStrokePaint.setStyle(Paint.Style.STROKE);//描边
    }

    /**
     * 当尺寸发生改变时
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();//得到上下左右四个padding
        mViewWidth = w;
        mViewHeight = h;
        mViewY = (mViewHeight - mPaddingTop - mPaddingBottom) / 2;//中心为view的中心
        move(0, mBallProperty.getCurrentSelected());//绘制一下
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBallProperty.isShowBottomCircle()) {
            for (int i = 0; i < mBallProperty.getBallNumber() && mBallProperty.getBallNumber() != 1; i++) {
                float radius = mBallProperty.getBallRadius();
                float realWidth = mViewWidth - 2 * radius - mPaddingLeft - mPaddingRight;//圆点坐标能移动的范围
                int x = (int) (radius + mPaddingLeft + realWidth / (mBallProperty.getBallNumber() - 1) * i);
                canvas.drawCircle(x, mViewY, radius - mBallProperty.getBottomCircleStrokeWidth(), mBottomCirclePaint);//绘制填充
                canvas.drawCircle(x, mViewY, radius - mBallProperty.getBottomCircleStrokeWidth(), mBottomStrokePaint);//绘制描边
            }
        }

        mPath.reset();//重置一下
        mPath.moveTo(mX1, mY1);
        mPath.cubicTo(mX1, mY1, mX12, mY12, mX2, mY2);
        mPath.cubicTo(mX2, mY2, mX23, mY23, mX3, mY3);
        mPath.cubicTo(mX3, mY3, mX34, mY34, mX4, mY4);
        mPath.cubicTo(mX4, mY4, mX41, mY41, mX1, mY1);

        mPath.close();

        canvas.drawPath(mPath, mPaint);//绘制一下
    }

    /**
     * 控制小球移动的方法 范围 [0 ,1]
     *
     * @param percent
     */
    public void move(float percent, int position) {

        if (position < mBallProperty.getCurrentSelected())
            moveLeft(percent, position);//向左滑
        else
            moveRight(percent, position);//向右滑


        mX23 = mX34 = mX3;//设置一下控制点
        mY12 = mY23 = mY2;//设置下控制点

        mX12 = mX41 = mX1;
        mY34 = mY41 = mY4;

        postInvalidate();//重绘
    }

    /**
     * 向左滑
     *
     * @param percent
     * @param position
     */
    private void moveLeft(float percent, int position) {

        float extensionRadius = mBallProperty.getExtensionRatio();
        float radius = mBallProperty.getBallRadius();//球的半径
        int realWidth = (int) (mViewWidth - 2 * radius - mPaddingLeft - mPaddingRight);//圆点坐标能移动的范围

        int eachLength = mBallProperty.getBallNumber() == 1 ? realWidth
                : realWidth / (mBallProperty.getBallNumber() - 1);//每一段的长度

        int start = mBallProperty.getBallNumber() == 1 ? (int) (mPaddingLeft + radius) :
                (int) (eachLength * position + mPaddingLeft + radius);//开始位置


        if (percent <= mSpringBack) {//回弹时
            mViewX = start;//在半径的位置

            mPaint.setColor(mBallProperty.getColor(position));
        } else if (percent < 1 - mNormalTime) {
            mViewX = start + (percent - mSpringBack) / (mNormalTime * 3) * eachLength;
            float fraction = (percent - mSpringBack) / (1 - mNormalTime - mSpringBack);
            int color = ColorUtil.getCurrentColor(fraction, mBallProperty.getColor(position), mBallProperty.getColor(position + 1));
            mPaint.setColor(color);
        } else {
            mViewX = start + eachLength;
            mPaint.setColor(mBallProperty.getColor(position + 1));
        }
        calculatePoints();

        //右边控制点
        if (percent < mSpringBack / 2)//左边不动 右边压缩
            mX3 = mViewX + radius - (radius * ((percent) / mSpringBack) * 1.25f);
        else if (percent < mSpringBack)
            mX3 = mViewX + radius + (radius * ((percent - mSpringBack) / mSpringBack) * 1.25f);
        else if (percent < mSpringBack + mNormalTime)//右边拉长
            mX3 = mViewX + radius + extensionRadius * radius * ((percent - mSpringBack) / mNormalTime);
        else if (percent < mSpringBack + mNormalTime * 2)//保持拉长状态
            mX3 = mViewX + radius + radius * extensionRadius;
        else if (percent < mSpringBack + mNormalTime * 3)//压缩
            mX3 = mViewX + radius + radius * extensionRadius * (1 - ((percent - mNormalTime * 2 - mSpringBack) / mNormalTime));//复原
        else//正常形态
            mX3 = mViewX + radius;

        //左边控制点
        if (percent < mSpringBack + mNormalTime)//原态
            mX1 = mViewX - radius;
        else if (percent < mSpringBack + mNormalTime * 2) {//左边拉长
            float ratio = (percent - mSpringBack - mNormalTime) / mNormalTime;
            mX1 = mViewX - radius - extensionRadius * radius * ratio;
        } else if (percent < mSpringBack + mNormalTime * 3)//保持拉长
            mX1 = mViewX - radius - radius * extensionRadius;
        else {
            float ratio = (percent - 1) / mNormalTime;
            mX1 = mViewX - radius + extensionRadius * radius * ratio;//复原
        }

    }

    /**
     * 向右滑
     *
     * @param percent
     * @param position
     */
    private void moveRight(float percent, int position) {

        float radius = mBallProperty.getBallRadius();
        float extensionRadius = mBallProperty.getExtensionRatio();
        int realWidth = (int) (mViewWidth - 2 * radius - mPaddingLeft - mPaddingRight);//圆点坐标能移动的范围

        int eachLength = mBallProperty.getBallNumber() == 1 ? realWidth
                : realWidth / (mBallProperty.getBallNumber() - 1);//每一段的长度

        int start = mBallProperty.getBallNumber() == 1 ? (int) (mPaddingLeft + radius) :
                (int) (eachLength * position + mPaddingLeft + radius);//开始位置


        if (percent <= mNormalTime) {
            mViewX = start;//在半径的位置
            mPaint.setColor(mBallProperty.getColor(position));
        } else if (percent < 1 - mSpringBack) {//平移状态
            mViewX = start + (percent - mNormalTime) / (mNormalTime * 3) * eachLength;
            float fraction = (percent - mNormalTime) / (1 - mNormalTime - mSpringBack);
            int color = ColorUtil.getCurrentColor(fraction, mBallProperty.getColor(position), mBallProperty.getColor(position + 1));
            mPaint.setColor(color);
        } else {//复原状态
            mViewX = start + eachLength;
            mPaint.setColor(mBallProperty.getColor(position + 1));
        }

        calculatePoints();

        //右边半圆
        if (percent < mNormalTime)//拉伸
            mX3 = mViewX + radius + radius * (percent * 5) * extensionRadius;
        else if (percent < 2 * mNormalTime)//最长
            mX3 = mViewX + radius + radius * extensionRadius;
        else if (percent < 3 * mNormalTime)//压缩
            mX3 = mViewX + radius + radius * extensionRadius * (1 - ((percent - 2 * mNormalTime) / mNormalTime));
        else
            mX3 = mViewX + radius;//复原

        //左边控制点
        if (percent < mNormalTime)//开始左边不动
            mX1 = mViewX - radius;
        else if (percent < mNormalTime * 2)
            mX1 = mViewX - radius - radius * ((percent - mNormalTime) / mNormalTime) * extensionRadius;//拉长
        else if (percent < mNormalTime * 3)//左边一直是拉长状态
            mX1 = mViewX - radius - radius * extensionRadius;
        else if (percent < mNormalTime * 4)
            mX1 = mViewX - radius - radius * extensionRadius * (1 - ((percent - mNormalTime * 3) / mNormalTime));//复原
        else if (percent < 1 - (mSpringBack / 2f))
            mX1 = mViewX - radius + (radius * ((percent - (1 - mSpringBack)) / mSpringBack) * 1.25f);//压缩
        else
            mX1 = mViewX - radius - (radius * ((percent - 1) / mSpringBack) * 1.25f);//复原

    }

    /**
     * 计算各个点坐标
     */
    private void calculatePoints() {
        mX1 = mViewX - mBallProperty.getBallRadius();//x1的位置
        mX2 = mViewX;
        mX3 = mViewX + mBallProperty.getBallRadius();
        mX4 = mViewX;

        mY1 = mViewY;
        mY2 = mViewY - mBallProperty.getBallRadius();
        mY3 = mViewY;
        mY4 = mViewY + mBallProperty.getBallRadius();
    }


    /**
     * 当view wrap_content的默认大小 宽640dp 高 40p
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec); // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 设置wrap_content的默认宽 / 高值 // 默认宽/高的设定并无固定依据,根据需要灵活设置 // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = DensityUtil.dipToPx(getContext(), 300);
        int mHeight = DensityUtil.dipToPx(getContext(), 40); // 当模式是AT_MOST（即wrap_content）时设置默认值
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight); // 宽 / 高任意一个模式为AT_MOST（即wrap_content）时，都设置默认值
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        }

    }

    /**
     * 设置球的数量
     *
     * @param number
     */
    public void setBallNumber(int number) {
        mBallProperty.setBallNumber(number);
        move(0, mBallProperty.getCurrentSelected());//测量并重绘
    }

    /**
     * 设置当前选中
     *
     * @param currentSelected
     */
    public void setCurrentSelected(int currentSelected) {
        if (currentSelected < 0 || currentSelected >= mBallProperty.getBallNumber())
            return;
        mBallProperty.setCurrentSelected(currentSelected);
        mPaint.setColor(mBallProperty.getColor(currentSelected));
        move(0, mBallProperty.getCurrentSelected());//绘制一下
    }

    /**
     * 添加item的点击监听
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置小球点击监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 触摸监听
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && mOnItemClickListener != null) {//按下的时候
            int downX = (int) event.getX();
            int radius = (int) mBallProperty.getBallRadius();
            int realWidth = (int) (mViewWidth - mPaddingLeft - mPaddingRight - 2 * radius);//圆点坐标能移动的范围

            int eachLength = mBallProperty.getBallNumber() == 1 ? realWidth
                    : realWidth / (mBallProperty.getBallNumber() - 1);//每一段的长度

            for (int i = 0; i < mBallProperty.getBallNumber(); i++) {

                int start = (int) (eachLength * i + mPaddingLeft - mBallProperty.getCircleTouchPadding());//开始位置
                if (downX >= start && downX <= start + 2 * radius + 2 * mBallProperty.getCircleTouchPadding()) {
                    mOnItemClickListener.onItemClick(i);
                }
            }

        }

        return true;
    }

    /**
     * 设置颜色列表
     */
    public void setColorList(List<Integer> colorList) {
        mBallProperty.setColorList(colorList);
    }

}
