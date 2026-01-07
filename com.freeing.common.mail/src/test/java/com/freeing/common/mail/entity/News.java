package com.freeing.common.mail.entity;

public class News {
    private String title;
    private String content;
    private String author;
    private String publishTime;
    private String category;

    public News(String title, String content, String author, String publishTime, String category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishTime = publishTime;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
