package com.infinite.mysecurityfirst.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.infinite.mysecurityfirst.db.entity.LoginEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.infinite.mysecurityfirst.network.AppConstants.DB_NAME;


@Database(entities = {LoginEntity.class}, version = 1, exportSchema = false)

public abstract class RoomDbBuilder extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDbBuilder instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static synchronized RoomDbBuilder getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDbBuilder.class, DB_NAME)
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
