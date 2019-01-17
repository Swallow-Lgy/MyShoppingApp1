package com.bawei.dell.myshoppingapp.show.fragment;



import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.app.MyApp;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.home.bean.AddShopCarBean;
import com.bawei.dell.myshoppingapp.show.home.bean.SearchAddBean;
import com.bawei.dell.myshoppingapp.show.indent.activity.CreatIndentActivity;
import com.bawei.dell.myshoppingapp.show.shopcar.adpter.ShopCarGoodsAdpter;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.squareup.leakcanary.RefWatcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCarFragment extends BaseFrgment implements View.OnClickListener,IView {
    private RecyclerView shopcarrecycle;
    private ShopCarGoodsAdpter mCarGoodsAdpter;
    private IPresenterImpl iPresenter;
    private String searchShopCar="order/verify/v1/findShoppingCart";
    private String addShopCar="order/verify/v1/syncShoppingCart";
    private TextView totalprice;
    private CheckBox selectAll;
    private  List<ShopCarBean.ResultBean> result = new ArrayList<>();
    private Button goSettel;

    @Override
    protected void initData(View view) {
        mCarGoodsAdpter = new ShopCarGoodsAdpter(getContext());
        initIPresenter();
        ShopCarLayout();
        //商品价格进行计算总和
        mCarGoodsAdpter.setOnLisenter(new ShopCarGoodsAdpter.shopCarAddLisenter() {
            @Override
            public void shopOnCarLisenter(List<ShopCarBean.ResultBean> list) {
                double priceSum=0;
                int goodssum=0;
                int num=0;
                 for (int i=0;i<list.size();i++){
                     num=num+list.get(i).getCount();
                    if (list.get(i).getIscheck()){
                        //计算
                        priceSum+=(list.get(i).getPrice()*list.get(i).getCount());
                        goodssum+=list.get(i).getCount();
                    }
                 }
                 //设置价格
                totalprice.setText(priceSum+"");
                 //全选框是否选中
                 if (goodssum<num){
                     selectAll.setChecked(false);
                 }
                 else {
                     selectAll.setChecked(true);
                 }
                if (list.size()==0){
                    selectAll.setChecked(false);
                }
            }

        });
        //删除点击事件
        mCarGoodsAdpter.setOnDeleteLisenter(new ShopCarGoodsAdpter.DeleteLisenter() {
            @Override
            public void shopOnCarcLisenter(List<ShopCarBean.ResultBean> list, int position) {
                double priceSum=0;
                int goodssum=0;
                int num=0;
                for (int i=0;i<list.size();i++){
                    num=num+list.get(i).getCount();
                    if (list.get(i).getIscheck()){
                        //计算
                        priceSum+=(list.get(i).getPrice()*list.get(i).getCount());
                        goodssum+=list.get(i).getCount();
                    }
                }
                //设置价格
                totalprice.setText(priceSum+"");
                //全选框是否选中
                if (goodssum<num){
                    selectAll.setChecked(false);
                }
                else {
                    selectAll.setChecked(true);
                }

                List<SearchAddBean> addlist=new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    int commodityId = list.get(i).getCommodityId();
                    int count = list.get(i).getCount();
                    addlist.add(new SearchAddBean(Integer.valueOf(commodityId),count));
                }
                String data="[";
                for (SearchAddBean bean : addlist){
                    data+="{\"commodityId\":"+bean.getCommodityId()+",\"count\":"+bean.getCount()+"},";
                }
                String substring = data.substring(0, data.length() - 1);
                substring+="]";
                Map<String,String> params = new HashMap<>();
                params.put("data",substring);

                iPresenter.requestDataPput(addShopCar,params,AddShopCarBean.class);
                if (list.size()==0){
                    selectAll.setChecked(false);
                }
            }


        });
    }
    @Override
    public void onClick(View v) {
           switch (v.getId()){
               //全选框的单击事件
               case R.id.shopcar_select_all:
               selectIscheck(selectAll.isChecked());
               mCarGoodsAdpter.notifyDataSetChanged();
               break;
               //结算按钮的点击事件 跳转页面
               case R.id.gosettel:
                   String str = totalprice.getText().toString();
                   if (str.equals("0.0")){
                        break;
                   }
                   List<ShopCarBean.ResultBean> indetlist = new ArrayList<>();
                   for (int i=0;i<result.size();i++ ){
                       if (result.get(i).getIscheck()){
                           indetlist.add(new ShopCarBean.ResultBean(result.get(i).getCommodityId(),
                                   result.get(i).getCommodityName(),
                                   result.get(i).getPic(),
                                   result.get(i).getPrice(),
                                   result.get(i).getCount(),
                                   result.get(i).getIscheck()
                                   ));
                       }
                   }
                   Intent intent = new Intent(getActivity(),CreatIndentActivity.class);
                   intent.putExtra("creatindent", (Serializable) indetlist);
                   startActivity(intent);
                   break;
               default:
                   break;
           }
    }
   //全选框的状态
    public void  selectIscheck(boolean b){
        double total=0;

        for (int i=0;i<result.size();i++){
            result.get(i).setIscheck(b);
            total+=(result.get(i).getPrice()*result.get(i).getCount());
        }
        if (b){
            totalprice.setText(total+"");
        }
        else {
            totalprice.setText("0.0");
        }
    }
    //记载布局
    public void ShopCarLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shopcarrecycle.setLayoutManager(linearLayoutManager);
        shopcarrecycle.setAdapter(mCarGoodsAdpter);
        getShopCarData();
    }
    //加载数据
    public void getShopCarData()
    {
        iPresenter.requestDataPget(searchShopCar,ShopCarBean.class);
    }
    @Override
    public void requestDataV(Object data) {
       if (data instanceof ShopCarBean){
           ShopCarBean carBean = (ShopCarBean) data;
           mCarGoodsAdpter.setmList(carBean.getResult());
           result = carBean.getResult();
           selectAll.setChecked(false);

       }
    }

    //寻找id
    @Override
    protected void initView(View view) {
     shopcarrecycle = view.findViewById(R.id.shopcar_recycle);
     totalprice = view.findViewById(R.id.totalprice);
     selectAll=view.findViewById(R.id.shopcar_select_all);
     goSettel = view.findViewById(R.id.gosettel);
     goSettel.setOnClickListener(this);
     selectAll.setOnClickListener(this);
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
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
