package com.sunrider.bikeparking.db.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = "parkinglocation")
public class LocationEntity {

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "lat")
    double lat;

    @ColumnInfo(name = "lng")
    double lng;

    @ColumnInfo(name = "comment")
    String comment;

    public LocationEntity(String address, double lat, double lng, String comment) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.comment = comment;
    }

    @Ignore
    public LocationEntity() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
