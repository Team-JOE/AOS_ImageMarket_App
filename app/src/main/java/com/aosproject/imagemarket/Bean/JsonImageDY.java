package com.aosproject.imagemarket.Bean;

public class JsonImageDY {
    int imageCode;
    String imageFilepath;

    public JsonImageDY(int imageCode, String imageFilepath) {
        this.imageCode = imageCode;
        this.imageFilepath = imageFilepath;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }

    public String getImageFilepath() {
        return imageFilepath;
    }

    public void setImageFilepath(String imageFilepath) {
        this.imageFilepath = imageFilepath;
    }
}
