package com.bawei.dell.myshoppingapp.show.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.show.my.activity.FootmarkActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.MyAddressActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.MyMessageActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.WalletActivity;

/***
 * 作者:刘广燕
 * 时间：1.11
 */

public class MainFragment extends BaseFrgment implements View.OnClickListener{

    private TextView myaddress,mymessage,mycricle,mywallet,myfootmark;
    @Override
    protected void initData(View view) {

    }

    //点击事件
    @Override
    public void onClick(View v) {
          //我的收货地址
        switch (v.getId()){
            case R.id.shdz_text:
                Intent intent = new Intent(getActivity(),MyAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.grzl_text:
                Intent intent1 = new Intent(getActivity(),MyMessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.wdzj_text:
                Intent intent2 = new Intent(getActivity(),FootmarkActivity.class);
                startActivity(intent2);
                break;
            case R.id.wdqb_text:
                Intent intent3 = new Intent(getActivity(),WalletActivity.class);
                startActivity(intent3);
            default:
                    break;
        }
    }
    //获取资源ID
    @Override
    protected void initView(View view) {
        myaddress = view.findViewById(R.id.shdz_text);
        mycricle = view.findViewById(R.id.wdqz_text);
        myfootmark = view.findViewById(R.id.wdzj_text);
        mymessage = view.findViewById(R.id.grzl_text);
        mywallet = view.findViewById(R.id.wdqb_text);
        myaddress.setOnClickListener(this);
        mycricle.setOnClickListener(this);
        myfootmark.setOnClickListener(this);
        mymessage.setOnClickListener(this);
        mywallet.setOnClickListener(this);

    }
    //加载布局
    @Override
    protected int getViewById() {
        return R.layout.fragment_main;
    }


}
