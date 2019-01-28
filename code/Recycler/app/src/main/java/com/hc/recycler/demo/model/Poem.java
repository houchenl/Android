package com.hc.recycler.demo.model;

/**
 * Created by liulei on 2017/5/10.
 * 存储一条诗词信息
 */

public class Poem {

    private String title;
    private String author;

    public Poem() {
    }

    public Poem(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Poem{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

}
