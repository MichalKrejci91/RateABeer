package com.mkrejci.rateabeer.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class AppUser {
    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @OneToMany(mappedBy = "appUser")
    private List<Rating> ratings;

    // == constructors ==
    public AppUser() {}
    public AppUser(int id, String username, List<Rating> ratings) {
        this.id = id;
        this.username = username;
        this.ratings = ratings;
    }

    // == getters & setters ==
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    // == toString method ==
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
