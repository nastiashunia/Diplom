package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myroom1.DB.Model.CategoryCost;

import java.util.List;
@Dao
public interface CategoryCostDao {

    @Insert
    void insertCategoryCost(CategoryCost categoryCost);

    @Delete
    void deleteCategoryCost(CategoryCost categoryCost);

    @Query("SELECT * FROM categoryCost")
    List<CategoryCost> getAllCategoryCost();

    /*@Query("SELECT CategoryCost.name FROM categoryCost WHERE id = :categoryCostId")
    CategoryCost getByIdCategoryCost(long categoryCostId);*/

    /*@Query("SELECT * FROM categoryCost WHERE id IN (:categoryCostId)")
    List<CategoryCost> loadAllById(int[] categoryCostId);*/

}
