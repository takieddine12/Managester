package com.manager.managester.employee.employeemodel;

public class ProductsModel {

    private String productimage;
    private String producticon;
    private String productname;
    private String brand;
    private String number;
    private String productiondate;
    private String productionexpiry;

    public ProductsModel(){
        //empty Constructor
    }

    public ProductsModel(String productimage, String producticon, String productname, String brand, String number, String productiondate, String productionexpiry) {
        this.productimage = productimage;
        this.producticon = producticon;
        this.productname = productname;
        this.brand = brand;
        this.number = number;
        this.productiondate = productiondate;
        this.productionexpiry = productionexpiry;
    }

    public ProductsModel(String productname, String brand, String number, String productiondate, String productionexpiry) {
        this.productname = productname;
        this.brand = brand;
        this.number = number;
        this.productiondate = productiondate;
        this.productionexpiry = productionexpiry;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProducticon() {
        return producticon;
    }

    public void setProducticon(String producticon) {
        this.producticon = producticon;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public String getProductionexpiry() {
        return productionexpiry;
    }

    public void setProductionexpiry(String productionexpiry) {
        this.productionexpiry = productionexpiry;
    }
}
