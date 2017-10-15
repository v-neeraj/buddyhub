package com.example.neeraj.buddyhub;

/**
 * Created by ankitgarg on 14/10/17.
 */

public class Image_Fragment_Object {
    private int imageId;
    private String imageName;
    public Image_Fragment_Object(int imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
