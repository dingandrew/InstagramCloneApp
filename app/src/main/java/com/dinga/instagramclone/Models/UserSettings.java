package com.dinga.instagramclone.Models;

public class UserSettings {
    private Users users;
    private UserAccountSettings settings;

    public UserSettings(Users users, UserAccountSettings settings) {
        this.users = users;
        this.settings = settings;
    }

    public UserSettings() {

    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UserAccountSettings getSettings() {
        return settings;
    }

    public void setSettings(UserAccountSettings settings) {
        this.settings = settings;
    }
}
