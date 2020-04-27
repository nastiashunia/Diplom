package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myroom1.DB.Model.CategoryCost;
import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Income;

import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insertIncome(Income income);

    @Delete
    void deleteIncome(Income income);

    @Query("SELECT * FROM Income")
    List<Income> getAllIncome();
}

