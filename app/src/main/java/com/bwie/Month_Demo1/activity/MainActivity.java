package com.bwie.Month_Demo1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.Month_Demo1.R;
import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText searchEt;
    private AutoFlowLayout conFl1;
    private AutoFlowLayout conFl2;
    private ArrayList<String> content;
    private ArrayList<String> content2;
    private TextView tjText;
    private Button searchBtn;
    private ImageView backImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        initView();
        initData();
    }

    private void initData() {
        content2 = new ArrayList<>();
        content2.add("aaa");
        content2.add("bbb");
        content2.add("ccc");
        auto2();
        content = new ArrayList<>();
        searchBtn.setOnClickListener(this);
        backImg.setOnClickListener(this);

    }

    private void auto2() {
        conFl2.setAdapter(new FlowAdapter(content2) {
            @Override
            public View getView(int i) {
                View view = View.inflate(MainActivity.this, R.layout.text_layout, null);
                tjText = view.findViewById(R.id.tj_text);
                tjText.setText(content2.get(i));
                tjText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = tjText.getText().toString();
                        Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                        intent.putExtra("s1",s);
                        startActivity(intent);
                    }
                });
                return view;
            }
        });
    }

    private void initView() {
        searchBtn = findViewById(R.id.search_btn);
        searchEt = findViewById(R.id.search_et);
        conFl1 = findViewById(R.id.con_fl1);
        conFl2 = findViewById(R.id.con_fl2);
        backImg = findViewById(R.id.back_img);

    }


    private void auto() {
        conFl1.setAdapter(new FlowAdapter(content) {
            @Override
            public View getView(int i) {
                View view = View.inflate(MainActivity.this, R.layout.text_layout, null);
                tjText = view.findViewById(R.id.tj_text);
                tjText.setText(content.get(i));
                tjText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = tjText.getText().toString();
                        //String s1 = searchEt.getText().toString();
                        Toast.makeText(MainActivity.this, s + "", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                        intent.putExtra("s1",s);
                        startActivity(intent);
                    }
                });
                return view;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.search_btn:

                String s1 = searchEt.getText().toString();
                if (s1 != null && !s1.equals("")) {

                    content.add(s1);
                    auto();
                    content.clear();
                    Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                    intent.putExtra("s1",s1);
                    startActivity(intent);

                } else {

                    Toast.makeText(this, "请在输入框输入您想搜索的商品", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.back_img:

                finish();

                break;

            default:

                break;


        }
    }
}
