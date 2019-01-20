package com.bawei.dell.myshoppingapp.show.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.app.MyApp;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.indent.adpter.IndentAdpter;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentDelBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.JudegBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.PayBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndentFragment extends BaseFrgment implements View.OnClickListener,IView {
    private IPresenterImpl iPresenter;
    private Button allbut,obligationbut,waitbut,judgebut,completedbut;
    private String indentsearhurl="order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=%d";
    private String payUrl="order/verify/v1/pay";
    private String delUrl="order/verify/v1/deleteOrder?orderId=%s";
    private String judegUrl="order/verify/v1/confirmReceipt";
    int page;
    private XRecyclerView indentXRecycle;
    private IndentAdpter mIndentAdpter;
    int mType =0;
    int count =5;
    @Override
    protected int getViewById() {
        return R.layout.fragment_indent;
    }
    @Override
    protected void initData(View view) {
        mIndentAdpter = new IndentAdpter(getContext());

        initLayout();
        //去支付的点击事件
        goPayOnClick();
        //点击确认收货的事件
        getJudegOnClick();
        //取消订单的点击事件
        delOnClick();
    }
    //去支付的点击事件
    public void goPayOnClick(){
        mIndentAdpter.setOnPayClcikLisenter(new IndentAdpter.onPayClcik() {
            @Override
            public void onPayClickLisenter(String id, int type) {
                getPayData(id);
            }
        });
    }
    //取消订单的点击事件
    public void delOnClick(){
        mIndentAdpter.setOnDelClcikLisenter(new IndentAdpter.onDelClcik() {
            @Override
            public void onDelClickLisenter(String id,int i) {
              getDelData(id);
              mIndentAdpter.delIndent(i);
            }
        });
    }
   //点击确认收货的事件
    public void getJudegOnClick(){
        mIndentAdpter.setOnJudegClcikLisenter(new IndentAdpter.onJudegClcik() {
            @Override
            public void onJudegClickLisenter(String id, int type) {
                getJudegData(id);
            }
        });
    }
   //加载布局
    public void initLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        indentXRecycle.setLayoutManager(linearLayoutManager);
        indentXRecycle.setAdapter(mIndentAdpter);
        indentXRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData(mType,page,count);
                indentXRecycle.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                 getData(mType,page,count);
                 indentXRecycle.loadMoreComplete();
            }
        });
        getData(mType,page,count);
    }
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.indet_but_all:
                 Toast.makeText(getActivity(),"全部订单",Toast.LENGTH_SHORT).show();
                 mType=0;
                 page=1;
                 count=5;
                 getData(mType,page,count);
                 break;
             case R.id.indet_but_obligation:
                 Toast.makeText(getActivity(),"待付款",Toast.LENGTH_SHORT).show();
                mType=1;
                page=1;
                count=5;
                getData(mType,page,count);
                break;
             case R.id.indet_but_wait:
                 Toast.makeText(getActivity(),"待收货",Toast.LENGTH_SHORT).show();
                 mType=2;
                 page=1;
                 count=5;
                 getData(mType,page,count);
               break;
             case R.id.indet_but_judge:
                 Toast.makeText(getActivity(),"待评价",Toast.LENGTH_SHORT).show();
                 mType=3;
                 page=1;
                 count=5;
                 getData(mType,page,count);
                 break;
             case R.id.indet_but_completed:
                 Toast.makeText(getActivity(),"已完成",Toast.LENGTH_SHORT).show();
                 mType=9;
                 page=1;
                 count=5;
                 getData(mType,page,count);
                 break;
             default:
                 break;
         }
    }
    //删除订单取消网络
    public void getDelData(String id){
        iPresenter.requestDataPdelelte(String.format(delUrl,id),IndentDelBean.class);
    }
   //请求网络
    public void getData(int status,int page,int count){
         iPresenter.requestDataPget(String.format(indentsearhurl,status,page,count),IndentBean.class);
    }
    //去支付请求网络数据
    public void getPayData(String id){
        Map<String,String> map=new HashMap<>();
        map.put("orderId",id);
        map.put("payType",1+"");
        iPresenter.requestDataPpost(payUrl,map,PayBean.class);
    }
    //确认收货网络请求
    public void  getJudegData(String id){
        Map<String,String> map = new HashMap<>();
        map.put("orderId",id);
        iPresenter.requestDataPput(judegUrl,map,JudegBean.class);
    }
    @Override
    public void requestDataV(Object data) {
        if (data instanceof IndentBean){
            IndentBean indentBean = (IndentBean) data;
            List<IndentBean.OrderListBean> orderList = indentBean.getOrderList();
            if (page==1){
                mIndentAdpter.setmList(orderList);
            }
            else {
                mIndentAdpter.addmList(orderList);
            }
            page++;
        }
        if (data instanceof PayBean){
            PayBean payBean = (PayBean) data;
            if (payBean.getStatus().equals("0000")){
                Toast.makeText(getActivity(),payBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),payBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof JudegBean){
            JudegBean judegBean = (JudegBean) data;
            if (judegBean.getStatus().equals("0000")){
                Toast.makeText(getActivity(),judegBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),judegBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof IndentDelBean){
            IndentDelBean delBean = (IndentDelBean) data;
            if (delBean.getStatus().equals("0000")){
                Toast.makeText(getActivity(),delBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),delBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //获取资源ID
    @Override
    protected void initView(View view) {
        //绑定ipresenter
        initIpresenter();
       allbut = view.findViewById(R.id.indet_but_all);
       completedbut = view.findViewById(R.id.indet_but_completed);
       obligationbut = view.findViewById(R.id.indet_but_obligation);
        judgebut = view.findViewById(R.id.indet_but_judge);
       waitbut = view.findViewById(R.id.indet_but_wait);
       indentXRecycle = view.findViewById(R.id.intent_xrecycle);
       allbut.setOnClickListener(this);
       completedbut.setOnClickListener(this);
       obligationbut.setOnClickListener(this);
       judgebut.setOnClickListener(this);
       waitbut.setOnClickListener(this);
    }
    //绑定ipresenter
    public void initIpresenter(){
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
