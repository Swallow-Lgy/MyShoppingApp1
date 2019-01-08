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
import com.bawei.dell.myshoppingapp.show.home.bean.HomeGoodsSearchsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeGoodsSearchAdpter extends RecyclerView.Adapter<HomeGoodsSearchAdpter.ViewHolder> {
    private List<HomeGoodsSearchsBean.ResultBean> mList;
    private Context mContext;

    public HomeGoodsSearchAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<HomeGoodsSearchsBean.ResultBean> list) {
       mList.clear();
       if (list!=null){
           mList.addAll(list);
       }
       notifyDataSetChanged();
    }
    public void addmList(List<HomeGoodsSearchsBean.ResultBean> list) {

        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  View.inflate(mContext,R.layout.home_search_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         viewHolder.searchsales.setText("已销售"+mList.get(i).getSaleNum()+"件");
         viewHolder.searchtitle.setText(mList.get(i).getCommodityName());
         viewHolder.searchprice.setText("￥"+mList.get(i).getPrice());
        Glide.with(mContext).load(mList.get(i).getMasterPic()).into(viewHolder.searchImage);
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
        private ImageView searchImage;
        private TextView searchprice,searchtitle,searchsales;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchImage = itemView.findViewById(R.id.home_moregs_icon);
            searchprice = itemView.findViewById(R.id.home_moregs_price);
             searchtitle= itemView.findViewById(R.id.home_moregs_title);
            searchsales = itemView.findViewById(R.id.home_moregs_sales);
        }
    }
}
