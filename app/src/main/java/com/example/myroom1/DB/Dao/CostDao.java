package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Income;

import java.util.List;

@Dao
public interface CostDao {
    @Insert
    void insertCost(Cost cost);

    @Delete
    void deleteCost(Cost cost);

    @Query("SELECT * FROM Cost")
    List<Cost> getAllCost();


    @Query("SELECT * FROM Cost WHERE categorycost_id = :categoryId")
    List<Cost> getCostByIdCategory(long categoryId);


}
