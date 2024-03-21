package com.gfg.leaderboard.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;

    
    private String email;

    @Column(name = "score")
    private int score;

    @Column(name = "badge")
    private Set<Badge> badge= new HashSet<>();

    public User() {
        score=0;
    }

    public User(Long id, String name,int score, Set<Badge> badge, String email) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
