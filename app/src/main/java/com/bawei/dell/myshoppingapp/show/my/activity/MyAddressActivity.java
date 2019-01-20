package com.bawei.dell.myshoppingapp.show.my.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentAdressBean;
import com.bawei.dell.myshoppingapp.show.my.adpter.MyAddressAdpter;
import com.bawei.dell.myshoppingapp.show.my.bean.MyAddressBean;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.List;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener,IView {
    private String myAddressUrl="user/verify/v1/receiveAddressList";
    private IPresenterImpl iPresenter;
    private RecyclerView myAddressRecycle;
    private MyAddressAdpter addressAdpter;
    private Button addAddress;
    private List<MyAddressBean.ResultBean> result;
    //加载数据
    @Override
    protected void initData() {
       //展示我的地址
        initAddressLayout();
        addressAdpter.setMupDateOnclick(new MyAddressAdpter.upDateOnclick() {
            @Override
            public void upDateOnClickListener(int i,int id) {
                Intent intent = new Intent(MyAddressActivity.this,UpdataActivity.class);
                intent.putExtra("name",result.get(i).getRealName());
                intent.putExtra("phone",result.get(i).getPhone());
                intent.putExtra("address",result.get(i).getAddress());
                 intent.putExtra("zpicode", result.get(i).getZipCode());
                 intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
   //我的地址请求网络数据
    public void getMyAddressData(){
        iPresenter.requestDataPget(myAddressUrl,MyAddressBean.class);
    }
    //加载布局
    public void initAddressLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        addressAdpter = new MyAddressAdpter(this);
        myAddressRecycle.setLayoutManager(linearLayoutManager);
        myAddressRecycle.setAdapter(addressAdpter);
        getMyAddressData();
    }
   //设置点击事件
    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.my_add_address:
                  Intent intent = new Intent(this,NewAddressActivity.class);
                  startActivity(intent);
                  break;
                  default:
                      break;
          }
    }
    //网络请求返回数据
    @Override
    public void requestDataV(Object data) {
       if (data instanceof MyAddressBean){
           MyAddressBean addressBean = (MyAddressBean) data;
           result = addressBean.getResult();
           if (result.size()==0){
               return;
           }
           addressAdpter.setmList(result);
       }
    }
    //寻找资源ID
    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定
        initPresenter();
        myAddressRecycle = findViewById(R.id.my_address_recycle);
        addAddress = findViewById(R.id.my_add_address);
        addAddress.setOnClickListener(this);
    }
    //加载页面布局
    @Override
    protected int getViewId() {
        return R.layout.activity_my_address;
    }
    //绑定
    public void initPresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
