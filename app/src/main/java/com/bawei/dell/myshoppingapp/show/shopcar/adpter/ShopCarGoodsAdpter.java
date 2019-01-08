package com.bawei.dell.myshoppingapp.show.shopcar.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
           viewHolder.price.setText(mList.get(i).getPrice()+"");
           viewHolder.name.setText(mList.get(i).getCommodityName());
           viewHolder.checkBox.setChecked(mList.get(i).getIscheck());
           Glide.with(mContext).load(mList.get(i).getPic()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name,price;
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.shopcar_item_image);
            checkBox = itemView.findViewById(R.id.shopcar_item_check);
            name = itemView.findViewById(R.id.shopcar_item_name);
            price = itemView.findViewById(R.id.shopcar_item_price);
        }
    }
}
