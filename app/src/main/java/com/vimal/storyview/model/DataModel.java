package com.vimal.storyview.model;

public class DataModel {

    private String type;
    private String imgUrl;
    private String title;
    private String content;

    public DataModel(String type, String title, String content, String imgUrl) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}