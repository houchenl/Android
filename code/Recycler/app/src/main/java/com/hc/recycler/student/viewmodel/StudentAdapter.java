package com.hc.recycler.student.viewmodel;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hc.recycler.BR;
import com.hc.recycler.R;
import com.hc.recycler.databinding.LayoutStudentItemBinding;
import com.hc.recycler.student.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private List<Student> mListStudents;

    public StudentAdapter(List<Student> mListStudents) {
        this.mListStudents = mListStudents;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutStudentItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_student_item, parent, false);

        StudentViewHolder viewHolder = new StudentViewHolder(itemBinding.itemLayout);
        viewHolder.setItemBinding(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = mListStudents.get(position);
        holder.getItemBinding().setVariable(BR.student, student);
        holder.getItemBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mListStudents.size();
    }

}
