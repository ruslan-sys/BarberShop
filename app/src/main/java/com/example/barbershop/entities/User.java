package com.example.barbershop.entities;

import android.net.Uri;

public class User {
    private final Uri picture;
    private final String firstName;
    private final String lastName;
    private final String name;
    private final String id;
    private final String email;
    private final String permissions;

    public User(Uri picture, String firstName,String lastName,
                String id, String email, String permissions) {
        this.picture = picture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.id = id;
        this.email = email;
        this.permissions = permissions;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPermissions() {
        return permissions;
    }
}