package com.bawei.dell.myshoppingapp.show.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.my.adpter.WalletAdpter;
import com.bawei.dell.myshoppingapp.show.my.bean.WalletBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class WalletActivity extends BaseActivity implements View.OnClickListener,IView {

    private IPresenterImpl iPresenter;
    private TextView moeny;
    private XRecyclerView walletRecycle;
    private String walleturl="user/verify/v1/findUserWallet?page=%d&count=%d";
    private WalletAdpter walletAdpter;
    int page;
    @Override
    protected void initData() {
        //绑定
        initPresenter();
        //加载布局
        initLayout();
    }
    //加载布局
    public void initLayout(){
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        walletAdpter = new WalletAdpter(this);
        walletRecycle.setAdapter(walletAdpter);
        walletRecycle.setLayoutManager(linearLayoutManager);
        walletRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getWalletData();
                walletRecycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                 getWalletData();
                 walletRecycle.loadMoreComplete();
            }
        });
        getWalletData();
    }
    @Override
    public void onClick(View v) {

    }
    //获取数据
    public void  getWalletData(){
        iPresenter.requestDataPget(String.format(walleturl,page,10),WalletBean.class);
    }
    @Override
    public void requestDataV(Object data) {
           if (data instanceof WalletBean){
               WalletBean walletBean = (WalletBean) data;
               WalletBean.ResultBean result = walletBean.getResult();
               List<WalletBean.ResultBean.DetailListBean> detailList = result.getDetailList();

               moeny.setText(result.getBalance()+"");
               if (page==1){
                   walletAdpter.setmList(detailList);
               }
               else {
                   walletAdpter.addmList(detailList);
               }
               page++;
           }
    }
    //获取资源id
    @Override
    protected void initView(Bundle savedInstanceState) {
           moeny = findViewById(R.id.moeny);
           walletRecycle = findViewById(R.id.wallet_recycle);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_wallet;
    }
    //绑定
    public  void initPresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    //销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
