package com.yulin.messagepack;

/**
 * Created by liulei0905 on 2016/11/15.
 *
 */

public class Student {

    public int mAge;
    public String mName;
    public String mCountry;

    public Student() {
    }

    public Student(int mAge, String mName, String mCountry) {
        this.mAge = mAge;
        this.mName = mName;
        this.mCountry = mCountry;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

}
