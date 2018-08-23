package com.bwie.Month_Demo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.Month_Demo1.R;
import com.bwie.Month_Demo1.entity.ProductBean;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    private Context context;
    private List<ProductBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    public ShowAdapter(Context context, List<ProductBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.show_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ProductBean.DataBean product=list.get(position);
        String[] imageUrls=product.getImages().split("\\|");

        if (imageUrls!=null&&imageUrls.length>0){

            Glide.with(context).load(imageUrls[0]).into(holder.iv);

        }

        holder.tv.setText(product.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){

                    onItemClickListener.OnItemClickListener(position);

                }
            }
        });



    }

    public void loadData(List<ProductBean.DataBean> data){

        if (this.list!=null){

            this.list.addAll(data);
            notifyDataSetChanged();

        }

    }

    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);

            iv=itemView.findViewById(R.id.iv);
            tv=itemView.findViewById(R.id.tv);
        }


    }



    public interface OnItemClickListener{

        void OnItemClickListener(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
