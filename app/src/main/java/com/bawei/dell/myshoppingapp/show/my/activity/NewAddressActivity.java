package com.bawei.dell.myshoppingapp.show.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenter;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.my.bean.NewAddressBean;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.HashMap;
import java.util.Map;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
public class NewAddressActivity extends BaseActivity implements CityPickerListener ,View.OnClickListener,IView {
    private  CityPicker cityPicker;
    private EditText newname,newphone,newdetaile,newcode;
    private TextView newcity;
    private Button saveadd;
    private String addUrl="user/verify/v1/addReceiveAddress";
    private IPresenterImpl iPresenter;
    //逻辑处理
    @Override
    protected void initData() {
      cityPicker = new CityPicker(NewAddressActivity.this,this);
      //邦定
        initIpresenter();
    }
    //点击事件
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.new_address_address:
                 cityPicker.show();
                 break;
             case R.id.new_addsave_address:
                 String city = newcity.getText().toString();
                 String name = newname.getText().toString();
                 String detaile = newdetaile.getText().toString();
                 String address = city+" "+detaile;
                 String code = newcode.getText().toString();
                 String phone = newphone.getText().toString();
                 getAddData(name,phone,address,code);
             default:
                     break;
         }
    }
   //添加地址请求网络
    public void  getAddData(String realName,String phone,String address,String zipCode ){
        Map<String,String> map = new HashMap<>();
        map.put("realName",realName);
        map.put("phone",phone);
        map.put("address",address);
        map.put("zipCode",zipCode);
        iPresenter.requestDataPpost(addUrl,map,NewAddressBean.class);
    }
    //网络请求成功数据
    @Override
    public void requestDataV(Object data) {
        if (data instanceof NewAddressBean){
            NewAddressBean addressBean = (NewAddressBean) data;
            if (addressBean.getStatus().equals("0000")){
                Toast.makeText(this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
     //获取资源id
    @Override
    protected void initView(Bundle savedInstanceState) {
         newcity = findViewById(R.id.new_address_address);
         newcode = findViewById(R.id.new_address_code);
         newdetaile = findViewById(R.id.new_address_datiaddress);
         newname = findViewById(R.id.new_address_name);
         newphone = findViewById(R.id.new_address_phone);
         saveadd = findViewById(R.id.new_addsave_address);
         saveadd.setOnClickListener(this);
         newcity.setOnClickListener(this);
    }
    //地区选择器
    @Override
    public void getCity(String s) {
           newcity.setText(s);
    }
    //加载布局
    @Override
    protected int getViewId() {
        return R.layout.activity_new_address;
    }
    //绑定
    public void initIpresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
