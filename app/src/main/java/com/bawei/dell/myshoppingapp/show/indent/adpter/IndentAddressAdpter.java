package com.bawei.dell.myshoppingapp.show.indent.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentAdressBean;

import java.util.ArrayList;
import java.util.List;

public class IndentAddressAdpter extends RecyclerView.Adapter<IndentAddressAdpter.ViewHolder> {
    private List<IndentAdressBean.ResultBean> mList;
    private Context mContext;
    public IndentAddressAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<IndentAdressBean.ResultBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.intent_address_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         viewHolder.address.setText(mList.get(i).getAddress());
         viewHolder.phone.setText(mList.get(i).getPhone());
         viewHolder.name.setText(mList.get(i).getRealName());
         viewHolder.choes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (monClick!=null){
                     monClick.onClickLisenter(mList.get(i).getId());
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,address;
        private TextView choes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.address_name);
            phone = itemView.findViewById(R.id.address_phone);
            address = itemView.findViewById(R.id.address_address);
            choes = itemView.findViewById(R.id.chose_address);
        }
    }
    onClick monClick;
    public void setOnClickLisenter(onClick onClick){
        monClick = onClick;
    }
    public interface onClick{
        void onClickLisenter(int id);
    }
}
