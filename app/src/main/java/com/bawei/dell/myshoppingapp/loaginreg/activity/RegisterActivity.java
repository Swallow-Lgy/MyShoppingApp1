package com.bawei.dell.myshoppingapp.loaginreg.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.loaginreg.bean.RegBean;
import com.bawei.dell.myshoppingapp.loaginreg.validator.Validator;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.HashMap;
/**
 * 注册页面activity
 * */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private EditText reg_phone,reg_password;
    private IPresenterImpl mIPresenterImpl;
    private String regurl="user/v1/register";
    private Button reg_botton;
    private ImageView reg_eye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //获取id
        initId();
        //绑定
        initPresenter();
        //密码框判断显示隐藏
        setEye();
    }
    //密码框判断显示隐藏
    @SuppressLint("ClickableViewAccessibility")
    private void setEye() {
        reg_eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    reg_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    reg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
    }
    //获取id
    private void initId(){
        reg_password = findViewById(R.id.reg_password);
        reg_phone = findViewById(R.id.reg_phonenum);
        reg_botton = findViewById(R.id.reg_botton);
        reg_botton.setOnClickListener(this);
        reg_eye = findViewById(R.id.reg_icon_eye);
    }
    //请求数据
     public void initData(){
         HashMap<String,String> map = new HashMap<>();
         map.put("phone",reg_phone.getText()+"");
         map.put("pwd",reg_password.getText()+"");
         mIPresenterImpl.requestDataPpost(regurl,map,RegBean.class);
     }
     //点击事件
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.reg_botton:
            if (Validator.isPhoneValidator(reg_phone.getText().toString())){
                if (Validator.isPwdValidator(reg_password.getText().toString())){
                    initData();
                } else {
                    Toast.makeText(this,"输入正确的密码格式",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"输入正确的手机格式",Toast.LENGTH_SHORT).show();
            }
            break;
             default:
                 break;
       }
    }
    //返回数据
    @Override
    public void requestDataV(Object data) {
         if (data instanceof RegBean){
             RegBean regBean = (RegBean) data;
             String status = regBean.getStatus();
             if (status.equals("0000")){
                 Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                 startActivity(intent);
                 finish();
             }else{
                 Toast.makeText(this,regBean.getMessage(),Toast.LENGTH_SHORT).show();
             }
         }
    }
    //绑定
    public void initPresenter(){
         mIPresenterImpl = new IPresenterImpl(this);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenterImpl.destory();
    }
}
