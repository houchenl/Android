package com.hc.recycler.animal.viewmodel;

import android.databinding.BaseObservable;

import com.hc.recycler.animal.model.Animal;

public class AnimalViewModel extends BaseObservable {

    private final Animal animal;

    public AnimalViewModel(Animal animal) {
        this.animal = animal;
    }

    public Animal getModel() {
        return animal;
    }

    public String getName() {
        return animal.getName();
    }

    public int getAge() {
        return animal.getAge();
    }

}
