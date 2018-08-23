package com.bwie.Month_Demo1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.common.Api;
import com.bwie.Month_Demo1.entity.InsertBean;
import com.bwie.Month_Demo1.utils.OKHttpUtils;
import com.bwie.Month_Demo1.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView dtBackImg;
    private WebView productWv;
    private TextView inCart;
    private TextView addCart;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String detailUrl = intent.getStringExtra("detailUrl");
        pid = intent.getIntExtra("pid",0);
        productWv.loadUrl(detailUrl);
        dtBackImg.setOnClickListener(this);
        inCart.setOnClickListener(this);
        addCart.setOnClickListener(this);
    }

    private void initView() {
        dtBackImg = findViewById(R.id.dt_back_img);
        productWv = findViewById(R.id.product_wv);
        inCart = findViewById(R.id.inCart);
        addCart= findViewById(R.id.addCart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.dt_back_img:

                finish();

                break;

            case R.id.inCart:

                Intent intent = new Intent(DetailsActivity.this, CartActivity.class);
                startActivity(intent);

                break;

            case R.id.addCart:

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("uid","17224");
                hashMap.put("pid",pid+"");

                OKHttpUtils.getInstance().postData(Api.ADDGOUWUCHE, hashMap, new RequestCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (200==response.code()){

                            try {
                                String result = response.body().string();
                                parseJsonResult(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });

                break;

            default:

                break;



        }
    }

    private void parseJsonResult(String result) {
        InsertBean insertBean = new Gson().fromJson(result, InsertBean.class);
        if (insertBean.getCode().equals("0")){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DetailsActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
