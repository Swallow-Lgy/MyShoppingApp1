package com.bawei.dell.myshoppingapp.show.my.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentAdressBean;
import com.bawei.dell.myshoppingapp.show.my.bean.MyAddressBean;

import java.util.ArrayList;
import java.util.List;

public class MyAddressAdpter extends RecyclerView.Adapter<MyAddressAdpter.ViewHolder> {
    private List<MyAddressBean.ResultBean> mList;
    private Context mContext;
    public MyAddressAdpter(Context mContext) {
        this.mContext = mContext;
        mList  = new ArrayList<>();
    }

    public void setmList(List<MyAddressBean.ResultBean> mList)
    {
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = View.inflate(mContext, R.layout.my_address_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mList.get(i).getRealName());
        viewHolder.phone.setText(mList.get(i).getPhone());
        viewHolder.address.setText(mList.get(i).getAddress());
        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mupDateOnclick!=null){
                    mupDateOnclick.upDateOnClickListener(i,mList.get(i).getId());
                }
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,phone,address;
        Button update,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_address_name);
            phone = itemView.findViewById(R.id.my_address_phone);
            address = itemView.findViewById(R.id.my_address_address);
            update = itemView.findViewById(R.id.my_address_update);
            delete = itemView.findViewById(R.id.my_address_delete);
        }
    }
    //修改的点击事件
    upDateOnclick mupDateOnclick;
    public void setMupDateOnclick(upDateOnclick upDateOnclick){
        mupDateOnclick = upDateOnclick;
    }
    public interface upDateOnclick{
        void upDateOnClickListener(int i,int id);
    }
    //删除
    delOnclick mDelOnclick;
    public void setDelOnclick(delOnclick delOnclick){
        mDelOnclick = delOnclick;
    }
    public interface delOnclick{
        void upDateOnClickListener(int i,int id);
    }
}
