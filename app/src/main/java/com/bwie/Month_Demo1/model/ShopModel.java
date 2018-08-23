package com.bwie.Month_Demo1.model;

import android.os.Handler;

import com.bwie.Month_Demo1.common.Api;
import com.bwie.Month_Demo1.entity.ShopBean;
import com.bwie.Month_Demo1.utils.OKHttpUtils;
import com.bwie.Month_Demo1.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class ShopModel {

    private Handler handler = new Handler();
    private ShopBean shopBean;


    public void shopData(HashMap<String, String> params, final ShopCallBack shopCallBack) {
        OKHttpUtils.getInstance().postData(Api.CHAGOUWUCHE, params, new RequestCallback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (200 == response.code()) {
                    String result = null;
                    try {
                        result = response.body().string();
                        parseJsonResult(result,shopCallBack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (shopCallBack != null) {
                    shopCallBack.failure("请求失败");
                }
            }
        });
    }

    private void parseJsonResult(String result, final ShopCallBack shopCallBack) {
        shopBean = new Gson().fromJson(result, ShopBean.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (shopCallBack!=null){

                    shopCallBack.success(shopBean);

                }
            }
        });
    }

    public interface ShopCallBack {

        void success(ShopBean shopBean);

        void failure(String Msg);

    }
}
