package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financialassistant.DB.Model.DocumentPeriod;

import java.util.List;
@Dao
public interface DocumentPeriodDao {
    @Insert
    void insertDocumentPeriod(DocumentPeriod documentPeriod);

    @Update
    void updateDocumentPeriod(DocumentPeriod documentPeriod);

    @Delete
    void deleteDocumentPeriod(DocumentPeriod documentPeriod);

    @Query("SELECT * FROM documentPeriod")
    List<DocumentPeriod> getAllDocumentPeriod();

    @Query("SELECT * FROM documentPeriod WHERE id = :Id")
    DocumentPeriod getDocumentPeriodById(long Id);
}
