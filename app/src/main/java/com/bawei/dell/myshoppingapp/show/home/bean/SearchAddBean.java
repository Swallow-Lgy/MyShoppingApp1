package com.bawei.dell.myshoppingapp.show.home.bean;

public class SearchAddBean {
     int commodityId;
     int count;

    public SearchAddBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public SearchAddBean() {
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
