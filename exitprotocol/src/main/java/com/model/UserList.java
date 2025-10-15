package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
    }

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

<<<<<<< HEAD
    // public User getUser(String email, String password) {}
    // public User getUser(String username) {}
=======
    public User getUser(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)
                    && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }
        return null;
    }

>>>>>>> 93dba94d78315d427a0abcd8e7bb426eded62785
    public void createAccount(
            String firstName,
            String lastName,
            String email,
            String password,
            String teamName,
            int avatar,
            int score,
            UUID id) {
                //come back to this, need to determine what we want UUID length etc to look like 
                User newUser = new User(firstName, lastName, email, password, teamName, avatar,score, id);
                getUsers().add(newUser);
                DataWriter.saveUsers();
    }
}
