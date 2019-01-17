package com.bawei.dell.myshoppingapp.show.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.home.activity.GoodsDetailedActivity;
import com.bawei.dell.myshoppingapp.show.my.bean.UpdateAddressBean;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.HashMap;
import java.util.Map;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class UpdataActivity extends BaseActivity implements View.OnClickListener,IView,CityPickerListener {
    private CityPicker cityPicker;
    private String name,phone,address,zipCode;
    private EditText updatename,updatephone,updatedetaile,updatecode;
    private TextView updatecity;
    private Button update;
    private String[] split;
    private String update_address_url="user/verify/v1/changeReceiveAddress";
    private int id;
     private IPresenterImpl iPresenter;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
         zipCode = intent.getStringExtra("zpicode");
         id = intent.getIntExtra("id", 0);
        split = address.split("\\ ");
        //绑定
        initPresneter();
        //设置修改前的数据
          setUpDataBefore();
        cityPicker = new CityPicker(UpdataActivity.this,this);
    }
    //设置修改前的数据
    public void setUpDataBefore(){
          updatephone.setText(phone);
          updatename.setText(name);
          String address = split[0]+" "+split[1]+" "+split[2];
          updatedetaile.setText(split[3]);
          updatecity.setText(address);
          updatecode.setText(zipCode);
    }
    //修改
    public void  setUpdateAfter()
    {
        String newcode = updatecode.getText().toString();
        String newdatecity = updatedetaile.getText().toString();
        String newcity = updatecity.getText().toString();
        String newname = updatename.getText().toString();
        String newphone = updatephone.getText().toString();
       String address =  newcity+" "+newdatecity;
        if (newcity!=null&&newphone!=null&&newcode!=null&&newdatecity!=null&&newname!=null)
        {
            Map<String, String> map = new HashMap<>();
            map.put("id", id+"");
            map.put("realName", newname);
            map.put("phone", newphone);
            map.put("address", address);
            map.put("zipCode", newcode);
            iPresenter.requestDataPput(update_address_url,map,UpdateAddressBean.class);
        }
        else
        {
            Toast.makeText(UpdataActivity.this, "内容不能为空",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.update_address_address:
                   cityPicker.show();
                   break;
               case R.id.update_update_address:
                   setUpdateAfter();
                   break;
                   default:
                       break;
           }
    }

    @Override
    public void requestDataV(Object data)
    {
        if (data instanceof UpdateAddressBean){
            UpdateAddressBean addressBean = (UpdateAddressBean) data;
            if (addressBean.getStatus().equals("0000"))
            {
                Toast.makeText(UpdataActivity.this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdataActivity.this,MyAddressActivity.class);
                startActivity(intent);
            }
        }
    }
    
    @Override
    protected void initView(Bundle savedInstanceState) {
          updatecity  = findViewById(R.id.update_address_address);
          updatecode = findViewById(R.id.update_address_code);
          updatedetaile = findViewById(R.id.update_address_datiaddress);
          updatename = findViewById(R.id.update_address_name);
          updatephone  = findViewById(R.id.update_address_phone);
          update = findViewById(R.id.update_update_address);
          update.setOnClickListener(this);
          updatecity.setOnClickListener(this);
    }
    //地区选择器
    @Override
    public void getCity(String s) {
        updatecity.setText(s);
    }
    //加载页面
    @Override
    protected int getViewId() {
        return R.layout.activity_updata;
    }
    //绑定
    public void initPresneter(){
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
