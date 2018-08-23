package com.bwie.Month_Demo1.view;

import com.bwie.Month_Demo1.entity.ShopBean;

public interface IShopView {

    void onSuccess(ShopBean shopBean);
    void onFailure(String Msg);

}
