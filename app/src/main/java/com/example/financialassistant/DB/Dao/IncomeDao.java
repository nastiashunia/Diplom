package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.financialassistant.DB.Model.Income;

import java.util.List;

@Dao
public interface IncomeDao  {

    @Insert
    void insertIncome(Income income);

    @Delete
    void deleteIncome(Income income);

    @Update
    void updateIncome(Income income);

    @Query("DELETE FROM Income WHERE categoryincome_id = :categoryIncomeId")
    int deleteByCategory (long categoryIncomeId);

    @Query("SELECT * FROM Income ORDER BY date DESC")
    List<Income> getAllIncome();

    @Query("SELECT * FROM Income WHERE id = :incomeId")
    Income getIncomeById(long incomeId);

    @Query("UPDATE Income SET document_id = :toId WHERE document_id = :id")
    void updateIncomeForDeleteDocument(long id, long toId);

    @Query("SELECT * FROM Income WHERE categoryincome_id = :categoryId  ORDER BY date DESC")
    List<Income> getIncomeByIdCategory(long categoryId);

    @Query("SELECT * FROM Income WHERE date BETWEEN :toDate AND :nowDate  ORDER BY date DESC")
    List<Income> getIncomeByMonthOrWeek (long toDate, long nowDate);

    @Query("SELECT * FROM Income WHERE date BETWEEN :toDate AND :nowDate AND categoryincome_id = :category  ORDER BY date DESC")
    List<Income> getIncomeByMonthOrWeekFromCategory (long toDate, long nowDate, long category);

    @Query("SELECT SUM(sum) FROM Income WHERE date BETWEEN :toDate AND :nowDate")
    int getSumAllByMonthOrWeek(long toDate, long nowDate);

    @Query("SELECT SUM(sum) FROM Income WHERE date BETWEEN :toDate AND :nowDate AND categoryincome_id = :category")
    int getSumByMonthOrWeekFromCategory(long toDate, long nowDate, long category);



   /* @Query("SELECT id, categoryincome_id, SUM(sum) FROM Income GROUP BY categoryincome_id")
    List<Income> getIncomeDiagram();*/

}

