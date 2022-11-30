package com.example.restorancepatsaji.Model;

import com.google.gson.annotations.SerializedName;

public class AppUser {

    @SerializedName("id")
    String id;

    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("role")
    String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
