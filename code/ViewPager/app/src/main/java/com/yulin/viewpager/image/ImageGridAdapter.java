package com.yulin.viewpager.image;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yulin.viewpager.R;
import com.yulin.viewpager.Tool;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageGridViewHolder> {

    private static final String TAG = "houchenl-GridAdapter";

    private List<String> urls;
    private Activity context;

    private OnItemClickListener mOnClickListener;

    ImageGridAdapter(Activity activity, List<String> urls) {
        this.context = activity;
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
        int resizeWidth = Tool.getScreenWidth(context) / 3;
        String url = urls.get(i) + "?resize=" + resizeWidth + ":x";
        Glide.with(context).load(url).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = holder.root.getX();
                float y = holder.root.getY();
                int width = holder.root.getWidth();
                int height = holder.root.getHeight();
                Log.d(TAG, "onClick: x " + x + ", y " + y + ", width " + width + ", height " + height);

                if (mOnClickListener != null)
                    mOnClickListener.onItemClick(holder.getAdapterPosition(), x, y, width, height);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls != null ? urls.size() : 0;
    }

    static class ImageGridViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        View root;

        ImageGridViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            imageView = itemView.findViewById(R.id.item_image);
        }

    }

    void setOnClickListener(OnItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnItemClickListener {
        /**
         * @param x      点击item的x
         * @param y      点击item的y
         * @param width  点击item的width
         * @param height 点击item的height
         */
        void onItemClick(int position, float x, float y, int width, int height);
    }

}
