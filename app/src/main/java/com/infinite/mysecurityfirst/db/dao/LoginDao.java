package com.infinite.mysecurityfirst.db.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.infinite.mysecurityfirst.db.entity.LoginEntity;

import java.util.List;

/**
 * Created by Rajini Ragupathy on 05-06-2020.
 */
public interface LoginDao {
    @Query("SELECT * FROM `login_table `")
    List<LoginEntity> getAllLoginRecord();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoginEntity record);

    @Query("DELETE FROM `login_table `")
    void deleteAll();
}
