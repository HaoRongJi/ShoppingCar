package com.bwie.Month_Demo1.model;

import android.os.Handler;

import com.bwie.Month_Demo1.entity.ProductBean;
import com.bwie.Month_Demo1.utils.OKHttpUtils;
import com.bwie.Month_Demo1.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ShowModel {

    private Handler handler=new Handler();
    private ProductBean productBean;

    public void showData(HashMap<String, String> params, final ShowCallBack showCallBack) {
        OKHttpUtils.getInstance().postData("http://www.zhaoapi.cn/product/searchProducts", params, new RequestCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (200==response.code()){

                    try {
                        String result=response.body().string();
                        parseJsonResult(result,showCallBack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (showCallBack!=null){

                    showCallBack.failure("请求失败");

                }
            }
        });

    }

    private void parseJsonResult(String result, final ShowCallBack showCallBack) {
        productBean = new Gson().fromJson(result, ProductBean.class);
        final List<ProductBean.DataBean> data = productBean.getData();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (showCallBack!=null){

                    showCallBack.success(data);

                }
            }
        });
    }

    public interface  ShowCallBack{

        void failure(String msg);
        void success(List<ProductBean.DataBean> data);

    }
}
