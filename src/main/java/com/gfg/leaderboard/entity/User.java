package com.gfg.leaderboard.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

// @Entity
// @Table(name = "user")
@Document(collection = "user")
public class User {
    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "score")
    private int score;

    @Column(name = "badge")
    private Set<Badge> badge; 

    public User() {
        score=0;
        badge=new HashSet<>();
    }

    public User(Long id, String name,String email,int score, Set<Badge> badge) {
       
        this.id=id;
        this.name = name;
        this.score=score;
        this.badge = badge;
        this.email=email;
    }

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Badge> getBadge() {
        return badge;
    }

    public void setBadge(Set<Badge> badge) {
        this.badge = badge;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", badge=" + badge +
                ", score=" + score +
                '}';
    }
}
