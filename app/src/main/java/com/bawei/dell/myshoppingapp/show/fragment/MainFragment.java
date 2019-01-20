package com.bawei.dell.myshoppingapp.show.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.custom.GlideCircleTransform;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.indent.activity.IssueActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.CricleActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.FootmarkActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.MyAddressActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.MyMessageActivity;
import com.bawei.dell.myshoppingapp.show.my.activity.WalletActivity;
import com.bawei.dell.myshoppingapp.show.my.bean.SearchMessageBean;
import com.bawei.dell.myshoppingapp.show.my.bean.UpdateHeadImage;
import com.bawei.dell.myshoppingapp.util.StringToFile;
import com.bawei.dell.myshoppingapp.view.IView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

/***
 * 作者:刘广燕
 * 时间：1.11
 */

public class MainFragment extends BaseFrgment implements View.OnClickListener,IView {

    private TextView myaddress,mymessage,mycricle,mywallet,myfootmark;
    private ImageView headimage;
    private TextView username;
    private IPresenterImpl iPresenter;
    private String searchMeaaage="user/verify/v1/getUserById";
    private String updateHead="user/verify/v1/modifyHeadPic";
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private PopupWindow popupWindow;
    private TextView cream;
    private TextView photograph;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 裁剪之后
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";//临时文件名
    private String path=Environment.getExternalStorageDirectory()+"/image.png";
    @Override
    protected void initData(View view) {
        //初始化
        initPresenter();
        //查询个人
        getUserMeassage();
        //弹出pop
        popShow();
        //点击相册
        onClickPhonto();
        //点击相机
        onClickCream();
    }

    //点击事件
    @Override
    public void onClick(View v)
    {
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
                break;
            case R.id.wdqz_text:
                Intent intent4 = new Intent(getActivity(),CricleActivity.class);
                startActivity(intent4);
                break;
            default:
                    break;
        }
    }
    //点击相册
    public void onClickPhonto(){
        photograph.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
            }
           });
    }
    //点击相机
    public void onClickCream(){
        cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path)));
                startActivityForResult(intent,PHOTO_REQUEST_CAREMA);
            }
        });
    }
     private void crop(Uri uri)
     {
         Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(uri, "image/*");
         intent.putExtra("crop", "true");
         // 裁剪框的比例，1：1
         intent.putExtra("aspectX", 1);
         intent.putExtra("aspectY", 1);
         // 裁剪后输出图片的尺寸大小
         intent.putExtra("outputX", 250);
         intent.putExtra("outputY", 250);
         intent.putExtra("return-data", true);
         startActivityForResult(intent, PHOTO_REQUEST_CUT); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
     }
     //回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相机返回的数据
        if (requestCode == PHOTO_REQUEST_CAREMA)
        {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }
        //相册返回的数据
        else if(requestCode==PHOTO_REQUEST_GALLERY)
        {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PHOTO_REQUEST_CUT);

        }
        else if (requestCode == PHOTO_REQUEST_CUT)
        {
            if (data!=null)
            {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap!=null){
                    //将bitmap转换成uri
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,null,null));
                    try {
                        StringToFile.saveBitmap(bitmap,PATH_FILE,50);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String,String> map = new HashMap<>();
                    map.put("image",PATH_FILE);
                    iPresenter.requestDataPpostFile(updateHead,map,UpdateHeadImage.class);
                }

            }
        }
        else {
            return;
        }
    }
    //点击头像弹出pop
    public void  popShow()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
                    //相机
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            };
            ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
        }

        View view = View.inflate(getActivity(),R.layout.my_pop_headimage,null);
            cream = view.findViewById(R.id.cream);
            photograph = view.findViewById(R.id.photograph);
        popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        //定义颜色颜色
        ColorDrawable cdw = new ColorDrawable(getResources().getColor(R.color.popcolor));
        //设置颜色
        popupWindow.setBackgroundDrawable(cdw);
        headimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v,1000,150);
            }
        });
        popupWindow.dismiss();
    }
    //查询个人信息
    public void  getUserMeassage(){
        iPresenter.requestDataPget(searchMeaaage,SearchMessageBean.class);
    }
    //请求成功数据

    @Override
    public void requestDataV(Object data) {
        if (data instanceof SearchMessageBean)
        {
            SearchMessageBean messageBean = (SearchMessageBean) data;
            SearchMessageBean.ResultBean result = messageBean.getResult();
            username.setText(result.getNickName());
            Glide.with(getActivity()).load(result.getHeadPic()).transform(new GlideCircleTransform(getActivity())).into(headimage);
        }
        if (data instanceof UpdateHeadImage)
        {
            UpdateHeadImage updateHeadImage = (UpdateHeadImage) data;
            Glide.with(getActivity()).load(updateHeadImage.getHeadPath()).transform(new GlideCircleTransform(getActivity())).into(headimage);
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
        headimage = view.findViewById(R.id.headimage);
        username = view.findViewById(R.id.username);
        myaddress.setOnClickListener(this);
        mycricle.setOnClickListener(this);
        myfootmark.setOnClickListener(this);
        mymessage.setOnClickListener(this);
        mywallet.setOnClickListener(this);
    }
    //加载布局
    @Override
    protected int getViewById()
    {
          return R.layout.fragment_main;
    }
    //销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
    public void initPresenter(){
        iPresenter = new IPresenterImpl(this);
    }
}
