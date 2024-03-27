package com.mkrejci.rateabeer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.sql.Timestamp;

@Entity
public class Rating {
    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne
    private Beer beer;

    private Timestamp timestamp;

    @Min(value=1, message = "Rate minimum 1 star")
    @Max(value=5, message = "Rate maximum 5 stars")
    private int stars;
    private String description;

    // == constructors ==
    public Rating(){}

    public Rating(int id, AppUser appUser, Beer beer, Timestamp timestamp,int stars, String description) {
        this.id = id;
        this.appUser = appUser;
        this.beer = beer;
        this.timestamp = timestamp;
        this.stars = stars;
        this.description = description;
    }

    // == getters & setters ==
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //== toString method ==
    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", appUser=" + appUser +
                ", beer=" + beer +
                ", timestamp=" + timestamp +
                ", stars=" + stars +
                ", description='" + description + '\'' +
                '}';
    }
}
