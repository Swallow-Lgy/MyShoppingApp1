package com.bawei.dell.myshoppingapp.show.my.activity;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.my.bean.SearchMessageBean;
import com.bawei.dell.myshoppingapp.show.my.bean.UserNameUpdate;
import com.bawei.dell.myshoppingapp.show.my.bean.UserPwdUpdate;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.HashMap;
import java.util.Map;

public class MyMessageActivity extends BaseActivity implements IView {
    private String updateNameUrl = "user/verify/v1/modifyUserNick";
    private String updatePwdUrl ="user/verify/v1/modifyUserPwd";
    private SharedPreferences sharedPreferences;
    private TextView name, pwdnum;
    private String searchMeaaage="user/verify/v1/getUserById";
    private IPresenterImpl iPresenter;
    private EditText newname;
    private PopupWindow popupWindow;
    @Override
    protected void initData() {
        //绑定
        initPrsenter();
        //请求查询的数据
        getUserMeassage();
        //得到账号和密码
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        //修改姓名
        /*updateUserName();*/
        //修改密码
        updateUserPwd();
    }
   /*//修改名称
    public void updateUserName(){
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }
                else {
                    String newname = name.getText().toString();
                    Map<String,String> map = new HashMap<>();
                    map.put("nickName",newname);
                    iPresenter.requestDataPput(updateNameUrl,map,UserNameUpdate.class);
                }
            }
        });
    }*/
    //修改密码
    public void updateUserPwd(){
        pwdnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }
                else {
                    String newpwd = pwdnum.getText().toString();
                    String pwd = sharedPreferences.getString("pwd", null);
                    Map<String,String> map = new HashMap<>();
                    map.put("newPwd",newpwd);
                    map.put("oldPwd",pwd);
                    iPresenter.requestDataPput(updatePwdUrl,map,UserPwdUpdate.class);
                }
            }
        });
    }
    //判断聚焦
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点所在的控件；
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                // 判断点击的点是否落在当前焦点所在的 view 上；
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //修改姓名的pop
    public void uodateName(){
        final View view = View.inflate(this,R.layout.pop_name,null);
        newname = view.findViewById(R.id.newname_edit);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        //获取焦点
        popupWindow.setFocusable(true);
        //定义颜色颜色
        ColorDrawable cdw = new ColorDrawable(getResources().getColor(R.color.popcolor));
        //设置颜色
        popupWindow.setBackgroundDrawable(cdw);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v,0,1);

            }
        });
        popupWindow.dismiss();
    }
   //查询个人信息
    public void  getUserMeassage(){
        iPresenter.requestDataPget(searchMeaaage,SearchMessageBean.class);
    }
    @Override
    public void requestDataV(Object data) {
        if (data instanceof SearchMessageBean){
            SearchMessageBean messageBean = (SearchMessageBean) data;
            SearchMessageBean.ResultBean result = messageBean.getResult();
            String pwd = sharedPreferences.getString("pwd", null);
            name.setText(result.getNickName());
            pwdnum.setText(pwd);
        }
        if (data instanceof UserNameUpdate){
            UserNameUpdate nameUpdate = (UserNameUpdate) data;
           if ( nameUpdate.getStatus().equals("0000")){
               Toast.makeText(MyMessageActivity.this,nameUpdate.getMessage(),Toast.LENGTH_SHORT).show();
           }
        }
        if (data instanceof UserPwdUpdate){
            UserPwdUpdate pwdUpdate = (UserPwdUpdate) data;
            if ( pwdUpdate.getStatus().equals("0000")){
                Toast.makeText(MyMessageActivity.this,pwdUpdate.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        name = findViewById(R.id.meaage_name);
        pwdnum = findViewById(R.id.meaage_password);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_my_message;
    }
    //绑定
    public void initPrsenter(){
        iPresenter = new IPresenterImpl(this);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
