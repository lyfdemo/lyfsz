package com.bwie.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myapplication.R;
import com.bwie.myapplication.bean.ShopBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ShopBean.DataBean> data;

    public MyAdapter(Context context, List<ShopBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<ShopBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_recy, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(i);
        String images = data.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(viewHolder.send_item_image);
        viewHolder.send_item_text.setText(data.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView send_item_image;
        TextView send_item_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            send_item_image = itemView.findViewById(R.id.send_item_image);
            send_item_text = itemView.findViewById(R.id.send_item_text);
        }
    }

    public interface OnClickItem {
        void OnClickItemView(View view, int position);
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public void onClick(View view) {
        if (onClickItem != null) {
            onClickItem.OnClickItemView(view, (int) view.getTag());
        }
    }

}
