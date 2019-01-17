package com.bawei.dell.myshoppingapp.show.indent.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class IndentItemjudegAdpter extends RecyclerView.Adapter<IndentItemjudegAdpter.ViewHolder> {
   private List<IndentBean.OrderListBean.DetailListBean> mList;
   private Context mContext;

    public IndentItemjudegAdpter(List<IndentBean.OrderListBean.DetailListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = View.inflate(mContext, R.layout.indent_judge_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         viewHolder.price.setText(mList.get(i).getCommodityPrice()+"");
         viewHolder.name.setText(mList.get(i).getCommodityName());
        String commodityPic = mList.get(i).getCommodityPic();
        String[] split = commodityPic.split(",");
        Glide.with(mContext).load(split[0]).into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name,price,count;
        Button gojudeg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.shopcar_item_image);
            name = itemView.findViewById(R.id.shopcar_item_name);
            price = itemView.findViewById(R.id.shopcar_item_price);
            gojudeg = itemView.findViewById(R.id.gojudeg);
        }
    }
    //确认收货的接口回调
    onJudegClcik monJudegClcik;
    public void setOnJudegClcikLisenter(onJudegClcik judegClcik){
        monJudegClcik = judegClcik;
    }
    public interface onJudegClcik{
        void onJudegClickLisenter();
    }
}
