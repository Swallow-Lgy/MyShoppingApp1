package com.bawei.dell.myshoppingapp.show.indent.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.base.BaseActivity;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.indent.adpter.RecycleImageAdpter;
import com.bawei.dell.myshoppingapp.show.indent.bean.CommitCricleBean;
import com.bawei.dell.myshoppingapp.show.indent.bean.IssueBean;
import com.bawei.dell.myshoppingapp.show.my.bean.UpdateHeadImage;
import com.bawei.dell.myshoppingapp.util.StringToFile;
import com.bawei.dell.myshoppingapp.view.IView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueActivity extends BaseActivity implements View.OnClickListener,IView {
    private IPresenterImpl iPresenter;
    private ImageView item_eva_image,iVimagetwo,iVimage;
    private TextView item_eva_name;
    private TextView item_eva_price;
    private CheckBox mCheck;
    private Button bPhoto,bClose,bCrame,mIssue;
    private EditText tVname;
    private String content;
    private String IssueUrl="commodity/verify/v1/addCommodityComment";
    private String cricleUrl="circle/verify/v1/releaseCircle";
    private int commid;
    private String orderId;
    private  Dialog dialog;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 裁剪之后
    private String path=Environment.getExternalStorageDirectory()+"/header_image.png";
    private RecycleImageAdpter imageAdpter;
    private RecyclerView imgaeRecycle;
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private File file;
    private List<File> file_list=new ArrayList<>();
    @Override
    protected void initData()
    {
        Intent intent = getIntent();
        commid = intent.getIntExtra("commid", 0);
        String name = intent.getStringExtra("name");
        final String image = intent.getStringExtra("image");
        String[] split = image.split("\\,");
        int price = intent.getIntExtra("price", 0);
        orderId = intent.getStringExtra("orderId");
        item_eva_name.setText(name);
        item_eva_price.setText("￥" + price + "");
        Glide.with(this).load(split[0]).into(item_eva_image);
        //初始化
        initPresenter();
        //设置图片的list集合和适配器
        initImageAdpter();
        //imageAdpter的条目点击事件
        oneImageClick();
    }
    public void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        //初始化控件
        inflate.findViewById(R.id.choosePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.takePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.btn_cancel).setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        if(dialogWindow == null){
            return;
         }
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    //设置图片的list集合和适配器
    private void  initImageAdpter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        imgaeRecycle.setLayoutManager(gridLayoutManager);
        imageAdpter = new RecycleImageAdpter(this);
        imgaeRecycle.setAdapter(imageAdpter);
    }
    //imageAdpter的条目点击事件
    public void  oneImageClick(){
        imageAdpter.getImage(new RecycleImageAdpter.ImageClick() {
            @Override
            public void getdata() {
                show();
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
                    ActivityCompat.requestPermissions(IssueActivity.this, mStatenetwork, 100);
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
          switch (v.getId())
          {
              case R.id.mIssue:
                  content = tVname.getText().toString();
                  Map<String,String> map=new HashMap<>();
                  map.put("commodityId",commid+"");
                  map.put("content",content);
                  map.put("orderId",orderId);
                  iPresenter.requestDataPduoContext(IssueUrl,map,file_list,IssueBean.class);
                   if (mCheck.isChecked())
                   {
                       Map<String,String> map1=new HashMap<>();
                       map1.put("commodityId",commid+"");
                       map1.put("content",content);
                       iPresenter.requestDataPduoContext(cricleUrl,map1,file_list,CommitCricleBean.class);
                   }
                  break;
                  //点击相机
              case R.id.takePhoto:
                  dialog.dismiss();
                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path)));
                  startActivityForResult(intent,PHOTO_REQUEST_CAREMA);
                  break;
              case R.id.choosePhoto:
                  dialog.dismiss();
                  Intent intent1 = new Intent(Intent.ACTION_PICK);
                  intent1.setType("image/*");
                  startActivityForResult(intent1,PHOTO_REQUEST_GALLERY);
                  break;
              case R.id.btn_cancel:
                  dialog.dismiss();
                  break;
                  default:
                      break;
          }
    }



    //回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相机返回的数据
        if (requestCode == PHOTO_REQUEST_CAREMA&&resultCode==RESULT_OK)
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
        else if(requestCode==PHOTO_REQUEST_GALLERY&&resultCode==RESULT_OK)
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
        //剪辑成功
        else if (requestCode == PHOTO_REQUEST_CUT&&resultCode==RESULT_OK)
        {
            if (data!=null)
            {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap!=null){
                    //将bitmap转换成uri
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null));
                    try {
                        StringToFile.saveBitmap(bitmap,PATH_FILE,50);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<Object>  list = new ArrayList<>();
                    list.add(bitmap);
                    imageAdpter.setmList(list);
                    file = new File(PATH_FILE);
                    file_list.add(file);
                }

            }
        }

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_issue;
    }
    @Override
    public void requestDataV(Object data) {
          if (data instanceof IssueBean){
              IssueBean issueBean = (IssueBean) data;
              if (issueBean.getStatus().equals("0000")){
                  Toast.makeText(IssueActivity.this,issueBean.getMessage(),Toast.LENGTH_SHORT).show();
              }
          }
        if (data instanceof CommitCricleBean){
            CommitCricleBean commitCricleBean = (CommitCricleBean) data;
        }
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        item_eva_image = findViewById(R.id.item_eva_image);
        item_eva_name =  findViewById(R.id.item_eva_name);
        item_eva_price =  findViewById(R.id.item_eva_price);
        imgaeRecycle = findViewById(R.id.issue_image_recycle);
        mCheck=findViewById(R.id.mCheck);
        tVname=findViewById(R.id.tVname);
        mIssue=findViewById(R.id.mIssue);
        mIssue.setOnClickListener(this);


    }
    public void initPresenter(){
        iPresenter = new IPresenterImpl(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.destory();
    }
}
