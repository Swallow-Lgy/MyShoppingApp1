package com.bawei.dell.myshoppingapp.loaginreg.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.activity.MainActivity;
import com.bawei.dell.myshoppingapp.loaginreg.bean.LoaginBean;
import com.bawei.dell.myshoppingapp.loaginreg.validator.Validator;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.HashMap;

/**
 * 登录页面activity
 * */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IView {

    Button login_button;
    TextView mRegtext;
    private IPresenterImpl  mIPresenterImpl;
    private String  mLogUrl="user/v1/login";
    private EditText phonenum,password;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox login_remcheck;
    private ImageView login_eye;

    Button tiao;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loagin);
        //获取id
        initId();
        //绑定
        initPresentere();
        //密码显示隐藏
        setEye();
        //记住密码

        mSharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        boolean remcheck = mSharedPreferences.getBoolean("remcheck", false);
        if (remcheck){
            String phone = mSharedPreferences.getString("phone", null);
            String pwd = mSharedPreferences.getString("pwd", null);
            password.setText(pwd);
            phonenum.setText(phone);
            login_remcheck.setChecked(true);
        }
    }
    //密码显示隐藏
    @SuppressLint("ClickableViewAccessibility")
    private void setEye() {
        login_eye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return true;
            }
        });
    }

    //获取id
    public void initId(){
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        mRegtext = findViewById(R.id.reg_text);
        mRegtext.setOnClickListener(this);
        password = findViewById(R.id.password);
        phonenum = findViewById(R.id.phonenum);
        login_remcheck = findViewById(R.id.login_remcheck);
        login_eye = findViewById(R.id.login_icon_eye);

    }
    //请求数据
    public void initData(){
        HashMap<String,String> map = new HashMap<>();
        String  pwd = password.getText().toString();
        String pnum = phonenum.getText().toString();
        map.put("phone",pnum);
        map.put("pwd",pwd);
        mIPresenterImpl.requestDataPpost(mLogUrl,map,LoaginBean.class);
    }
    //点击事件
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            //登录按钮
            case R.id.login_button:
            //记住密码按钮
            case R.id.login_remcheck:
                String phone = phonenum.getText().toString();
                String pwd = password.getText().toString();
                if (login_remcheck.isChecked()){
                    editor.putBoolean("remcheck",true);
                    editor.putString("phone",phone);
                    editor.putString("pwd",pwd);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
           if (Validator.isPhoneValidator(phone)){
                   if (Validator.isPwdValidator(pwd)){
                        initData();
                   }else {
                       Toast.makeText(this,"输入正确的密码格式",Toast.LENGTH_SHORT).show();
                   }
                   } else {
                   Toast.makeText(this,"输入正确的手机格式",Toast.LENGTH_SHORT).show();
                   }
                break;
            case R.id.reg_text:
               Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(intent);
               break;
            default:
                break;
        }
    }
    //数据请求成功
    @Override
    public void requestDataV(Object data) {
        if (data instanceof LoaginBean){
            LoaginBean loaginBean = (LoaginBean) data;
            if (loaginBean.getStatus().equals("0000")){
                editor.putString("sessionId", loaginBean.getResult().getSessionId());
                editor.putString("userId", loaginBean.getResult().getUserId()+"");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this,loaginBean.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    //绑定
    public void initPresentere(){
        mIPresenterImpl = new IPresenterImpl(this);
    }
    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenterImpl.destory();
    }
}
