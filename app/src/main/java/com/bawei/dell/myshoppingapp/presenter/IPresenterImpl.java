package com.bawei.dell.myshoppingapp.presenter;

import com.bawei.dell.myshoppingapp.callback.MyCallBack;
import com.bawei.dell.myshoppingapp.model.IModelImpl;
import com.bawei.dell.myshoppingapp.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView mIView;
    private IModelImpl mIModelImpl;

    public IPresenterImpl(IView mIView) {
        this.mIView = mIView;
         mIModelImpl = new IModelImpl();
    }
  //从m层情求数据返回给v层post
    @Override
    public void requestDataPpost(String url, Map<String, String> map, Class clazz) {
        mIModelImpl.requestDataMpost(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.requestDataV(data);
            }
        });
    }
    //从m层情求数据返回给v层get
    @Override
    public void requestDataPget(String url,Class clazz) {
        mIModelImpl.requestDataMget(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.requestDataV(data);
            }
        });
    }

    @Override
    public void requestDataPdelelte(String url, Class clazz) {
        mIModelImpl.requestDataMgdelete(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.requestDataV(data);
            }
        });
    }

    @Override
    public void requestDataPput(String url, Map<String, String> map, Class clazz) {
        mIModelImpl.requestDataMput(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.requestDataV(data);
            }
        });
    }

    //解绑
    public void destory(){
        if (mIModelImpl!=null){
            mIModelImpl=null;
        }
        if (mIView!=null){
            mIView=null;
        }
    }
}
