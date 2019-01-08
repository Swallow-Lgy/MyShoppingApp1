package com.bawei.dell.myshoppingapp.show.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.loaginreg.activity.LoginActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.cricle.adpter.CricleListAdpter;
import com.bawei.dell.myshoppingapp.show.cricle.bean.CricleListBean;
import com.bawei.dell.myshoppingapp.show.cricle.bean.HomeGreatBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CricleFragment extends BaseFrgment implements IView {
     private XRecyclerView cricleXRecyclerView;
     private CricleListAdpter mCricleListAdpter;
     private String mCricleUrl="circle/v1/findCircleList?userId=774&sessionId=15231827283&page=%d&count=5";
     private int page;
     private IPresenterImpl mIPresenterImpl;
     private String mCricleGreat="circle/verify/v1/addCircleGreat";
     private String mCricleUrlNoGreat="circle/verify/v1/cancelCircleGreat";
     //加载页面布局
     @Override
    protected int getViewById() {
        return R.layout.fragment_cricle;
    }
   //处理数据
    @Override
    protected void initData(View view) {
        initLayout();
    }
    public void initLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cricleXRecyclerView.setLayoutManager(linearLayoutManager);
        cricleXRecyclerView.setAdapter(mCricleListAdpter);
        cricleXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
                cricleXRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                getData();
                cricleXRecyclerView.loadMoreComplete();
            }
        });
        page=1;
        getData();
        //点赞
        mCricleListAdpter.setOClickGreatLister(new CricleListAdpter.onClickGreat() {
            @Override
            public void onClickGreatLisnter(boolean b, int i) {
                if (b){
                    getNoGreatData(i);
                }
                else {
                    getGreatData(i);
                }
            }
        });
    }
    //请求数据
   public void getData(){
       mIPresenterImpl.requestDataPget(String.format(mCricleUrl,page),CricleListBean.class);
   }
   //点赞请求数据
    public void getGreatData(int id){
        HashMap<String,String> map = new HashMap<>();
        map.put("circleId",id+"");
         mIPresenterImpl.requestDataPpost(mCricleGreat,map,HomeGreatBean.class);
    }
    //取消点赞请求
    public void getNoGreatData(int id){
       mIPresenterImpl.requestDataPdelelte(String.format(mCricleUrlNoGreat,id),HomeGreatBean.class);
    }
    @Override
    protected void initView(View view) {
        cricleXRecyclerView  = view.findViewById(R.id.cricle_recycler);
        mCricleListAdpter = new CricleListAdpter(getActivity());
        mIPresenterImpl = new IPresenterImpl(this);
    }
   //成功返回的数据
    @Override
    public void requestDataV(Object data) {
         if (data instanceof CricleListBean){
             CricleListBean cricleListBean = (CricleListBean) data;
             List<CricleListBean.ResultBean> result = cricleListBean.getResult();
             if (page==1){
                 mCricleListAdpter.setmList(result);
             }
             else {
                 mCricleListAdpter.addmList(result);
             }
             page++;
         }
         if (data instanceof HomeGreatBean){
             HomeGreatBean greatBean = (HomeGreatBean) data;
             if (greatBean.getMessage().equals("请先登陆")){
                 Intent intent = new Intent(getActivity(),LoginActivity.class);
                 getActivity().startActivity(intent);

             }
             else {
                 Toast.makeText(getActivity(),greatBean.getMessage(),Toast.LENGTH_SHORT).show();
                   initLayout();
             }
         }
    }
}
