package com.bawei.dell.myshoppingapp.show.cricle.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.cricle.bean.CricleListBean;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CricleListAdpter extends RecyclerView.Adapter<CricleListAdpter.ViewHolder> {
    private List<CricleListBean.ResultBean> mList;
    private Context mContext;

    public CricleListAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<CricleListBean.ResultBean> list) {
        mList.clear();
        if(list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<CricleListBean.ResultBean> list) {
        if(list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CricleListAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.cricle_list_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CricleListAdpter.ViewHolder viewHolder, final int i) {

          viewHolder.comment.setText(mList.get(i).getContent());
          viewHolder.num.setText(mList.get(i).getWhetherGreat()+"");
          viewHolder.name.setText(mList.get(i).getNickName());
           SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
          java.util.Date d1=new Date(mList.get(i).getCreateTime());
           String times = format.format(d1);
          viewHolder.time.setText(times);
          Glide.with(mContext).load(mList.get(i).getHeadPic()).into(viewHolder.headicon);
          Glide.with(mContext).load(mList.get(i).getImage()).into(viewHolder.shapeicon);
         if (mList.get(i).getWhetherGreat()==1){
             viewHolder.click.setImageResource(R.mipmap.common_btn_prise_s);
         }
         else {
             viewHolder.click.setImageResource(R.mipmap.common_btn_prise_n);
         }
         viewHolder.click.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (monClickGreat!=null){
                     if (mList.get(i).getWhetherGreat()==1){
                         monClickGreat.onClickGreatLisnter(true,mList.get(i).getId());
                     }
                     else {
                         monClickGreat.onClickGreatLisnter(false,mList.get(i).getId());
                     }
                 }
             }
         });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         private ImageView headicon,shapeicon,click;
         private TextView comment,time,num,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headicon = itemView.findViewById(R.id.cricle_list_headicon);
            click = itemView.findViewById(R.id.cricle_list_click);
            comment = itemView.findViewById(R.id.cricle_list_comment);
            name = itemView.findViewById(R.id.cricle_list_name);
            shapeicon = itemView.findViewById(R.id.cricle_list_shapeicon);
            num = itemView.findViewById(R.id.cricle_list_num);
            time = itemView.findViewById(R.id.cricle_list_time);
        }
    }
    onClickGreat monClickGreat;
    public void setOClickGreatLister(onClickGreat onClickGreat){
        monClickGreat=onClickGreat;
    }
    public interface onClickGreat{
        void onClickGreatLisnter(boolean b,int i);
    }
}
