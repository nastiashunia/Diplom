package com.example.financialassistant.DB;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.financialassistant.DB.Dao.CategoryCostDao;
import com.example.financialassistant.DB.Dao.CategoryIncomeDao;
import com.example.financialassistant.DB.Dao.CostDao;
import com.example.financialassistant.DB.Dao.DocumentDao;
import com.example.financialassistant.DB.Dao.DocumentPeriodDao;
import com.example.financialassistant.DB.Dao.IncomeDao;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Cost;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.DocumentPeriod;
import com.example.financialassistant.DB.Model.Income;

@Database(entities = { Cost.class, Income.class, Document.class, CategoryIncome.class, CategoryCost.class, DocumentPeriod.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract IncomeDao getIncomeDao();
    @SuppressWarnings("WeakerAccess")
    public abstract CategoryIncomeDao getCategoryIncomeDao();

    public abstract CategoryCostDao getCategoryCostDao();
  //  public abstract CategoryIncomeDao getCategoryIncomeDao();
    public abstract DocumentDao getDocumentDao();
    public abstract CostDao getCostDao();
    public abstract DocumentPeriodDao getDocumentPeriodDao();

    public void run_category_income() {
        CategoryIncome categoryIncome = new CategoryIncome();
        for (int i = 0; i < CategoryIncome.CATEGORY_START_NAME.length; i++) {
            categoryIncome.name = CategoryIncome.CATEGORY_START_NAME[i];
            getCategoryIncomeDao().insertCategoryIncome(categoryIncome);
        }}


    public void run_category_cost() {
        CategoryCost categoryCost = new CategoryCost();
        for (int i = 0; i < CategoryCost.CATEGORY_COST_START_NAME.length; i++) {
            categoryCost.name = CategoryCost.CATEGORY_COST_START_NAME[i];
            getCategoryCostDao().insertCategoryCost(categoryCost);
        }}

    public void run_document_period() {
        DocumentPeriod documentPeriod = new DocumentPeriod();
        for (int i = 0; i < DocumentPeriod.DOCUMENT_PERIOD_START_NAME.length; i++) {
            documentPeriod.name = DocumentPeriod.DOCUMENT_PERIOD_START_NAME[i];
            getDocumentPeriodDao().insertDocumentPeriod(documentPeriod);}

    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }




}