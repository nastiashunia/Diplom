package com.example.financialassistant.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financialassistant.DB.Model.Document;

import java.util.List;

@Dao
public interface DocumentDao {
    @Insert
    void insertDocument(Document document);

    @Delete
    void deleteDocument(Document document);

    @Update
    void updateDocument (Document document);

    @Query("SELECT * FROM Document WHERE id = :documentId")
    Document getDocumentById(long documentId);

    @Query("SELECT * FROM document")
    List<Document> getAllDocument();

    @Query("SELECT * FROM document WHERE finish_date BETWEEN :dateStart AND :dateFinish")
    List<Document> getAllDocumentDateFinish( long dateStart, long dateFinish);

    /*@Query("SELECT Document.name FROM document WHERE id = :documentId")
    Document getByIdDocument(long documentId);*/
}
