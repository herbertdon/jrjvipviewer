package com.dondavid.jrjvip.pojo;

/**
 * Created by herbert.tang on 12/16/2016.
 */
public class DataModel {
    private int idx;
    private int id;
    private String content;
    private String time;
    private String image;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return getTime() +" : " +getContent() + " : " + getImage();
    }
}
