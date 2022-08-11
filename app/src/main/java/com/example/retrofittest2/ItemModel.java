package com.example.retrofittest2;

public class ItemModel {
    int id;
    String author;
    int width;
    int height;
    String url;
    String download_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", url='" + url + '\'' +
                ", download_url='" + download_url + '\'' +
                '}';
    }
}
