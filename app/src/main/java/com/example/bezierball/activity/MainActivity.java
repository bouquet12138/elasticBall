package com.example.bezierball.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bezierball.R;
import com.example.bezierball.adapter.MyPagerAdapter;
import com.example.bezierball.custom_view.BezierBall;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";//TAG

    private ViewPager mViewPager;
    private BezierBall mIndicatorBall;

    private MyPagerAdapter mMyPagerAdapter;
    private List<View> mViewList;
    private int lastSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mIndicatorBall = findViewById(R.id.indicatorBall);
        LayoutInflater layoutInflater = getLayoutInflater();

        mViewList = new ArrayList<>();//数组

        mViewList.add(layoutInflater.inflate(R.layout.item_view_one, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.item_view_two, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.item_view_three, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.item_view_four, null, false));

        List<Integer> colorList = new ArrayList<>();
        colorList.add(0xffffba55);
        colorList.add(0xfffff893);
        colorList.add(0xffbe9951);
        colorList.add(0xff71aeff);

        mIndicatorBall.setColorList(colorList);//设置一下颜色列表
        mIndicatorBall.setBallNumber(mViewList.size());//设置一下指示器的尺寸
        mMyPagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mMyPagerAdapter);//设置适配器
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: position " + position);
                Log.d(TAG, "onPageScrolled: positionOffset " + positionOffset);
                mIndicatorBall.move(positionOffset, position);//移动一下
            }

            @Override
            public void onPageSelected(int position) {
                mIndicatorBall.setCurrentSelected(position);
                Log.e(TAG, "onPageSelected: currentSelected " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIndicatorBall.setOnItemClickListener((position) -> {
            mViewPager.setCurrentItem(position);//设置当前选项
        });

    }

}
