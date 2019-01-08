package com.bawei.dell.myshoppingapp.show.home.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.home.activity.GoodsDetailedActivity;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeGoodsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeFainshAdpter extends RecyclerView.Adapter<HomeFainshAdpter.ViewHolder> {
    private List<HomeGoodsBean.ResultBean.MlssBean.CommodityListBeanXX> mList;
    private Context mContext;

    public HomeFainshAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<HomeGoodsBean.ResultBean.MlssBean.CommodityListBeanXX> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeFainshAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.home_fashion_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFainshAdpter.ViewHolder viewHolder, final int i) {
           viewHolder.fashionprice.setText("￥"+mList.get(i).getPrice()+"");
           viewHolder.fashionname.setText(mList.get(i).getCommodityName());
           Glide.with(mContext).load(mList.get(i).getMasterPic()).into(viewHolder.fashionimage);
           viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(mContext,GoodsDetailedActivity.class);
                   intent.putExtra("id",mList.get(i).getCommodityId());
                   mContext.startActivity(intent);
               }
           });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fashionimage;
        private TextView fashionname,fashionprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fashionimage = itemView.findViewById(R.id.home_fashion_image);
            fashionname = itemView.findViewById(R.id.home_fashion_goodsname);
            fashionprice = itemView.findViewById(R.id.home_fashion_goodsprice);
        }
    }

}
