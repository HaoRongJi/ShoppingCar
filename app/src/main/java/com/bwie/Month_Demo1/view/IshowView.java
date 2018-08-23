package com.bwie.Month_Demo1.view;

import com.bwie.Month_Demo1.entity.ProductBean;

import java.util.List;

public interface IshowView {

    void success(List<ProductBean.DataBean> productBean);
    void failure(String msg);
}
