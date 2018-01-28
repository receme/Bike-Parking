package com.sunrider.bikeparking.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sunrider.bikeparking.db.daos.ParkingLocationDao;
import com.sunrider.bikeparking.db.entities.LocationEntity;

@Database(entities = {LocationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "bikeparking";

    public abstract ParkingLocationDao parkingLocationDao();
}
