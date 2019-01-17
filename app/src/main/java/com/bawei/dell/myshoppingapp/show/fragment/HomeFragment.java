package com.bawei.dell.myshoppingapp.show.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.app.MyApp;
import com.bawei.dell.myshoppingapp.base.BaseFrgment;
import com.bawei.dell.myshoppingapp.presenter.IPresenterImpl;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeFainshAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeGoodsMoreAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeGoodsSearchAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeHotAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeLifeAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeListSearchAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeOneListAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomePagerTransformer;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeTwoListAdpter;
import com.bawei.dell.myshoppingapp.show.home.adpter.HomeViewPagerAdpter;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeGoodsBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeGoodsMoreBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeGoodsSearchsBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeListSaerchBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeOneListBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeTwoListBean;
import com.bawei.dell.myshoppingapp.show.home.bean.HomeViewpagerBean;
import com.bawei.dell.myshoppingapp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
public class HomeFragment extends BaseFrgment implements View.OnClickListener ,IView {
    private String  mHotMoreUrl="commodity/v1/findCommodityListByLabel?labelId=%d&page=%d&count=%d";
    private String mBannerUrl = "commodity/v1/bannerShow";
    private String mHotGoodsUrl = "commodity/v1/commodityList";
    private String mHotSearchUrl="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    private String mOneListUrl="commodity/v1/findFirstCategory";
    private String mTwoListUr="commodity/v1/findSecondCategory?firstCategoryId=%s";
    private String mTwoSearchUrl="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    private ViewPager mHomeViewPager;
    private IPresenterImpl mIPresenterImpl;
    private HomeViewPagerAdpter mViewPagerAdpter;
    private int currentItem,mSpance = 2,moreid,page,searchPage,mTlistPage=1;
    private String mListSearchId;
    private HomeHotAdpter mHotAdpter;
    private RecyclerView mHotRecycle,mFainshRecycle,mLifeRecycle,mOneRecycle,mTwoRecycle;
    private HomeFainshAdpter mFainshAdpter;
    private HomeLifeAdpter mLifeAdpter;
    private ImageView mHotMore,mFashionMore,mLifeMore;
    private XRecyclerView moreRecycle,searchRecycle;
    private  HomeGoodsBean goodsBean;
    private ScrollView mHomeScroll;
    private HomeGoodsMoreAdpter mHomeMoreAdpter;
    private ImageView mHomeMoreBg,mHomeSearchRigth;
    private TextView mHomeMoreHeadline,mHomeSearchText;
    private RelativeLayout mRelativeHomeMore,mRelativeHomeSearch,mTwoListRelative,mNoneRelative;
    private EditText mHomeSearchEdit;
    private HomeGoodsSearchAdpter mSearchAdpter;
    private  String seachname;
    private HomeOneListAdpter oneListAdpter;
     private ImageView mListSearchBut;
     private Boolean listFalg=true;
     private HomeTwoListAdpter twoListAdpter;
     private HomeListSearchAdpter listSearchAdpter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            currentItem++;
            mHomeViewPager.setCurrentItem(currentItem);
            sendEmptyMessageDelayed(0,2000);
        }
    };
    @Override
    protected void initData(View view) {
          //绑定presenter
           initPresenter();
         //banner请求数据
          getBannerData();
         //商品返回数据
          getGoodsData();
    }

    //banner请求数据
    public void getBannerData(){
        mHomeViewPager.setPageMargin(10);
        mHomeViewPager.setOffscreenPageLimit(4);
        mHomeViewPager.setPageTransformer(true,new HomePagerTransformer());
        currentItem = mHomeViewPager.getCurrentItem();
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(currentItem,1000 );
        mIPresenterImpl.requestDataPget(mBannerUrl,HomeViewpagerBean.class);
    }
    //热销新品请求数据
    public void getGoodsData(){
        mIPresenterImpl.requestDataPget(mHotGoodsUrl,HomeGoodsBean.class);
        //热销新品
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mHotAdpter = new HomeHotAdpter(getContext());
        mHotRecycle.setLayoutManager(linearLayoutManager);
        mHotRecycle.setAdapter(mHotAdpter);
        //魔力时尚
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        mFainshAdpter = new HomeFainshAdpter(getContext());
        mFainshRecycle.setLayoutManager(linearLayoutManager1);
        mFainshRecycle.setAdapter(mFainshAdpter);
        //品质生活
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),mSpance);
        mLifeAdpter = new HomeLifeAdpter(getContext());
        mLifeRecycle.setLayoutManager(gridLayoutManager);
        mLifeRecycle.setAdapter(mLifeAdpter);
    }
    //加载更多商品的上拉刷新，下拉加载
    public void layoutGoodsMore(){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),mSpance);
            moreRecycle.setLayoutManager(gridLayoutManager);
            mHomeMoreAdpter = new HomeGoodsMoreAdpter(getContext());
            moreRecycle.setAdapter(mHomeMoreAdpter);
            moreRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page=1;
                    getMoreData(moreid,page);
                    moreRecycle.refreshComplete();
                }
                @Override
                public void onLoadMore() {
                   page++;
                   getMoreData(moreid,page);
                   moreRecycle.loadMoreComplete();
                }
            });
        getMoreData(moreid,page);
    }
    //查询的页面加载
    public void layoutGoodsSearch(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),mSpance);
        searchRecycle.setLayoutManager(gridLayoutManager);
        mSearchAdpter = new HomeGoodsSearchAdpter(getContext());
        searchRecycle.setAdapter(mSearchAdpter);
        searchRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                searchPage=1;
                getSearchData(seachname);
                searchRecycle.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                Log.i("LGY",searchPage+"");
                getSearchData(seachname);
                searchRecycle.loadMoreComplete();
            }

        });
        getSearchData(seachname);
    }
    //二级类表查询结果
    public void layoutListSearch(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),mSpance);
        searchRecycle.setLayoutManager(gridLayoutManager);
        listSearchAdpter = new HomeListSearchAdpter(getContext());
        searchRecycle.setAdapter(listSearchAdpter);
        searchRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mTlistPage=1;
                getTwoSearchData();
                searchRecycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                getTwoSearchData();
                searchRecycle.loadMoreComplete();
            }
        });
        getTwoSearchData();
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_newhot_more:
                   mHomeScroll.setVisibility(View.GONE);
                   mRelativeHomeSearch.setVisibility(View.GONE);
                   mRelativeHomeMore.setVisibility(View.VISIBLE);
                   mHomeMoreBg.setImageResource(R.mipmap.bg_rxxp_syf);
                   mHomeMoreHeadline.setText("热销新品");
                   int hot = goodsBean.getResult().getRxxp().get(0).getId();
                   page=1;
                   getMoreData(hot,page);
                   layoutGoodsMore();
                   break;
            case R.id.fashion_more:
                mHomeScroll.setVisibility(View.GONE);
                mRelativeHomeSearch.setVisibility(View.GONE);
                mRelativeHomeMore.setVisibility(View.VISIBLE);
                mHomeMoreBg.setImageResource(R.mipmap.bg_rxxp_syf);
                mHomeMoreHeadline.setText("魔力时尚");
                int fashion = goodsBean.getResult().getMlss().get(0).getId();
                page=1;
                getMoreData(fashion,page);

                layoutGoodsMore();
                break;
            case R.id.life_more:
                mHomeScroll.setVisibility(View.GONE);
                mRelativeHomeSearch.setVisibility(View.GONE);
                mRelativeHomeMore.setVisibility(View.VISIBLE);
                mHomeMoreBg.setImageResource(R.mipmap.bg_rxxp_syf);
                mHomeMoreHeadline.setText("品质生活");
                int life = goodsBean.getResult().getMlss().get(0).getId();
                page=1;
                getMoreData(life,page);
                layoutGoodsMore();
                break;
                //搜索框显示
            case R.id.home_sesrch_but1:
                Toast.makeText(getActivity(),"11111",Toast.LENGTH_SHORT).show();
                mHomeSearchEdit.setVisibility(View.VISIBLE);
                mHomeSearchRigth.setVisibility(View.GONE);
                mHomeSearchText.setVisibility(View.VISIBLE);
                break;
               //搜索框隐藏
            case R.id.home_sesrch_text:
                seachname = mHomeSearchEdit.getText().toString();
                 if (seachname.length()>0){
                     mRelativeHomeSearch.setVisibility(View.VISIBLE);
                     mHomeScroll.setVisibility(View.GONE);

                     searchPage=1;
                     layoutGoodsSearch();
                 }
                mHomeSearchEdit.setVisibility(View.INVISIBLE);
                mHomeSearchRigth.setVisibility(View.VISIBLE);
                mHomeSearchText.setVisibility(View.GONE);
                break;
            case R.id.home_sesrch_but:
                if (listFalg){
                    mTwoListRelative.setVisibility(View.VISIBLE);
                    getOneListData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                    mOneRecycle.setLayoutManager(linearLayoutManager);
                    oneListAdpter = new HomeOneListAdpter(getContext());
                    mOneRecycle.setAdapter(oneListAdpter);
                    oneListAdpter.setOnClickOneList(new HomeOneListAdpter.onClcikOneList() {
                        @Override
                        public void oneListOnClick(String id) {
                            getTwoListData(id);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                            twoListAdpter = new HomeTwoListAdpter(getContext());
                            mTwoRecycle.setLayoutManager(linearLayoutManager);
                            mTwoRecycle.setAdapter(twoListAdpter);
                            twoListAdpter.setOnClickTwoList(new HomeTwoListAdpter.onClickTwoList() {
                                @Override
                                public void onClickTwoListLister(String id) {
                                    mTlistPage=1;
                                    mTwoListRelative.setVisibility(View.GONE);
                                    mRelativeHomeMore.setVisibility(View.GONE);
                                    mRelativeHomeSearch.setVisibility(View.VISIBLE);
                                    mHomeScroll.setVisibility(View.GONE);
                                    searchRecycle.setVisibility(View.VISIBLE);
                                    mListSearchId = id;
                                    layoutListSearch();
                                    listFalg=!listFalg;
                                }
                            });
                        }
                    });
                }
                else {
                    mTwoListRelative.setVisibility(View.GONE);

                }
                listFalg=!listFalg;
                 break;
                  default:
                     break;
        }
    }
    //点击加载id
    public void getMoreData(int id,int page){
        moreid = id;
        mIPresenterImpl.requestDataPget(String.format(mHotMoreUrl,id,page,5),HomeGoodsMoreBean.class);
    }
    //查询网络请求
    public void getSearchData(String seachname){
        mIPresenterImpl.requestDataPget(String.format(mHotSearchUrl,seachname,searchPage,5),HomeGoodsSearchsBean.class);
    }
    //加载一级类表
    public void getOneListData(){
        mIPresenterImpl.requestDataPget(mOneListUrl,HomeOneListBean.class);
    }
    //加载二级类表
    public void getTwoListData(String id){
        mIPresenterImpl.requestDataPget(String.format(mTwoListUr,id),HomeTwoListBean.class);
    }
   //根据二级类表额的id查询数据
    public void getTwoSearchData(){
        mIPresenterImpl.requestDataPget(String.format(mTwoSearchUrl,mListSearchId,mTlistPage,5),HomeListSaerchBean.class);
    }

     //成功请求数据
    @Override
    public void requestDataV(Object data){
    //banner返回数据
      if (data instanceof HomeViewpagerBean){
           HomeViewpagerBean viewpagerBean = (HomeViewpagerBean) data;
           List<HomeViewpagerBean.ResultBean> result = viewpagerBean.getResult();
           mViewPagerAdpter = new HomeViewPagerAdpter(result, getContext());
          mHomeViewPager.setAdapter(mViewPagerAdpter);
                }
        //热销新品返回数据
        if (data instanceof HomeGoodsBean){
            //热销新品
            goodsBean = (HomeGoodsBean) data;
            List<HomeGoodsBean.ResultBean.RxxpBean.CommodityListBean> commodityList = goodsBean.getResult().getRxxp().get(0).getCommodityList();
            mHotAdpter.setmList(commodityList);
            //魔力时尚
            List<HomeGoodsBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList1 = goodsBean.getResult().getMlss().get(0).getCommodityList();
            mFainshAdpter.setmList(commodityList1);
            //品质生活
            List<HomeGoodsBean.ResultBean.PzshBean.CommodityListBeanX> commodityList2 = goodsBean.getResult().getPzsh().get(0).getCommodityList();
            mLifeAdpter.setmList(commodityList2);
        }
        //加载更多
        if (data instanceof HomeGoodsMoreBean){
            HomeGoodsMoreBean moreBean = (HomeGoodsMoreBean) data;
            List<HomeGoodsMoreBean.ResultBean>  result = moreBean.getResult();
            if (page==1){
                mHomeMoreAdpter.setmList(result);
            }
            else {
                mHomeMoreAdpter.addmList(result);
            }
            page++;
            if (result.size()==0){
                Toast.makeText(getActivity(),"没有更多商品了",Toast.LENGTH_SHORT).show();
            }
        }
        //关键字搜索商品
        if (data instanceof HomeGoodsSearchsBean){
            HomeGoodsSearchsBean searchsBean = (HomeGoodsSearchsBean) data;
            List<HomeGoodsSearchsBean.ResultBean> result = searchsBean.getResult();
            if (searchPage==1){
                mSearchAdpter.setmList(result);
                if (result.size()==0){
                    mNoneRelative.setVisibility(View.VISIBLE);
                    searchRecycle.setVisibility(View.GONE);
                }
                else {
                    searchRecycle.setVisibility(View.VISIBLE);
                    mNoneRelative.setVisibility(View.GONE);
                }
            }
            else {
                mSearchAdpter.addmList(result);
            }
            searchPage++;

        }
        //一级类表
        if (data instanceof HomeOneListBean){
          HomeOneListBean oneListBean = (HomeOneListBean) data;
          List<HomeOneListBean.ResultBean> result = oneListBean.getResult();
          oneListAdpter.setmList(result);
        }
        //二级列表
        if (data instanceof HomeTwoListBean){
            HomeTwoListBean twoListBean = (HomeTwoListBean) data;
            List<HomeTwoListBean.ResultBean> result = twoListBean.getResult();
            twoListAdpter.setmList(result);
        }
        //二级类表查询数据
        if (data instanceof HomeListSaerchBean){
          HomeListSaerchBean saerchBean = (HomeListSaerchBean) data;
            List<HomeListSaerchBean.ResultBean> result = saerchBean.getResult();
            if (mTlistPage==1){
                listSearchAdpter.setmList(result);
            }
            else {
                listSearchAdpter.addmList(result);
            }
            mTlistPage++;
          /* if (result.size()==0){
                Toast.makeText(getActivity(),"没有更多商品了",Toast.LENGTH_SHORT).show();
           }*/

        }
    }
    //获取资源ID
    @Override
    protected void initView(View view) {
        mHomeViewPager = view.findViewById(R.id.home_viewpager);
        mHotRecycle = view.findViewById(R.id.home_hot_recycle);
        mFainshRecycle = view.findViewById(R.id.home_fashion_recycle);
        mLifeRecycle = view.findViewById(R.id.home_life_recycle);
        mHotMore = view.findViewById(R.id.home_newhot_more);
        mFashionMore = view.findViewById(R.id.fashion_more);
        mLifeMore = view.findViewById(R.id.life_more);
        moreRecycle = view.findViewById(R.id.home_more_recycle);
        mHomeMoreBg = view.findViewById(R.id.home_more_bg);
        mHomeMoreHeadline = view.findViewById(R.id.home_more_headline);
        mRelativeHomeMore = view.findViewById(R.id.relative_home_more);
        mHomeScroll = view.findViewById(R.id.home_scroll);
        mHomeSearchRigth = view.findViewById(R.id.home_sesrch_but1);
        mHomeSearchText = view.findViewById(R.id.home_sesrch_text);
        mHomeSearchEdit = view.findViewById(R.id.home_sesrch_edit);
        searchRecycle = view.findViewById(R.id.home_search_recycle);
        mTwoListRelative = view.findViewById(R.id.two_list_relative);
        mRelativeHomeSearch = view.findViewById(R.id.search_relative);
        mOneRecycle  = view.findViewById(R.id.home_one_list);
        mListSearchBut  = view.findViewById(R.id.home_sesrch_but);
        mTwoRecycle  = view.findViewById(R.id.home_two_list);
        mNoneRelative = view.findViewById(R.id.home_none_goods);
        mListSearchBut.setOnClickListener(this);
        mHomeSearchRigth.setOnClickListener(this);
        mHomeSearchText.setOnClickListener(this);
        mHotMore.setOnClickListener(this);
        mFashionMore.setOnClickListener(this);
        mLifeMore.setOnClickListener(this);

}

   //监听返回键
    public void getBackData(boolean back){
        if(back){
            mRelativeHomeMore.setVisibility(View.GONE);
            mHomeSearchText.setVisibility(View.GONE);
            mHomeSearchRigth.setVisibility(View.VISIBLE);
            mHomeSearchEdit.setVisibility(View.INVISIBLE);
            mHomeScroll.setVisibility(View.VISIBLE);
            searchRecycle.setVisibility(View.GONE);
            mNoneRelative.setVisibility(View.GONE);
        }
    }

    //加载布局
    @Override
    protected int getViewById() {
        return R.layout.fragment_home;
    }
    //绑定
    public void initPresenter(){
        mIPresenterImpl = new IPresenterImpl(this);
    }
     //解绑
    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPresenterImpl.destory();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
