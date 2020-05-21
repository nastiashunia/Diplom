package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.financialassistant.DB.Model.CategoryIncome;

import java.util.List;
@Dao
public interface CategoryCostDao {

    @Insert
    void insertCategoryCost(CategoryCost categoryCost);

    @Update
    void updateCategoryCost(CategoryCost categoryCost);

    @Delete
    void deleteCategoryCost(CategoryCost categoryCost);

    @Query("SELECT * FROM categoryCost")
    List<CategoryCost> getAllCategoryCost();

    @Query("SELECT * FROM categoryCost WHERE id = :Id")
    CategoryCost getCategoryCostById(long Id);

    /*@Query("SELECT CategoryCost.name FROM categoryCost WHERE id = :categoryCostId")
    CategoryCost getByIdCategoryCost(long categoryCostId);*/

    /*@Query("SELECT * FROM categoryCost WHERE id IN (:categoryCostId)")
    List<CategoryCost> loadAllById(int[] categoryCostId);*/

}
