package com.bawei.dell.myshoppingapp.show.home.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawei.dell.myshoppingapp.show.home.bean.HomeViewpagerBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeViewPagerAdpter extends PagerAdapter {
    private List<HomeViewpagerBean.ResultBean> mList;
    private Context mContext;

    public HomeViewPagerAdpter(List<HomeViewpagerBean.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(mList.get(position%mList.size()).getImageUrl()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
