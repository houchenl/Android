package com.hc.recycler.friends.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hc.recycler.R;

public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private View rootView;
    private TextView tvName, tvAge;
    private OnRecyclerViewListener listener;

    public PersonViewHolder(View itemView, OnRecyclerViewListener listener) {
        super(itemView);
        this.listener = listener;

        rootView = itemView.findViewById(R.id.item_layout);
        tvName = (TextView) itemView.findViewById(R.id.item_tv1);
        tvAge = (TextView) itemView.findViewById(R.id.item_tv2);

        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvAge() {
        return tvAge;
    }

    @Override
    public void onClick(View view) {
        // getAdapterPosition() == getLayoutPosition() == position
        if (listener != null)
            listener.onItemClick(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        if (listener != null)
            return listener.onItemLongClick(getAdapterPosition());
        else
            return false;
    }

}
