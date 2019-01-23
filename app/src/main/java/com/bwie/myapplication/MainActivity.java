package com.bwie.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bwie.myapplication.adapter.MyAdapter;
import com.bwie.myapplication.bean.ShopBean;
import com.bwie.myapplication.utils.OkHttpUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.send_recy)
    RecyclerView sendRecy;
    private List<ShopBean.DataBean> mdata = new ArrayList<>();
    private String mUrl = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E7%AC%94%E8%AE%B0%E6%9C%AC&page=1";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String jsonStr = (String) msg.obj;
            Gson gson = new Gson();
            ShopBean shopBean = gson.fromJson(jsonStr, ShopBean.class);
            List<ShopBean.DataBean> data = shopBean.getData();
            mdata.addAll(data);
            adapter.setData(mdata);
        }
    };
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //样式
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        sendRecy.setLayoutManager(manager);
//适配器
        adapter = new MyAdapter(MainActivity.this, mdata);
        sendRecy.setAdapter(adapter);
//请求数据
        OkHttpUtil.getInter().asyncGet(mUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(1, response.body().string()));
            }
        });
        //点击事件
        adapter.setOnClickItem(new MyAdapter.OnClickItem() {
            @Override
            public void OnClickItemView(View view, int position) {
                EventBus.getDefault().postSticky(mdata.get(position).getTitle());
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
