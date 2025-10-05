package com.model;

public class UserList {
    private static UserList userList;

    private UserList() {}

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }

        return userList;
    }

    public User getUser(String username, String password) {}
    public User getUser(String username) {}
    public void createAccount(
        String firstName,
        String lastName,
        String email,
        String password,
        String teamName,
        int avatar
    ) {}
}
