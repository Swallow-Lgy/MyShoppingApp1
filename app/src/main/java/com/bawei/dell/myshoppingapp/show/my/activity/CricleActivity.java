package com.bawei.dell.myshoppingapp.show.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.my.adpter.MyCricleAdpter;
import com.bawei.dell.myshoppingapp.show.my.bean.MyCriCleBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class CricleActivity extends BaseActivity implements View.OnClickListener,IView {
     private XRecyclerView myCricleRecycle;
     private IPresenterImpl iPresenter;
     private String searchMyCricle="circle/verify/v1/findMyCircleById?page=%d&count=%d";
     private int page=1;
     private MyCricleAdpter cricleAdpter;
    @Override
    protected void initData() {
        //绑定
        initIpresenter();
        //加载数据
        getMyCriCleLayout();
    }
    //点击事件
    @Override
    public void onClick(View v)
    {

    }
    //加载布局
    public void getMyCriCleLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cricleAdpter = new MyCricleAdpter(this);
        myCricleRecycle.setLayoutManager(linearLayoutManager);
        myCricleRecycle.setAdapter(cricleAdpter);
        page=1;
        myCricleRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh()
            {
                page=1;
                getMyCricleData();
                myCricleRecycle.refreshComplete();
            }
            @Override
            public void onLoadMore()
            {
                 getMyCricleData();
                 myCricleRecycle.loadMoreComplete();
            }
        });
        getMyCricleData();
    }
    //加载数据
    public void getMyCricleData(){
           iPresenter.requestDataPget(String.format(searchMyCricle,page,5),MyCriCleBean.class);
    }
    @Override
    public void requestDataV(Object data) {
           if (data instanceof MyCriCleBean){
               MyCriCleBean criCleBean = (MyCriCleBean) data;
               List<MyCriCleBean.ResultBean> result = criCleBean.getResult();
               if (page==1){
                   cricleAdpter.setmList(result);
               }
               else {
                   cricleAdpter.addmList(result);
               }
               page++;
           }
    }
    //加载页面
    @Override
    protected void initView(Bundle savedInstanceState) {
          myCricleRecycle = findViewById(R.id.my_cricle_recycle);
    }
    @Override
    protected int getViewId() {
        return R.layout.activity_cricle;
    }
   //绑定
    public void initIpresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    //销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
