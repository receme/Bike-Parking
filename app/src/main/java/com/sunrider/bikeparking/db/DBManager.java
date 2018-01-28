package com.sunrider.bikeparking.db;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.sunrider.bikeparking.db.entities.LocationEntity;

import java.util.List;

public class DBManager implements DatabaseService {


    private static DBManager instance;
    private AppDatabase appDatabase;

    public static DBManager getInstance(Context context) {

        if (instance == null) {
            instance = new DBManager(context);
        }
        return instance;
    }

    private DBManager(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
    }


    @Override
    public long insertParkingLocation(LocationEntity locationEntity) {
        return appDatabase.parkingLocationDao().insertParkingLocation(locationEntity);
    }

    @Override
    public int deleteParkingLocation(LocationEntity locationEntity) {
        return appDatabase.parkingLocationDao().deleteParkingLocation(locationEntity);
    }

    @Override
    public List<LocationEntity> getAllParkingLocation() {
        return appDatabase.parkingLocationDao().getAllParkingLocation();
    }
}
