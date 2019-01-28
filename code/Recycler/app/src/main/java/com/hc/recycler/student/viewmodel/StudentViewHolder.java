package com.hc.recycler.student.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hc.recycler.databinding.LayoutStudentItemBinding;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    private LayoutStudentItemBinding itemBinding;

    public StudentViewHolder(View itemView) {
        super(itemView);
    }

    public LayoutStudentItemBinding getItemBinding() {
        return itemBinding;
    }

    public void setItemBinding(LayoutStudentItemBinding itemBinding) {
        this.itemBinding = itemBinding;
    }

}
