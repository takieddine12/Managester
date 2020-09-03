package com.manager.managester.employee.employeemodel;

import com.google.gson.annotations.SerializedName;

public class EmployeeModel {

    @SerializedName(("name"))
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phonenumber")
    private String phonenumber;
    @SerializedName("companycode")
    private String companycode;
    @SerializedName("id")
    private String id;

    public EmployeeModel(){

        ///Empty Constructor
    }

    public EmployeeModel(String name, String email, String password, String phonenumber, String companycode, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.companycode = companycode;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
