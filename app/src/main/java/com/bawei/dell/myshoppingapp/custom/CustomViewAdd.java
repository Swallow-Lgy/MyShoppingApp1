package com.bawei.dell.myshoppingapp.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.shopcar.adpter.ShopCarGoodsAdpter;
import com.bawei.dell.myshoppingapp.show.shopcar.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class CustomViewAdd  extends LinearLayout implements View.OnClickListener {
    private EditText editText;
    public CustomViewAdd(Context context) {
        super(context);
        init(context);
    }

    public CustomViewAdd(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomViewAdd(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;
    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.shopcar_addview_item,null);
        Button jia=view.findViewById(R.id.jia);
        jia.setOnClickListener(this);
        Button jian=view.findViewById(R.id.jian);
        jian.setOnClickListener(this);
        editText = view.findViewById(R.id.jsedit);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String str = s.toString();
                    count = Integer.valueOf(str);

                }
                catch (Exception e){
                    list.get(position).setCount(1);
                }
                if (monCall!=null){
                    monCall.onCallBack(count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private int count;
    private List<ShopCarBean.ResultBean> list = new ArrayList<>();
    private int position;
    private ShopCarGoodsAdpter carAdpter;
    public void setData(ShopCarGoodsAdpter mcarAdpter, List<ShopCarBean.ResultBean>list, int i){
        this.list=list;
        this.position=i;
        this.carAdpter=mcarAdpter;
        count=list.get(i).getCount();
        editText.setText(count+"");
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.jia:
                count++;
                editText.setText(count + "");
                list.get(position).setCount(count);
                monCall.onCallBack(count);
                carAdpter.notifyDataSetChanged();
                break;
            case R.id.jian:
                if (count>1){
                    count--;
                }
                else {
                    Toast.makeText(context,"我是有底线的",Toast.LENGTH_SHORT);
                }
                editText.setText(count+"");
                list.get(position).setCount(count);
                monCall.onCallBack(count);
                carAdpter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }
    OnCall monCall;
    public void setOnCallBack(OnCall onCall){
        monCall=onCall;
    }
    public interface  OnCall{
        void onCallBack(int num);
    }
}
