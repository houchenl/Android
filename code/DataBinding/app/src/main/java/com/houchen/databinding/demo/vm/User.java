package com.houchen.databinding.demo.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.houchen.databinding.BR;

public class User extends BaseObservable {

    private String mFirstName;
    private String mLastName;

    @Bindable
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

}
