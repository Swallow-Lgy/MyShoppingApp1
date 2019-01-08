package com.bawei.dell.myshoppingapp.show.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.show.fragment.CricleFragment;
import com.bawei.dell.myshoppingapp.show.fragment.HomeFragment;
import com.bawei.dell.myshoppingapp.show.fragment.IndentFragment;
import com.bawei.dell.myshoppingapp.show.fragment.MainFragment;
import com.bawei.dell.myshoppingapp.show.fragment.ShopCarFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
     private List<Fragment> mList;
     private ViewPager mViewPagerShow;
     private RadioGroup group;
      private HomeFragment homeFragment;

    @Override
    protected void initData() {

    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewPagerShow = findViewById(R.id.show_viewpager);
        group = findViewById(R.id.show_group);
       /* //页面切换
        initLayout();
        //点击按钮切换页面
        selectLayout();
        //点击页面切换按钮
        selectButton();*/
        homeFragment = new HomeFragment();
        mList = new ArrayList<>();
        mList.add(homeFragment);
        mList.add(new CricleFragment());
        mList.add(new ShopCarFragment());
        mList.add(new IndentFragment());
        mList.add(new MainFragment());
        mViewPagerShow.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }
            @Override
            public int getCount() {
                return mList.size();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.show_but_home:
                        mViewPagerShow.setCurrentItem(0);
                        break;
                    case R.id.show_but_cricle:
                        mViewPagerShow.setCurrentItem(1);
                        break;
                    case R.id.show_but_shopcar:
                        mViewPagerShow.setCurrentItem(2);
                        break;
                    case R.id.show_but_indent:
                        mViewPagerShow.setCurrentItem(3);
                        break;
                    case R.id.show_but_main:
                        mViewPagerShow.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
            }
        });
        mViewPagerShow.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.show_but_home);
                        break;
                    case 1:
                        group.check(R.id.show_but_cricle);
                        break;
                    case 2:
                        group.check(R.id.show_but_shopcar);
                        break;
                    case 3:
                        group.check(R.id.show_but_indent);
                        break;
                    case 4:
                        group.check(R.id.show_but_main);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }
   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            homeFragment.getBackData(true);
        }
        return false;
    }


}
