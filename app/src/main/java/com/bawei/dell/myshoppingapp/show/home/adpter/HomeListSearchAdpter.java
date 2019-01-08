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
import com.bawei.dell.myshoppingapp.show.home.bean.HomeListSaerchBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeListSearchAdpter extends RecyclerView.Adapter<HomeListSearchAdpter.ViewHolder> {
    private List<HomeListSaerchBean.ResultBean> mList;
    private Context mContext;

    public HomeListSearchAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<HomeListSaerchBean.ResultBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<HomeListSaerchBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.home_search_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext).load(mList.get(i).getMasterPic()).into(viewHolder.twoSearchImage);
        viewHolder.twoSearchsales.setText("已售"+mList.get(i).getSaleNum()+"件");
        viewHolder.twoSearchtitle.setText(mList.get(i).getCommodityName());
        viewHolder.twoSearchprice.setText("￥"+mList.get(i).getPrice());
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
        private ImageView twoSearchImage;
        private TextView twoSearchprice,twoSearchtitle,twoSearchsales;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            twoSearchImage = itemView.findViewById(R.id.home_moregs_icon);
            twoSearchprice = itemView.findViewById(R.id.home_moregs_price);
            twoSearchtitle= itemView.findViewById(R.id.home_moregs_title);
            twoSearchsales = itemView.findViewById(R.id.home_moregs_sales);
        }
    }
}
