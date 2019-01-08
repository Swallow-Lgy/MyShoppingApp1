package com.bawei.dell.myshoppingapp.show.home.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeTwoListBean;

import java.util.ArrayList;
import java.util.List;

public class HomeTwoListAdpter extends RecyclerView.Adapter<HomeTwoListAdpter.ViewHolder> {
    private List<HomeTwoListBean.ResultBean> mList;
    private Context mContext;
    public HomeTwoListAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }
    public void setmList(List<HomeTwoListBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.home_twolist_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.twolistname.setText(mList.get(i).getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (monClickTwoList!=null){
                        monClickTwoList.onClickTwoListLister(mList.get(i).getId());
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView twolistname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            twolistname = itemView.findViewById(R.id.home_twolist_name);
        }
    }
    onClickTwoList monClickTwoList;
    public void setOnClickTwoList(onClickTwoList onClickTwoList){
            monClickTwoList = onClickTwoList;
    }
    public interface  onClickTwoList{
        void onClickTwoListLister(String id);
    }
}
