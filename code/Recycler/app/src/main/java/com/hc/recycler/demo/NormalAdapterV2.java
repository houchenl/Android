package com.hc.recycler.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.recycler.BR;
import com.hc.recycler.R;
import com.hc.recycler.Util;
import com.hc.recycler.demo.model.Poem;

import java.util.List;

/**
 * Created by liulei on 2017/5/10.
 * 普通适配器
 */

class NormalAdapterV2 extends RecyclerView.Adapter<NormalViewHolderV2> {

    private List<Poem> datas;
    private OnItemClickListener mOnItemClickListener;

    NormalAdapterV2(List<Poem> datas) {
        this.datas = datas;
    }

    @Override
    public NormalViewHolderV2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false);
        return new NormalViewHolderV2(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolderV2 holder, int position) {
        final Poem poem = datas.get(position);

        holder.getTvTitle().setText(poem.getTitle());
        holder.getTvAuthor().setText(poem.getAuthor());

        final int pos = holder.getAdapterPosition();

        holder.getRootLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(pos, v);
                }
            }
        });

        holder.getRootLayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemLongClick(pos, v);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

}
