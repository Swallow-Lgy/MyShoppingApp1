package com.bawei.dell.myshoppingapp.show.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.shopcar.adpter.ShopCarGoodsAdpter;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.List;

public class ShopCarFragment extends BaseFrgment implements View.OnClickListener,IView {
    private RecyclerView shopcarrecycle;
    private ShopCarGoodsAdpter mCarGoodsAdpter;
    private IPresenterImpl iPresenter;
    private String searchShopCar="order/verify/v1/findShoppingCart";
    @Override
    protected void initData(View view) {
        initIPresenter();
        ShopCarLayout();
    }
    @Override
    public void onClick(View v) {

    }
    //记载布局
    public void ShopCarLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shopcarrecycle.setLayoutManager(linearLayoutManager);
        mCarGoodsAdpter = new ShopCarGoodsAdpter(getContext());
        shopcarrecycle.setAdapter(mCarGoodsAdpter);
        getShopCarData();
    }
    //加载数据
    public void getShopCarData(){
        iPresenter.requestDataPget(searchShopCar,ShopCarBean.class);
    }
    @Override
    public void requestDataV(Object data) {
       if (data instanceof ShopCarBean){
           ShopCarBean carBean = (ShopCarBean) data;
           List<ShopCarBean.ResultBean> result = carBean.getResult();
           mCarGoodsAdpter.setmList(result);
       }
    }

    //寻找id
    @Override
    protected void initView(View view) {
     shopcarrecycle = view.findViewById(R.id.shopcar_recycle);
    }
    @Override
    protected int getViewById() {
        return R.layout.fragment_shopcar;
    }
    //绑定
    public void initIPresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
