package com.sunrider.bikeparking.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sunrider.bikeparking.db.daos.ParkingLocationDao;
import com.sunrider.bikeparking.db.entities.LocationEntity;

@Database(entities = {LocationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "bikeparking";

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME).build();
        }

        return INSTANCE;
    }

    public abstract ParkingLocationDao parkingLocationDao();
}
