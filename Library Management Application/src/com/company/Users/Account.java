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
}
