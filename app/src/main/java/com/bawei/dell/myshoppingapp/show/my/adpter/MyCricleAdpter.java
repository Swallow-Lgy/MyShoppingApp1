package com.bawei.dell.myshoppingapp.show.my.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.my.bean.MyCriCleBean;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCricleAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyCriCleBean.ResultBean> mList;
    private Context mContext;

    public MyCricleAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<MyCriCleBean.ResultBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<MyCriCleBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view = View.inflate(mContext,R.layout.my_circle_item_view_one,null);
      return new ViewHodlerOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHodlerOne viewHodlerOne = (ViewHodlerOne) viewHolder;
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                new java.util.Date(mList.get(i).getCreateTime()));
        viewHodlerOne.onetime.setText(date);
        viewHodlerOne.onecontext.setText(mList.get(i).getContent());
        viewHodlerOne.onecheck.setChecked(mList.get(i).isIscheck());
        Glide.with(mContext).load(mList.get(i).getImage()).into(viewHodlerOne.oneimage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class ViewHodlerOne extends RecyclerView.ViewHolder{
        private TextView onecontext,onetime;
        private ImageView oneimage;
        private CheckBox onecheck;
        public ViewHodlerOne(@NonNull View itemView)
        {
            super(itemView);
            onecheck = itemView.findViewById(R.id.circle_check1);
            onecontext = itemView.findViewById(R.id.circle_content1);
            onetime = itemView.findViewById(R.id.circle_time1);
            oneimage = itemView.findViewById(R.id.circle_image1);
        }
    }

}
