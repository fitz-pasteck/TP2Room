package com.moiroudthibaut.tp2room.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.moiroudthibaut.tp2room.database.entity.Place;

import java.util.List;

@Dao
public interface PlaceDAO {

    @Query("SELECT * FROM place")
    List<Place> getAll();

    @Query("SELECT * FROM place")
    LiveData<List<Place>> subscribeGetAll();

    @Query("SELECT * FROM place WHERE _id = :id")
    Place get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Place... places);

    @Delete
    void delete(Place... places);

}
