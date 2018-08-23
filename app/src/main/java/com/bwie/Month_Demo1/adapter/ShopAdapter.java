package com.bwie.Month_Demo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.entity.ShopBean;
import com.bwie.Month_Demo1.utils.CartAllCheckobxListen;
import com.bwie.Month_Demo1.utils.CartCheckListenr;
import com.bwie.Month_Demo1.widget.MyJiaJianView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class ShopAdapter extends XRecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    private Context context;
    private List<ShopBean.DataBean.ListBean> list;
    private CartCheckListenr cartCheckListenr;
    private CartAllCheckobxListen cartAllCheckobxListen;

    public ShopAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setCartCheckListenr(CartCheckListenr cartCheckListenr) {
        this.cartCheckListenr = cartCheckListenr;
    }

    public void setCartAllCheckobxListen(CartAllCheckobxListen cartAllCheckobxListen) {
        this.cartAllCheckobxListen = cartAllCheckobxListen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sonproduct_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ShopBean.DataBean.ListBean listBean = list.get(position);

        holder.priceTv.setText("优惠价：¥" + listBean.getBargainPrice());
        holder.titleTv.setText(listBean.getTitle());
        String[] split = listBean.getImages().split("\\|");
        if (split!=null&&split.length>0){

            Glide.with(context).load(split[0]).into(holder.productIv);

        }else{

            holder.productIv.setImageResource(R.mipmap.ic_launcher);

        }

        holder.checkBox.setChecked(listBean.isSelected());

        holder.myJIaJianView.setNumEt(listBean.getTotalNum());

        holder.myJIaJianView.setJiaJianListener(new MyJiaJianView.JiaJianListener() {
            @Override
            public void getNum(int num) {
                listBean.setTotalNum(num);
                if (cartCheckListenr!=null){

                    cartCheckListenr.notifyParent();

                }

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){

                    listBean.setSelected(true);

                }else {

                    listBean.setSelected(false);

                }

                if (cartCheckListenr!=null){

                    cartCheckListenr.notifyParent();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView titleTv, priceTv;
        private ImageView productIv;
        private MyJiaJianView myJIaJianView;

        public MyViewHolder(View inflate) {
            super(inflate);
            checkBox = itemView.findViewById(R.id.productCheckbox);
            titleTv = itemView.findViewById(R.id.title);
            priceTv = itemView.findViewById(R.id.price);
            productIv = itemView.findViewById(R.id.product_icon);
            myJIaJianView = itemView.findViewById(R.id.jiajianqi);
        }
    }
}
