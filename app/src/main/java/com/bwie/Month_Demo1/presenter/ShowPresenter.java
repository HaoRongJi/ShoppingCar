package com.bwie.Month_Demo1.presenter;

import com.bwie.Month_Demo1.entity.ProductBean;
import com.bwie.Month_Demo1.model.ShowModel;
import com.bwie.Month_Demo1.view.IshowView;

import java.util.HashMap;
import java.util.List;

public class ShowPresenter {

    private ShowModel showModel;
    private IshowView ishowView;



    public ShowPresenter(IshowView ishowView) {
        this.showModel = new ShowModel();
        attachView(ishowView);
    }

    public void attachView(IshowView ishowView){

        this.ishowView=ishowView;

    }

    public void showData(HashMap<String, String> params) {
        showModel.showData(params, new ShowModel.ShowCallBack() {
            @Override
            public void failure(String msg) {
                ishowView.failure(msg);
            }

            @Override
            public void success(List<ProductBean.DataBean> data) {
                ishowView.success(data);
            }
        });
    }

    public void detachView(){

        this.ishowView=null;

    }
}
