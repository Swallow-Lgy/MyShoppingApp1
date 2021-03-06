package com.bawei.dell.myshoppingapp.show.shopcar.bean;

import java.io.Serializable;
import java.util.List;

public class ShopCarBean implements Serializable{


    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private int commodityId;
        private String commodityName;
        private String pic;
        private double price;
        private int count;
        private boolean ischeck=false;

        public ResultBean(int commodityId, String commodityName, String pic, double price, int count, boolean ischeck) {
            this.commodityId = commodityId;
            this.commodityName = commodityName;
            this.pic = pic;
            this.price = price;
            this.count = count;
            this.ischeck = ischeck;
        }

        public boolean getIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
