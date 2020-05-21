package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Income;

import java.util.List;
@Dao
public interface CategoryIncomeDao {
    @Insert
    void insertCategoryIncome(CategoryIncome categoryIncome);

    @Update
    void updateCategoryIncome(CategoryIncome categoryIncome);

    @Delete
    void deleteCategoryIncome(CategoryIncome categoryIncome);

    @Query("SELECT * FROM categoryIncome")
    List<CategoryIncome> getAllCategoryIncome();

    @Query("SELECT * FROM categoryIncome WHERE id = :Id")
    CategoryIncome getCategoryIncomeById(long Id);

   /* @Query("SELECT CategoryIncome.name FROM categoryIncome WHERE id = :categoryIncomeId")
    CategoryIncome getByIdCategoryIncome(long categoryIncomeId);*/
}
