package com.bawei.dell.myshoppingapp.show.indent.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bawei.dell.myshoppingapp.R;
import com.bawei.dell.myshoppingapp.show.indent.bean.IndentBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IndentAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IndentBean.OrderListBean>  mList;
    private Context mContext;
    private final int TYPE_OBLIGATION=1;
    private final int TYPE_WAIT=2;
    private final int TYPE_JUDGE=3;
    private final int TYPE_COMPLEED=9;

    public IndentAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }
    public void setmList(List<IndentBean.OrderListBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<IndentBean.OrderListBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getOrderStatus()==TYPE_OBLIGATION){
            return TYPE_OBLIGATION;
        }
        if (mList.get(position).getOrderStatus()==TYPE_WAIT){
            return TYPE_WAIT;
        }
        if (mList.get(position).getOrderStatus()==TYPE_JUDGE){
            return TYPE_JUDGE;
        }
        else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_OBLIGATION:
                 View  obligationview = View.inflate(mContext, R.layout.obligation_indent,null);
                 return new ObligationViewHolder(obligationview);
            case TYPE_WAIT:
                  View waitview = View.inflate(mContext,R.layout.wait_indent,null);
                 return new WaitViewHolder(waitview);
            case TYPE_JUDGE:
                View view =  View.inflate(mContext,R.layout.judge_indent,null);
                 return  new JudegViewHolder(view);
                default:
                    return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        final IndentBean.OrderListBean orderListBean = mList.get(i);
        //循环所有的类型
        switch (itemViewType){
            //待付款
            case TYPE_OBLIGATION:
                final ObligationViewHolder obligation = (ObligationViewHolder) viewHolder;
                obligation.obligationum.setText(orderListBean.getOrderId());
                String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                new java.util.Date(orderListBean.getOrderTime()));
                obligation.obligatiodata.setText(date);
                IndentItemAdpter adpter = new IndentItemAdpter(orderListBean.getDetailList(), mContext);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                obligation.obligatiorecyclerView.setAdapter(adpter);
                obligation.obligatiorecyclerView.setLayoutManager(linearLayoutManager);
                int size = orderListBean.getDetailList().size();
                int total=0;
                double price=0;
                for (int j=0;j<size;j++){
                    IndentBean.OrderListBean.DetailListBean detail = orderListBean.getDetailList().get(j);
                    total+=detail.getCommodityCount();
                    price +=detail.getCommodityCount()*detail.getCommodityPrice();
                }
                obligation.obligatiosum.setText(total+"");
                obligation.obligationprice.setText(price+"");
                final double finalPrice = price;
                obligation.obligatipayBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (monPayClcik!=null){
                             monPayClcik.onPayClickLisenter(orderListBean.getOrderId(),
                                     orderListBean.getOrderStatus()
                                     );
                        }
                    }
                });
                obligation.obligatipayDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (monDelClcik!=null){
                            monDelClcik.onDelClickLisenter(orderListBean.getOrderId(),i);
                        }
                    }
                });
                break;
                //待收货
            case TYPE_WAIT:
                WaitViewHolder waitViewHolder = (WaitViewHolder) viewHolder;
                waitViewHolder.waitcode.setText(orderListBean.getOrderId());
                waitViewHolder.waittype.setText(orderListBean.getExpressCompName());
                waitViewHolder.quickcode.setText(orderListBean.getExpressSn());
                String waitdate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                new java.util.Date(orderListBean.getOrderTime()));
                waitViewHolder.waittime.setText(waitdate);
                LinearLayoutManager linearLayoutManagerw=new LinearLayoutManager(mContext);
                linearLayoutManagerw.setOrientation(OrientationHelper.VERTICAL);
                IndentItemAdpter waitadpter = new IndentItemAdpter(orderListBean.getDetailList(), mContext);
                waitViewHolder.waitrecycle.setAdapter(waitadpter);
                waitViewHolder.waitrecycle.setLayoutManager(linearLayoutManagerw);
                waitViewHolder.waitbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (monJudegClcik!=null){
                            monJudegClcik.onJudegClickLisenter(orderListBean.getOrderId(),orderListBean.getOrderStatus());
                        }
                    }
                });
                break;
                //待评价
            case TYPE_JUDGE:
                JudegViewHolder judegViewHolder = (JudegViewHolder) viewHolder;
                judegViewHolder.judgecode.setText(orderListBean.getOrderId());
                String judgdate = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                new java.util.Date(orderListBean.getOrderTime()));
                judegViewHolder.judgetime.setText(judgdate);
                IndentItemjudegAdpter indentItemjudegAdpter = new IndentItemjudegAdpter(orderListBean.getDetailList(),mContext);
                LinearLayoutManager linearLayoutManagerj = new LinearLayoutManager(mContext);
                judegViewHolder.judgerecycle.setAdapter(indentItemjudegAdpter);
                judegViewHolder.judgerecycle.setLayoutManager(linearLayoutManagerj);

               break;
                default:
                    return;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //待付款
   public class ObligationViewHolder extends RecyclerView.ViewHolder{
       private TextView obligationum;
       private TextView obligatiodata;
       private TextView obligationprice,obligatiosum;
       private Button obligatipayBut,obligatipayDel;
       private RecyclerView obligatiorecyclerView;
       public ObligationViewHolder(@NonNull View itemView) {
           super(itemView);
           obligatiodata = itemView.findViewById(R.id.obligation_indent_data);
           obligationum = itemView.findViewById(R.id.obligation_indent_num);
           obligatiosum = itemView.findViewById(R.id.obligation_indent_sum);
           obligationprice = itemView.findViewById(R.id.obligation_indent_sumprice);
           obligatiorecyclerView = itemView.findViewById(R.id.obligation_indent_recycle);
            obligatipayBut  = itemView.findViewById(R.id.obligation_indent_pay);
            obligatipayDel = itemView.findViewById(R.id.obligation_but_cancel);
       }
   }
   //待收货
    public class WaitViewHolder extends RecyclerView.ViewHolder{
       TextView waitcode, waittime,waittype,quickcode;
       Button waitbut;
       RecyclerView waitrecycle;
       public WaitViewHolder(@NonNull View itemView) {
           super(itemView);
           waitcode= itemView.findViewById(R.id.wait_item_code);
           waittime =  itemView.findViewById(R.id.wait_item_time);
           waittype =  itemView.findViewById(R.id.wait_quick_type);
           quickcode =  itemView.findViewById(R.id.wait_quickly_code);
           waitbut = itemView.findViewById(R.id.wait_but);
           waitrecycle =  itemView.findViewById(R.id.wait_item_recycler);
       }
   }
  //待评价
    public class JudegViewHolder extends RecyclerView.ViewHolder{
         TextView judgecode,judgemore,judgetime;
          RecyclerView judgerecycle;
      public JudegViewHolder(@NonNull View itemView) {
          super(itemView);
          judgecode =  itemView.findViewById(R.id.judge_item_code);
          judgemore =  itemView.findViewById(R.id.judge_item_more);
          judgetime =  itemView.findViewById(R.id.judge_item_time);
          judgerecycle = itemView.findViewById(R.id.judge_item_recycler);
      }


  }
  public void delIndent(int i){
        mList.remove(i);
        notifyDataSetChanged();
  }
    //取消订单的接口回调
    onDelClcik monDelClcik;
    public void setOnDelClcikLisenter(onDelClcik delClcik){
        monDelClcik=delClcik;
    }
    public interface onDelClcik{
        void onDelClickLisenter(String id,int i);
    }
   //去支付的接口回调
    onPayClcik monPayClcik;
    public void setOnPayClcikLisenter(onPayClcik payClcik){
        monPayClcik=payClcik;
    }
    public interface onPayClcik{
        void onPayClickLisenter(String id,int type);
    }
    //确认收货的接口回调
    onJudegClcik monJudegClcik;
    public void setOnJudegClcikLisenter(onJudegClcik judegClcik){
          monJudegClcik = judegClcik;
    }
    public interface onJudegClcik{
        void onJudegClickLisenter(String id,int type);
    }
}
