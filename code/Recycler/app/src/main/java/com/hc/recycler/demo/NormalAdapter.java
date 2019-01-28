package com.hc.recycler.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.recycler.BR;
import com.hc.recycler.R;
import com.hc.recycler.databinding.ItemNormalBinding;
import com.hc.recycler.demo.model.Poem;

import java.util.List;

/**
 * Created by liulei on 2017/5/10.
 * 普通适配器
 */

class NormalAdapter extends RecyclerView.Adapter<NormalViewHolder> {

    private List<Poem> datas;
    private OnItemClickListener mOnItemClickListener;

    NormalAdapter(List<Poem> datas) {
        this.datas = datas;
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        final Poem poem = datas.get(position);

        holder.getBinding().setVariable(BR.poem, poem);
        holder.getBinding().executePendingBindings();

        holder.setPosition(position);
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.registClick(holder.getBinding().getRoot());
        holder.registLongClick(holder.getBinding().getRoot());
        holder.registClick(((ItemNormalBinding)holder.getBinding()).tvClick);
        holder.registLongClick(((ItemNormalBinding)holder.getBinding()).tvClick);
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

}
