package com.yulin.list.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yulin.list.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_lei on 2017/7/20.
 */

public class GridActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> insertData = new ArrayList<>();

    private List<String> mData = new ArrayList<>();

    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        initDate();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    private void initDate() {
        for (int i = 'A'; i <= 'z'; i++) {
            mData.add("" + (char)i);
        }

        insertData.add("how");
        insertData.add("are");
        insertData.add("you");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                adapter.add(1);
                break;
            case R.id.btn_delete:
                adapter.delete(1);
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tvText.setText(mData.get(position));
            holder.tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(GridActivity.this, mData.get(position) + " " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public void add(int pos) {
            mData.addAll(pos, insertData);
            notifyItemRangeInserted(pos, insertData.size());
        }

        public void delete(int pos) {
            mData.removeAll(insertData);
            notifyItemRangeRemoved(pos, insertData.size());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvText;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
