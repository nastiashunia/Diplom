package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financialassistant.DB.Model.Cost;


import java.util.List;

@Dao
public interface CostDao {
    @Insert
    void insertCost(Cost cost);

    @Delete
    void deleteCost(Cost cost);

    @Update
    void updateCost(Cost cost);

    @Query("SELECT * FROM Cost WHERE id = :costId")
    Cost getCostById(long costId);

    @Query("SELECT * FROM Cost")
    List<Cost> getAllCost();

    @Query("SELECT * FROM Cost WHERE categorycost_id = :categoryId")
    List<Cost> getCostByIdCategory(long categoryId);

    @Query("SELECT * FROM Cost WHERE date BETWEEN :toDate AND :nowDate")
    List<Cost> getCostByMonthOrWeek (long toDate, long nowDate);

    @Query("SELECT * FROM Cost WHERE date BETWEEN :toDate AND :nowDate AND categoryCost_id = :category")
    List<Cost> getCostByMonthOrWeekFromCategory (long toDate, long nowDate, long category);

    @Query("SELECT SUM(sum) FROM Cost WHERE date BETWEEN :toDate AND :nowDate")
    int getSumAllByMonthOrWeek(long toDate, long nowDate);

    @Query("SELECT SUM(sum) FROM Cost WHERE date BETWEEN :toDate AND :nowDate AND categoryCost_id = :category")
    int getSumByMonthOrWeekFromCategory(long toDate, long nowDate, long category);


}
