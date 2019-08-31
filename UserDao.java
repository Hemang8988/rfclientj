package com.example.crudappjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.crudappjava.model.User;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(User use);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * from user ORDER BY mFirstName ASC")
    LiveData<List<User>> getAllUsers();
}
