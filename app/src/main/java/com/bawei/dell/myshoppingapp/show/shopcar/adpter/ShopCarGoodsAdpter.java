package com.bawei.dell.myshoppingapp.show.shopcar.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.custom.CustomViewAdd;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShopCarGoodsAdpter extends RecyclerView.Adapter<ShopCarGoodsAdpter.ViewHolder> {
    private List<ShopCarBean.ResultBean> mList;
    private Context mContext;
    public ShopCarGoodsAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<ShopCarBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shopcar_goods_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
           viewHolder.price.setText(mList.get(i).getPrice()+"");
           viewHolder.name.setText(mList.get(i).getCommodityName());
           viewHolder.checkBox.setChecked(mList.get(i).getIscheck());
           Glide.with(mContext).load(mList.get(i).getPic()).into(viewHolder.image);
           viewHolder.customViewAdd.setData(this,mList,i);
           viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   mList.get(i).setIscheck(isChecked);
                   if (mshopCarLisenter!=null){
                       mshopCarLisenter.shopOnCarLisenter(mList);
                   }
               }
           });
            viewHolder.customViewAdd.setOnCallBack(new CustomViewAdd.OnCall() {
                @Override
                public void onCallBack(int num) {
                    if (mshopCarLisenter!=null){
                        mshopCarLisenter.shopOnCarLisenter(mList);
                    }
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(i);
                    notifyDataSetChanged();
                    if (mDeleteLisenter!=null){
                        mDeleteLisenter.shopOnCarcLisenter(mList,i);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name,price;
        private CheckBox checkBox;
        private CustomViewAdd customViewAdd;
        private Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.shopcar_item_image);
            checkBox = itemView.findViewById(R.id.shopcar_item_check);
            name = itemView.findViewById(R.id.shopcar_item_name);
            price = itemView.findViewById(R.id.shopcar_item_price);
            customViewAdd = itemView.findViewById(R.id.customadd);
            delete = itemView.findViewById(R.id.shop_car_del);
        }
    }
    shopCarAddLisenter mshopCarLisenter;
    public void  setOnLisenter(shopCarAddLisenter carAddLisenter){
        mshopCarLisenter = carAddLisenter;
    }
    public interface shopCarAddLisenter{
        void shopOnCarLisenter(List<ShopCarBean.ResultBean> list);
    }
    DeleteLisenter mDeleteLisenter;
    public void  setOnDeleteLisenter(DeleteLisenter deleteLisenter){
        mDeleteLisenter = deleteLisenter;
    }
    public interface DeleteLisenter{
        void shopOnCarcLisenter(List<ShopCarBean.ResultBean> list,int i);
    }
}
