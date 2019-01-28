package com.hc.recycler.animal.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.hc.recycler.animal.model.Animal;

public class AnimalsViewModel extends BaseObservable {

    @Bindable
    private final ObservableArrayList<AnimalViewModel> animals;

    public AnimalsViewModel() {
        this.animals = new ObservableArrayList<>();

        initData();
    }

    private void initData() {
        add("Dog", 17);
        add("Cat", 8);
        add("Elephant", 68);
    }

    public void add(String name, int age) {
        animals.add(new AnimalViewModel(new Animal(name, age)));
    }

}
