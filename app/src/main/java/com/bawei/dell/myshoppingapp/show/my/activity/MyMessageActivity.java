package com.bawei.dell.myshoppingapp.show.my.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
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
    private EditText edit_name;
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
        updateUserName();
        //修改密码
        updateUserPwd();
    }
   //修改名称
    public void updateUserName(){
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNameAler();
            }
        });
    }
    //修改密码
    public void updateUserPwd(){
         pwdnum.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updatePwdAlert();
             }
         });
    }
    //修改姓名的
    public void updateNameAler()
    {
        View nameView=View.inflate(MyMessageActivity.this,R.layout.alert_name,null);
        edit_name = nameView.findViewById(R.id.alert_edit_name);
        final AlertDialog.Builder builder=new AlertDialog
                .Builder(this)
                .setView(nameView);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(false);

        //确定事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    Map<String,String> params=new HashMap<>();
                    params.put("nickName",edit_name.getText().toString());
                    iPresenter.requestDataPput(updateNameUrl,params,UserNameUpdate.class);
                    dialog.dismiss();
                     getUserMeassage();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    //修改密码
    private void updatePwdAlert() {

        View passView=View.inflate(MyMessageActivity.this,R.layout.alert_pass,null);
        final EditText edit_pass = passView.findViewById(R.id.alert_edit_pass);
        final EditText edit_surepass = passView.findViewById(R.id.alert_edit_surepass);

        final AlertDialog.Builder builder=new AlertDialog
                .Builder(this)
                .setView(passView);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(false);

        //确定事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String,String> params=new HashMap<>();
                params.put("oldPwd",edit_pass.getText().toString());
                params.put("newPwd",edit_surepass.getText().toString());
                iPresenter.requestDataPput(updatePwdUrl,params,UserPwdUpdate.class);
                dialog.dismiss();
                getUserMeassage();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


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
            name.setText(result.getNickName());
            pwdnum.setText(result.getPassword());
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
