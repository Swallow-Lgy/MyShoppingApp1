package com.bawei.dell.myshoppingapp.show.indent.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.indent.adpter.CreatIndedntAdpter;
import com.bawei.dell.myshoppingapp.show.indent.adpter.IndentAddressAdpter;
import com.bawei.dell.myshoppingapp.show.indent.bean.ChoseAddressBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentAdressBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.SubmitIndentBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.SubmitResultBean;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 创建订单页面
 */

public class CreatIndentActivity extends BaseActivity implements IView {
         private List<ShopCarBean.ResultBean> mCreatindent;
         private RecyclerView mCreatIndentRecycle;
         private CreatIndedntAdpter indedntAdpter;
         private ImageView popAddressImage;
         private PopupWindow popupWindow;
         private  RecyclerView poprecycle;
         private IPresenterImpl iPresenter;
         private String addressUrl="user/verify/v1/receiveAddressList";
         private String sumbmitUrl="order/verify/v1/createOrder";
         private IndentAddressAdpter addressAdpter;
         private TextView name,phone,address,goodsnum,sumprice;
         private int addressid;
         private Button mSubmitindent;
         private String choseurl="user/verify/v1/setDefaultReceiveAddress";
        //加载数据
        @Override
    protected void initData()
        {
            //接受intent值
            Intent intent = getIntent();
            mCreatindent = (List<ShopCarBean.ResultBean>) intent.getSerializableExtra("creatindent");
            //绑定ipresneter
            initIpresenter();
            //加载订单布局
            initCreatIndetLayout();
            //弹框的单击事件
            initpop();
            //加载数据
            getAddressData();
            //加载地址布局
            initAddressLayout();
            //计算数量总和和价格
            totlePriceAndSum();
            //提交订单点击事件
            submitIndent();
            addressAdpter.setOnClickLisenter(new IndentAddressAdpter.onClick() {
                @Override
                public void onClickLisenter(int id) {
                     Map<String,String> map = new HashMap<>();
                     map.put("id",id+"");
                     iPresenter.requestDataPpost(choseurl,map,ChoseAddressBean.class);
                }
            });
         }
        //加载订单布局
    public void initCreatIndetLayout()
    {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mCreatIndentRecycle.setLayoutManager(linearLayoutManager);
            indedntAdpter = new CreatIndedntAdpter(mCreatindent, this);
            mCreatIndentRecycle.setAdapter(indedntAdpter);
    }
    //加载地址布局
     public void  initAddressLayout()
     {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            poprecycle.setLayoutManager(linearLayoutManager);
            addressAdpter = new IndentAddressAdpter(this);
            poprecycle.setAdapter(addressAdpter);
     }
     //计算数量总和和价格
    public void totlePriceAndSum(){
          int sum=0;
          double price=0;
          for (int i=0;i<mCreatindent.size();i++) {
              sum+=mCreatindent.get(i).getCount();
              price+=(mCreatindent.get(i).getCount()*mCreatindent.get(i).getPrice());
          }
          goodsnum.setText(sum+"");
          sumprice.setText(price+"");
    }
        //提交订单点击事件
    public void submitIndent(){
            mSubmitindent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<SubmitIndentBean> submitlist = new ArrayList<>();
                    for (int i=0;i<mCreatindent.size();i++){
                        SubmitIndentBean indentBean = new SubmitIndentBean();
                        indentBean.setCommodityId(mCreatindent.get(i).getCommodityId());
                        indentBean.setAmount(mCreatindent.get(i).getCount());
                        submitlist.add(indentBean);
                    }
                    String orderInfo = new Gson().toJson(submitlist);
                    String totalPrice = sumprice.getText().toString();
                    submitIndentData(orderInfo,totalPrice,addressid);
                }
            });
    }
        //弹框点击事件
    public void initpop(){
        //自定义布局
           final View view = View.inflate(this,R.layout.pop_address,null);
           poprecycle = view.findViewById(R.id.pop_recycle);
            // 创建PopupWindow对象，其中：
            // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
            // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
            popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT,true);
            //获取焦点
           popupWindow.setFocusable(true);
            //定义颜色颜色
           ColorDrawable cdw = new ColorDrawable(getResources().getColor(R.color.popcolor));
            //设置颜色
           popupWindow.setBackgroundDrawable(cdw);
           popAddressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v,0,1);
                popAddressImage.setSelected(!popAddressImage.isSelected());
              }
            });
            popupWindow.dismiss();
        }
        //查询收货地址
     public void getAddressData(){
         iPresenter.requestDataPget(addressUrl,IndentAdressBean.class);
      }
      //提交订单请求网络
     public void submitIndentData(String orderInfo, String totalPrice,int addressid){
         Map<String,String> map = new HashMap<>();
         map.put("orderInfo",orderInfo);
         map.put("totalPrice",totalPrice);
         map.put("addressId",addressid+"");
         iPresenter.requestDataPpost(sumbmitUrl,map,SubmitResultBean.class);
      }
        //网络请求
        @Override
    public void requestDataV(Object data) {
            //添加地址
           if (data instanceof IndentAdressBean){
              IndentAdressBean adressBean = (IndentAdressBean) data;
               List<IndentAdressBean.ResultBean> result = adressBean.getResult();
              for (int i=0;i<result.size();i++){
                  if (result.get(i).getWhetherDefault()==1){
                     name.setText(result.get(i).getRealName());
                     phone.setText(result.get(i).getPhone());
                     address.setText(result.get(i).getAddress());
                      addressid = result.get(i).getId();
                  }
              }
               addressAdpter.setmList(result);
           }
           //提交订单得到结果
           if (data instanceof SubmitResultBean){
               SubmitResultBean resultBean = (SubmitResultBean) data;
               if (resultBean.getStatus().equals("0000")){
                   Toast.makeText(this,resultBean.getMessage(),Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(this,resultBean.getMessage(),Toast.LENGTH_SHORT).show();
               }
           }
           if (data instanceof ChoseAddressBean)
           {
               ChoseAddressBean addressBean = (ChoseAddressBean) data;
               if (addressBean.getStatus().equals("0000")){
                   Toast.makeText(this,addressBean.getMessage(),Toast.LENGTH_SHORT).show();
               }
               getAddressData();
           }
        }
        //获取id
        @Override
    protected void initView(Bundle savedInstanceState) {
             mCreatIndentRecycle = findViewById(R.id.creat_indet_recycle);
             popAddressImage = findViewById(R.id.pop_show_address);
             name = findViewById(R.id.indent_name);
             address = findViewById(R.id.indent_address);
             phone = findViewById(R.id.indent_phone);
             sumprice = findViewById(R.id.creat_indent_sumprice);
             goodsnum = findViewById(R.id.creat_indent_sum);
             mSubmitindent = findViewById(R.id.creat_submit_indent);
        }
        //加载布局
        @Override
      protected int getViewId() {
            return R.layout.activity_creat_indent;
        }
        //绑定
       public void initIpresenter(){
           iPresenter = new IPresenterImpl(this);
       }
        //解绑
    @Override
      protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
