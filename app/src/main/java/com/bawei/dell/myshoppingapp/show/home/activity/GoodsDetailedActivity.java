
package com.bawei.dell.myshoppingapp.show.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.home.bean.AddShopCarBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeDetailedBean;
import com.bawei.dell.myshoppingapp.show.home.bean.SearchAddBean;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Templates;

public class GoodsDetailedActivity extends BaseActivity implements View.OnClickListener,IView {
     private int ids;
     private IPresenterImpl mIPresenterImpl;
     private String detailterUrl="commodity/v1/findCommodityDetailsById?commodityId=%d";
     private TextView price,name,sales,weigth;
     private WebView detailedweb;
     private FlyBanner goodsBeaner;
     private Button addShopCar,buy;
     public String addUrl="order/verify/v1/syncShoppingCart";
     public String searchUrl="order/verify/v1/findShoppingCart";
    @Override
    protected void initData() {
        Intent intent = getIntent();
        ids = intent.getIntExtra("id", 0);
        initIpresenter();
        getDetailedData();

    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.add_shopcar:
                  searchShopCar();
                  break;
                  default:
                      break;
          }
    }
    //查询购物车
    public void searchShopCar(){
        mIPresenterImpl.requestDataPget(searchUrl,ShopCarBean.class);
    }
    //加入购物车请求数据
    public void getAddCarData(List<SearchAddBean> mList){
        String string = "[";
        for (int i=0;i<mList.size();i++){
            if (Integer.valueOf(ids)==mList.get(i).getCommodityId()){
                int count = mList.get(i).getCount();
                count++;
                mList.get(i).setCount(count);
                break;
            }
            else  if (i==mList.size()-1){
                mList.add(new SearchAddBean(Integer.valueOf(ids),1));
                break;
            }
            for (SearchAddBean searchAddBean :mList){
                string+="{\"commodityId\":"+searchAddBean.getCommodityId()+",\"count\":"+searchAddBean.getCount()+"},";
            }
            String substring = string.substring(0, string.length() - 1);
             substring+="]";
             Map<String,String> map = new HashMap<>();
             map.put("data",substring);
             mIPresenterImpl.requestDataPput(addUrl,map,AddShopCarBean.class);
        }
        Map<String,String> map = new HashMap<>();
        map.put("data","[{\"commodityId\":"+ids+",\"count\":1}]");
        mIPresenterImpl.requestDataPput(addUrl,map,AddShopCarBean.class);
    }
    //详情请求数据
    public void  getDetailedData(){
        mIPresenterImpl.requestDataPget(String.format(detailterUrl,ids),HomeDetailedBean.class);
    }
    @Override
    public void requestDataV(Object data) {
        //详情展示
       if (data instanceof HomeDetailedBean){
           HomeDetailedBean detailedBean = (HomeDetailedBean) data;
           String picture = detailedBean.getResult().getPicture();
           String[] split = picture.split(",");
           List<String> list = new ArrayList<>();
           for (int i=0;i<split.length;i++){
               list.add(split[i]);
           }
           goodsBeaner.setImagesUrl(list);
           name.setText(detailedBean.getResult().getCommodityName());
           price.setText("￥"+detailedBean.getResult().getPrice()+"");
           weigth.setText(detailedBean.getResult().getWeight()+"");
           sales.setText("已销售"+detailedBean.getResult().getSaleNum()+"件");
           detailedweb.loadDataWithBaseURL(null,detailedBean.getResult().getDetails(),"text/html","utf-8",null);
       }
       //加入购物车
        if(data instanceof AddShopCarBean){
           AddShopCarBean carBean = (AddShopCarBean) data;
           if (carBean.getStatus().equals("0000")){
               Toast.makeText(GoodsDetailedActivity.this,carBean.getMessage(),Toast.LENGTH_SHORT).show();
           }
        }
        //查询购物车
        if (data instanceof ShopCarBean){
           ShopCarBean carBean = (ShopCarBean) data;
            List<ShopCarBean.ResultBean> result = carBean.getResult();
            List<SearchAddBean> list = new ArrayList<>();
            for (int i=0;i<result.size();i++){
                list.add(new SearchAddBean(result.get(i).getCommodityId(),result.get(i).getCount()));
            }
            getAddCarData(list);
        }
    }
    //获取资源ID
    @Override
    protected void initView(Bundle savedInstanceState) {
           price = findViewById(R.id.detailed_price);
           name = findViewById(R.id.detailed_name);
           weigth = findViewById(R.id.detailed_weigth);
           sales = findViewById(R.id.detailed_sales);
           detailedweb = findViewById(R.id.detailed_goods_detailed);
           goodsBeaner = findViewById(R.id.detailed_banner);
           addShopCar  = findViewById(R.id.add_shopcar);
           buy = findViewById(R.id.detailed_buy);
           addShopCar.setOnClickListener(this);
           buy.setOnClickListener(this);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_goods_detailed;
    }
    //绑定
    public void initIpresenter(){
        mIPresenterImpl = new IPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenterImpl.destory();
    }
}
