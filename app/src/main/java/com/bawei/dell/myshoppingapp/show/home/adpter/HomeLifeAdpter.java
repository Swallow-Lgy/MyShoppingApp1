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

public class HomeLifeAdpter extends RecyclerView.Adapter<HomeLifeAdpter.ViewHolder> {
    private List<HomeGoodsBean.ResultBean.PzshBean.CommodityListBeanX> mList;
    private Context mContext;

    public HomeLifeAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<HomeGoodsBean.ResultBean.PzshBean.CommodityListBeanX> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeLifeAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.home_life_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLifeAdpter.ViewHolder viewHolder, final int i) {
           viewHolder.lifeprice.setText("￥"+mList.get(i).getPrice()+"");
           viewHolder.lifeionname.setText(mList.get(i).getCommodityName());
           Glide.with(mContext).load(mList.get(i).getMasterPic()).into(viewHolder.lifeimage);
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
        private ImageView lifeimage;
        private TextView lifeionname,lifeprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lifeimage = itemView.findViewById(R.id.home_life_image);
            lifeionname = itemView.findViewById(R.id.home_life_goodsname);
            lifeprice = itemView.findViewById(R.id.home_life_goodsprice);
        }
    }

}
