package com.sunrider.bikeparking.db.daos;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sunrider.bikeparking.db.entities.LocationEntity;

import java.util.List;

@Dao
public interface ParkingLocationDao {

    @Insert
    long insertParkingLocation(LocationEntity locationEntity);

    @Delete
    int deleteParkingLocation(LocationEntity locationEntity);

    @Query("SELECT * FROM  locationentity")
    List<LocationEntity> getAllParkingLocation();
}
