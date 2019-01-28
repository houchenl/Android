package com.hc.recycler.friends.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.recycler.R;
import com.hc.recycler.friends.model.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private OnRecyclerViewListener mOnRecyclerViewListener;

    private List<Person> listPersons;

    public PersonAdapter(List<Person> listPersons) {
        this.listPersons = listPersons;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, null, false);
        return new PersonViewHolder(view, mOnRecyclerViewListener);
    }

    @Override
    public int getItemCount() {
        return listPersons == null ? 0 : listPersons.size();
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person person = listPersons.get(position);
        holder.getTvName().setText(person.getName());
        holder.getTvAge().setText(String.valueOf(person.getAge()));
    }

    public void setRecyclerViewListener(OnRecyclerViewListener mOnRecyclerViewListener) {
        this.mOnRecyclerViewListener = mOnRecyclerViewListener;
    }

}
