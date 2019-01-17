package com.bawei.dell.myshoppingapp.show.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.my.adpter.MineFootAdapter;
import com.bawei.dell.myshoppingapp.show.my.bean.FootmarkBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class FootmarkActivity extends BaseActivity implements View.OnClickListener,IView {

   private IPresenterImpl iPresenter;
   private XRecyclerView xRecyclerView;
   private String url="commodity/verify/v1/browseList?page=%d&count=%d";
   private MineFootAdapter footAdapter;
   private int page;
    @Override
    protected void initData() {
       //初始化ipresenter
        initIprsenter();
        //加载布局
        initLayout();
    }
   //加载布局
    public void initLayout(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        xRecyclerView.setLayoutManager(gridLayoutManager);
        footAdapter = new MineFootAdapter(this);
        xRecyclerView.setAdapter(footAdapter);
        page=1;
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                  page=1;
                  getData();
                  xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                getData();
                xRecyclerView.loadMoreComplete();
            }
        });
        getData();
    }
    @Override
    public void onClick(View v) {

    }
    //请求数据
    public void getData(){
        iPresenter.requestDataPget(String.format(url,page,10),FootmarkBean.class);
    }
    @Override
    public void requestDataV(Object data) {
           if (data instanceof FootmarkBean){
               FootmarkBean footmarkBean = (FootmarkBean) data;
               List<FootmarkBean.ResultBean> result = footmarkBean.getResult();

               if (page==1){
                   footAdapter.setList(result);
               }
               else {
                   footAdapter.addList(result);
               }
               page++;
           }
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
         xRecyclerView = findViewById(R.id.fiitmark_recycle);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_footmark;
    }
    public void initIprsenter(){
        iPresenter = new IPresenterImpl(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
