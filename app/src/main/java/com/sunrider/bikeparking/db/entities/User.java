package com.sunrider.bikeparking.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = "appuser")
public class User {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "userid")
    String userid;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "account_created_at")
    double accountCreatedAt;

    public User() {
    }

    public User(String userid, String name, double accountCreatedAt) {
        this.userid = userid;
        this.name = name;
        this.accountCreatedAt = accountCreatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAccountCreatedAt() {
        return accountCreatedAt;
    }

    public void setAccountCreatedAt(double accountCreatedAt) {
        this.accountCreatedAt = accountCreatedAt;
    }
}
