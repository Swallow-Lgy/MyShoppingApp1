package com.bawei.dell.myshoppingapp.show.indent.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.custom.CustomViewAdd;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class CreatIndedntAdpter extends RecyclerView.Adapter<CreatIndedntAdpter.ViewHolder> {
    private List<ShopCarBean.ResultBean> mList;
    private Context mContext;
    public CreatIndedntAdpter(List<ShopCarBean.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.creat_indent_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.price.setText(mList.get(i).getPrice()+"");
        viewHolder.name.setText(mList.get(i).getCommodityName());
        Glide.with(mContext).load(mList.get(i).getPic()).into(viewHolder.image);
        viewHolder.count.setText(mList.get(i).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name,price,count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.shopcar_item_image);
            name = itemView.findViewById(R.id.shopcar_item_name);
            price = itemView.findViewById(R.id.shopcar_item_price);
            count = itemView.findViewById(R.id.count);
        }
    }
}
