package com.yulin.viewpager.image;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageGridViewHolder> {

    private List<String> urls;
    private Context context;

    private OnItemClickListener mOnClickListener;

    public ImageGridAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    // 只执行一次
    @NonNull
    @Override
    public ImageGridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, viewGroup, false);
        return new ImageGridViewHolder(view);
    }

    // 每个item执行一次
    @Override
    public void onBindViewHolder(@NonNull final ImageGridViewHolder holder, int i) {
        Glide.with(context).load(urls.get(i)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null)
                    mOnClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls != null ? urls.size() : 0;
    }

    public static class ImageGridViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageGridViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }

    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
