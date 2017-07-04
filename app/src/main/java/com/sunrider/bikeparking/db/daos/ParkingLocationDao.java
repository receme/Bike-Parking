package com.sunrider.bikeparking.db.daos;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;

import java.util.List;

@Dao
public interface ParkingLocationDao {

    @Insert
    long insertParkingLocation(ParkingLocationEntity parkingLocationEntity);

    @Delete
    int deleteParkingLocation(ParkingLocationEntity parkingLocationEntity);

    @Query("SELECT * FROM  parkinglocation")
    List<ParkingLocationEntity> getAllParkingLocation();
}
