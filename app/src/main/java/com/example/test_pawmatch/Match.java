package com.example.pawmatch.models;

import com.google.firebase.firestore.Exclude;
import java.util.Date;

public class Match {
    private String id;
    private String pet1Id;
    private String pet2Id;
    private String pet1OwnerId;
    private String pet2OwnerId;
    private Date matchDate;
    private boolean isActive;
    private Date lastMessageDate;
    private String lastMessage;
    private boolean isRead;

    // Required empty constructor for Firestore
    public Match() {}

    public Match(String pet1Id, String pet2Id, String pet1OwnerId, String pet2OwnerId) {
        this.pet1Id = pet1Id;
        this.pet2Id = pet2Id;
        this.pet1OwnerId = pet1OwnerId;
        this.pet2OwnerId = pet2OwnerId;
        this.matchDate = new Date();
        this.isActive = true;
        this.isRead = false;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPet1Id() {
        return pet1Id;
    }

    public void setPet1Id(String pet1Id) {
        this.pet1Id = pet1Id;
    }

    public String getPet2Id() {
        return pet2Id;
    }

    public void setPet2Id(String pet2Id) {
        this.pet2Id = pet2Id;
    }

    public String getPet1OwnerId() {
        return pet1OwnerId;
    }

    public void setPet1OwnerId(String pet1OwnerId) {
        this.pet1OwnerId = pet1OwnerId;
    }

    public String getPet2OwnerId() {
        return pet2OwnerId;
    }

    public void setPet2OwnerId(String pet2OwnerId) {
        this.pet2OwnerId = pet2OwnerId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}