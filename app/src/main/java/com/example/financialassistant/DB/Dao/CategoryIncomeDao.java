package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.financialassistant.DB.Model.CategoryIncome;

import java.util.List;
@Dao
public interface CategoryIncomeDao {
    @Insert
    public void insertCategoryIncome(CategoryIncome categoryIncome);

    @Delete
    void deleteCategoryIncome(CategoryIncome categoryIncome);

    @Query("SELECT * FROM categoryIncome")
    List<CategoryIncome> getAllCategoryIncome();

   /* @Query("SELECT CategoryIncome.name FROM categoryIncome WHERE id = :categoryIncomeId")
    CategoryIncome getByIdCategoryIncome(long categoryIncomeId);*/
}
