package com.bwie.Month_Demo1.presenter;

import com.bwie.Month_Demo1.entity.ShopBean;
import com.bwie.Month_Demo1.model.ShopModel;
import com.bwie.Month_Demo1.view.IShopView;

import java.util.HashMap;

public class CartPresenter {

    private IShopView iShopView;
    private ShopModel shopModel;

    public CartPresenter(IShopView iShopView) {
        attachView(iShopView);
        this.shopModel = new ShopModel();
    }

    public void attachView(IShopView iShopView){

        this.iShopView = iShopView;

    }

    public void shopData(HashMap<String, String> params) {

        shopModel.shopData(params, new ShopModel.ShopCallBack() {
            @Override
            public void success(ShopBean shopBean) {
                iShopView.onSuccess(shopBean);
            }

            @Override
            public void failure(String Msg) {
                iShopView.onFailure(Msg);
            }
        });

    }

    public void detachView(){

        if (iShopView!=null){

            this.iShopView=null;

        }

    }
}
