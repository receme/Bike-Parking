package com.sunrider.bikeparking.db.daos;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sunrider.bikeparking.db.entities.LocationEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ParkingLocationDao {

    @Insert(onConflict = REPLACE)
    long insertParkingLocation(LocationEntity locationEntity);

    @Delete
    int deleteParkingLocation(LocationEntity locationEntity);

    @Query("SELECT * FROM  locationentity")
    List<LocationEntity> getAllParkingLocation();
}
