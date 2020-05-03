package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


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

    @Query("SELECT * FROM Income WHERE categoryincome_id = :categoryId")
    List<Income> getIncomeByIdCategory(long categoryId);

    @Query("SELECT * FROM Income WHERE date BETWEEN :toDate AND :nowDate")
    List<Income> getIncomeByMonthOrWeek (long toDate, long nowDate);

    @Query("SELECT * FROM Income WHERE date BETWEEN :toDate AND :nowDate AND categoryincome_id = :category")
    List<Income> getIncomeByMonthOrWeekFromCategory (long toDate, long nowDate, long category);

    @Query("SELECT SUM(sum) FROM Income WHERE date BETWEEN :toDate AND :nowDate")
    int getSumOrWeek(long toDate, long nowDate);



}

