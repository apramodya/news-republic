package com.pramodya.newsrepublic;
import java.io.Serializable;
public class News implements Serializable {
    String title;
    String description;
    String content;
    String image;
    public News(String title, String description, String content, String image) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
