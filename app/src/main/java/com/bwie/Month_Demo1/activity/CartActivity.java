package com.bwie.Month_Demo1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.adapter.CartAdapter;
import com.bwie.Month_Demo1.entity.ShopBean;
import com.bwie.Month_Demo1.presenter.CartPresenter;
import com.bwie.Month_Demo1.utils.CartAllCheckobxListen;
import com.bwie.Month_Demo1.view.IShopView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, IShopView, CartAllCheckobxListen {

    private XRecyclerView cartdetailsXv;
    private CheckBox selectallCk;
    private TextView totalprice_Tv;
    private ImageView cartBackImg;
    private int page = 1;
    private CartPresenter cartPresenter;
    private List<ShopBean.DataBean> list;
    private CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();

    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {

        loadData();

        cartBackImg.setOnClickListener(this);

        cartdetailsXv.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        cartdetailsXv.setLoadingMoreEnabled(true);

        cartdetailsXv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });

        selectallCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectallCk.isChecked()) {
                    if (list!=null&&list.size()>0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelected(true);
                            for (int j = 0; j < list.get(i).getList().size(); j++) {
                                list.get(i).getList().get(j).setSelected(true);
                            }
                        }
                    }
                }else {
                    if (list!=null&&list.size()>0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelected(false);
                            for (int j = 0; j < list.get(i).getList().size(); j++) {
                                list.get(i).getList().get(j).setSelected(false);
                            }
                        }
                    }
                }

                cartAdapter.notifyDataSetChanged();//全部刷新


                totalPrice();
            }

        });



    }

    private void loadData() {
        //cartBackImg.setOnClickListener(this);
        HashMap<String ,String> params=new HashMap<>();
        params.put("uid","17224");
        params.put("page",page+"");
        cartPresenter = new CartPresenter(this);
        cartPresenter.shopData(params);
    }

    private void initView() {
        cartdetailsXv = findViewById(R.id.cartdetails_xv);
        selectallCk = findViewById(R.id.selectall_ck);
        totalprice_Tv = findViewById(R.id.totalprice_tv);
        cartBackImg = findViewById(R.id.cart_back_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.cart_back_img:

                finish();

                break;

            default:

                break;

        }
    }

    @Override
    public void onSuccess(ShopBean shopBean) {
        if (shopBean!=null){

            if (page==1){

                list = shopBean.getData();
                cartAdapter = new CartAdapter(this,list);
                cartdetailsXv.setLayoutManager(new LinearLayoutManager(this));
                cartdetailsXv.setAdapter(cartAdapter);
                cartdetailsXv.refreshComplete();
            }else{

                if (cartAdapter!=null){

                    cartAdapter.addPageData(shopBean.getData());

                }

                cartdetailsXv.loadMoreComplete();

            }

            // TODO: 2018/8/23
            cartAdapter.setCartAllCheckobxListen(this);

        }

    }

    @Override
    public void onFailure(String Msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cartPresenter!=null){

            cartPresenter.detachView();

        }
    }

    @Override
    public void notifyAllCheckboxStatus() {
        StringBuilder stringBuilder=new StringBuilder();
        if (cartAdapter != null) {
            for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {

                stringBuilder.append(cartAdapter.getCartlist().get(i).isSelected());

                for (int i1 = 0; i1 < cartAdapter.getCartlist().get(i).getList().size(); i1++) {

                    stringBuilder.append(cartAdapter.getCartlist().get(i).getList().get(i1).isSelected());

                }
            }
        }

        if (stringBuilder.toString().contains("false")) {
            selectallCk.setChecked(false);

//            totalPrice = 0;
        } else {
            selectallCk.setChecked(true);
        }

        totalPrice();//计算总价
    }

    private void totalPrice() {

        double totalPrice=0;
        for (int i = 0; i < cartAdapter.getCartlist().size(); i++) {
            for (int j = 0; j <cartAdapter.getCartlist().get(i).getList().size(); j++) {
                //计算总价的关键代码块
                if (cartAdapter.getCartlist().get(i).getList().get(j).isSelected()) {
                    ShopBean.DataBean.ListBean listBean = cartAdapter.getCartlist().get(i).getList().get(j);
                    double p = listBean.getBargainPrice();
                    totalPrice+=p * listBean.getTotalNum();
                }

            }
        }
        totalprice_Tv.setText("总价:￥"+totalPrice);
    }
}
