package com.manager.managester.employee.employeemodel;

public class NotificationModel {

    private String ImageUrl;
    private String productIcon;
    private String productName;
    private String productMessage;


    public NotificationModel(String imageUrl, String productIcon, String productName, String productMessage) {
        ImageUrl = imageUrl;
        this.productIcon = productIcon;
        this.productName = productName;
        this.productMessage = productMessage;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductMessage() {
        return productMessage;
    }

    public void setProductMessage(String productMessage) {
        this.productMessage = productMessage;
    }
}
