package com.hc.recycler.animal.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.hc.recycler.R;
import com.hc.recycler.animal.viewmodel.AnimalsViewModel;
import com.hc.recycler.databinding.ActivityAnimalBinding;

public class AnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

//        AnimalsViewModel animalsViewModel = new AnimalsViewModel();
//
//        ActivityAnimalBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_animal);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        binding.setData(animalsViewModel);
//        binding.setView(this);
    }

}
