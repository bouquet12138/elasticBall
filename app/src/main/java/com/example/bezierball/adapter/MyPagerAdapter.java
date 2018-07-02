package com.example.bezierball.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViewLists;

    /**
     * 初始化
     *
     * @param viewLists
     */
    public MyPagerAdapter(List<View> viewLists) {
        mViewLists = viewLists;
    }

    @Override
    public int getCount() {
        return mViewLists == null ? 0 : mViewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewLists.get(position));
        return mViewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewLists.get(position));
    }
}
