package com.yulin.testdbspeed.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(indexes = {
        @Index(value = "number")
})
public class Student {
    @Id
    private Long id;

    private Integer age;
    private Integer number;
    @NotNull
    private String name;
    private String address;

    @Generated(hash = 1556870573)
    public Student() {
    }

    @Generated(hash = 1727847601)
    public Student(Long id, Integer age, Integer number, @NotNull String name, String address) {
        this.id = id;
        this.age = age;
        this.number = number;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
