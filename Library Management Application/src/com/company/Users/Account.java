package com.company.Users;

public abstract class Account {

    private String ssn;
    private String name;
    private String address;
    private String telephone;
    private String username, password;

    public Account (String ssn, String name, String address, String telephone, String username, String password) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
    }

    public String getSsn () {
        return ssn;
    }

    public void setName (String name) {
        if (!name.equals("")) {
            this.name = name;
        }
    }

    public void setAddress (String address) {
        if (!address.equals("")) {
            this.address = address;
        }
    }

    public void setTelephone (String telephone) {
        if (!telephone.equals("")) {
            this.telephone = telephone;
        }
    }
}
