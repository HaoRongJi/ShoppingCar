package com.bwie.Month_Demo1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.adapter.ShowAdapter;
import com.bwie.Month_Demo1.entity.ProductBean;
import com.bwie.Month_Demo1.presenter.ShowPresenter;
import com.bwie.Month_Demo1.view.IshowView;

import java.util.HashMap;
import java.util.List;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener,IshowView {

    private RecyclerView searchRv;
    private ImageView classesImg;
    private ImageView back1Img;
    private String content;
    private ShowPresenter showPresenter;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        content = intent.getStringExtra("s1");
        showPresenter=new ShowPresenter(this);
        HashMap<String,String> params=new HashMap<>();
        params.put("keywords",content+"");
        showPresenter.showData(params);



        back1Img.setOnClickListener(this);



    }

    private void initView() {
        searchRv = findViewById(R.id.search_rv);
        classesImg = findViewById(R.id.classes_img);
        back1Img = findViewById(R.id.back1_img);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back1_img:
                finish();
                break;

            case R.id.classes_img:
                if (i%2==0){
                    searchRv.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                    i++;
                }else{
                    searchRv.setLayoutManager(new GridLayoutManager(ShowActivity.this,2));
                    i++;
                }
                break;

            default:

                break;

        }

    }

    @Override
    public void success(final List<ProductBean.DataBean> productBean) {
        searchRv.setLayoutManager(new LinearLayoutManager(ShowActivity.this));

        ShowAdapter showAdapter = new ShowAdapter(ShowActivity.this,productBean);
        searchRv.setAdapter(showAdapter);
        showAdapter.setOnItemClickListener(new ShowAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                String detailUrl = productBean.get(position).getDetailUrl();
                int pid = productBean.get(position).getPid();
                Intent intent = new Intent(ShowActivity.this,DetailsActivity.class);
                intent.putExtra("detailUrl",detailUrl);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });


    }

    @Override
    public void failure(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (showPresenter!=null){

            showPresenter.detachView();

        }
    }
}
