package com.example.cursework2_2;

public class User {
    private int _id;
    private String user_login;
    private String user_password;
    private String user_role;

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public User(int _id, String user_login, String user_role) {
        this._id = _id;
        this.user_login = user_login;
        this.user_role = user_role;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
