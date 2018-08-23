package com.bwie.Month_Demo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.entity.ShopBean;
import com.bwie.Month_Demo1.utils.CartAllCheckobxListen;
import com.bwie.Month_Demo1.utils.CartCheckListenr;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements CartCheckListenr {

    private Context context;
    private List<ShopBean.DataBean> cartlist;
    private CartAllCheckobxListen cartAllCheckobxListen;

    public CartAdapter(Context context, List<ShopBean.DataBean> list) {
        this.context = context;
        this.cartlist = list;
    }

    public void addPageData(List<ShopBean.DataBean> list){

        if (cartlist!=null){

            cartlist.addAll(list);

            notifyDataSetChanged();

        }

    }

    public void setCartAllCheckobxListen(CartAllCheckobxListen cartAllCheckobxListen) {
        this.cartAllCheckobxListen = cartAllCheckobxListen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ShopBean.DataBean dataBean = cartlist.get(position);

        holder.nameTv.setText(dataBean.getSellerName());

        holder.checkBox.setChecked(dataBean.isSelected());

        holder.productRv.setLayoutManager(new LinearLayoutManager(context));

        ShopAdapter shopAdapter = new ShopAdapter(context,dataBean.getList());

        holder.productRv.setAdapter(shopAdapter);

        shopAdapter.setCartCheckListenr(this);

        for (int i = 0; i < dataBean.getList().size(); i++) {

            if (dataBean.getList().get(i).isSelected()){

                holder.checkBox.setChecked(true);

            }else{

                holder.checkBox.setChecked(false);

            }

        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){

                    dataBean.setSelected(true);

                    for (int i = 0; i < dataBean.getList().size(); i++) {

                        dataBean.getList().get(i).setSelected(true);

                    }

                }else{

                    dataBean.setSelected(false);

                    for (int i = 0; i < dataBean.getList().size(); i++) {

                        dataBean.getList().get(i).setSelected(false);

                    }

                }

                notifyDataSetChanged();

                if (cartAllCheckobxListen!=null){

                    cartAllCheckobxListen.notifyAllCheckboxStatus();

                }

            }
        });

    }

    public List<ShopBean.DataBean> getCartlist() {
        return cartlist;
    }

    @Override
    public int getItemCount() {
        return cartlist.size()==0?0:cartlist.size();
    }

    @Override
    public void notifyParent() {
        notifyDataSetChanged();
        if (cartAllCheckobxListen!=null){

            cartAllCheckobxListen.notifyAllCheckboxStatus();

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView nameTv;
        private RecyclerView productRv;

        public MyViewHolder(View inflate) {
            super(inflate);
            checkBox=inflate.findViewById(R.id.select_ck);
            nameTv=inflate.findViewById(R.id.selectName_tv);
            productRv=inflate.findViewById(R.id.product_rv);
        }
    }
}
