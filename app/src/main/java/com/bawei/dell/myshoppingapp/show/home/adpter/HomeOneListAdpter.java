package com.bawei.dell.myshoppingapp.show.home.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeOneListBean;

import java.util.ArrayList;
import java.util.List;

public class HomeOneListAdpter extends RecyclerView.Adapter<HomeOneListAdpter.ViewHolder> {
    private List<HomeOneListBean.ResultBean> mList;
    private Context mContext;
    private List<Boolean> str;
    public HomeOneListAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        str = new ArrayList<>();
    }

    public void setmList(List<HomeOneListBean.ResultBean> mList) {
        this.mList = mList;
        for (int i=0;i<mList.size();i++){
            str.add(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.home_onelist_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         viewHolder.listonename.setText(mList.get(i).getName());
           if (str.get(i)){
               viewHolder.listonename.setTextColor(Color.RED);
           }
           else {
               viewHolder.listonename.setTextColor(Color.RED);
           }
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (monClcikOneList!=null){
                     monClcikOneList.oneListOnClick(mList.get(i).getId());
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView listonename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listonename = itemView.findViewById(R.id.home_onelist_name);
        }
    }
    onClcikOneList monClcikOneList;
    public void setOnClickOneList(onClcikOneList onClcikOneList){
        monClcikOneList = onClcikOneList;
    }
    public interface onClcikOneList{
        void oneListOnClick(String id);
    }
}
