package com.yulin.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Entity mapped to table "STUDENT".
 */
@Entity(indexes = {
        @Index(value = "name", unique = true)
})
public class Student {

    @Id
    private Long id;

    @NotNull
    private String name;
    private String country;
    private int age;

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Student(Long id) {
        this.id = id;
    }

    @Generated(hash = 1961659134)
    public Student(Long id, @NotNull String name, String country, int age) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
