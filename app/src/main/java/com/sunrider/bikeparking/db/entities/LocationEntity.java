package com.sunrider.bikeparking.db.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = "locationentity")
public class LocationEntity {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "lat")
    double lat;

    @ColumnInfo(name = "lng")
    double lng;

    @ColumnInfo(name = "type")
    String type;

    @ColumnInfo(name = "comment")
    String comment;

    @ColumnInfo(name = "updated_at")
    String updated_at;

    @ColumnInfo(name = "userid")
    String userid;

    public LocationEntity(){

    }

    public LocationEntity(String address, double lat, double lng, String type, String comment, String updated_at, String userid) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.comment = comment;
        this.updated_at = updated_at;
        this.userid = userid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public enum LocationType {
        PARKING, SERVICING
    }
}
