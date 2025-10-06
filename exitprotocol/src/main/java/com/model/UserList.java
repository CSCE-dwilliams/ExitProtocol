package com.model;

import java.util.ArrayList;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {}

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();

        }

        return userList;
    }

    public void loadUsers() {
        users = DataLoader.getUsers();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    // public User getUser(String username, String password) {}
    // public User getUser(String username) {}
    public void createAccount(
        String firstName,
        String lastName,
        String email,
        String password,
        String teamName,
        int avatar
    ) {}
}
