package com.example.jaykisaan.Model;

public class UploadPost {
    private String ProductName;
    private String ImageUrl;

    public UploadPost() {
        // empty constructor needed
    }

    public UploadPost(String productName, String imageUrl) {
        ProductName = productName;
        ImageUrl = imageUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
