package com.example.jaykisaan.Model;

public class AddProduct {
    private String product_name;
    private String product_quantity;
    private String product_price;
    private String address1;
    private String address2;
    private String hometown;
    private String state;
    private String mobileno;
    private String product_image;

    public AddProduct() {
    }


    public AddProduct(String product_name, String product_quantity, String product_price, String address1, String address2, String hometown, String state, String mobileno, String product_image) {
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.address1 = address1;
        this.address2 = address2;
        this.hometown = hometown;
        this.state = state;
        this.mobileno = mobileno;
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
