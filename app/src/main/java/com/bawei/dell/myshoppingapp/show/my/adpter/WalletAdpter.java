package com.bawei.dell.myshoppingapp.show.my.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.my.bean.WalletBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WalletAdpter extends RecyclerView.Adapter<WalletAdpter.ViewHolder> {
    private List<WalletBean.ResultBean.DetailListBean> mList;
    private Context mContext;

    public WalletAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<WalletBean.ResultBean.DetailListBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<WalletBean.ResultBean.DetailListBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.my_wallet_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
           String date = new SimpleDateFormat("yyyy-MM-dd hh").format(
                new java.util.Date(mList.get(i).getCreateTime()));
           viewHolder.time.setText(date);
           viewHolder.moeny.setText("￥"+mList.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView moeny,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moeny =itemView.findViewById(R.id.wallet_item_money);
            time =itemView.findViewById(R.id.wallet_item_time);

        }
    }
}
