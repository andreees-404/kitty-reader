package com.cutedomain.kittyreader.models;

public class EBookJTest {
    private String author;
    private String title;


    public EBookJTest(String author, String title){
        this.author = author;
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
