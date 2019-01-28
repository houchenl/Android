package com.hc.recycler.friends.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hc.recycler.R;
import com.hc.recycler.friends.model.Person;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements OnRecyclerViewListener {

    private List<Person> mListPersons = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        initData();

        PersonAdapter adapter = new PersonAdapter(mListPersons);
        adapter.setRecyclerViewListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        mListPersons.add(new Person("aaa", 21));
        mListPersons.add(new Person("bbb", 21));
        mListPersons.add(new Person("ccc", 21));
        mListPersons.add(new Person("ddd", 21));
        mListPersons.add(new Person("eee", 21));
        mListPersons.add(new Person("fff", 21));
        mListPersons.add(new Person("ggg", 21));
        mListPersons.add(new Person("hhh", 21));
        mListPersons.add(new Person("iii", 21));
        mListPersons.add(new Person("jjj", 21));
        mListPersons.add(new Person("kkk", 21));
        mListPersons.add(new Person("lll", 21));
        mListPersons.add(new Person("mmm", 21));
        mListPersons.add(new Person("nnn", 21));
        mListPersons.add(new Person("ooo", 21));
        mListPersons.add(new Person("ppp", 21));
        mListPersons.add(new Person("qqq", 21));
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

}
