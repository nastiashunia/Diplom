package com.example.myroom1.DB;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.myroom1.DB.Dao.CategoryCostDao;
import com.example.myroom1.DB.Dao.CategoryIncomeDao;
import com.example.myroom1.DB.Dao.CostDao;
import com.example.myroom1.DB.Dao.DocumentDao;
import com.example.myroom1.DB.Dao.IncomeDao;
import com.example.myroom1.DB.Model.CategoryCost;
import com.example.myroom1.DB.Model.CategoryIncome;
import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.DB.Model.Income;

@Database(entities = { Cost.class, Income.class, Document.class, CategoryIncome.class, CategoryCost.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract IncomeDao getIncomeDao();
    @SuppressWarnings("WeakerAccess")
    public abstract CategoryIncomeDao getCategoryIncomeDao();

    public abstract CategoryCostDao getCategoryCostDao();
  //  public abstract CategoryIncomeDao getCategoryIncomeDao();
    public abstract DocumentDao getDocumentDao();
    public abstract CostDao getCostDao();

    public void run() {
        CategoryIncome categoryIncome = new CategoryIncome();
        for (int i = 0; i < CategoryIncome.CATEGORY_START_NAME.length; i++) {
            categoryIncome.name = CategoryIncome.CATEGORY_START_NAME[i];
            getCategoryIncomeDao().insertCategoryIncome(categoryIncome);
        }}

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}