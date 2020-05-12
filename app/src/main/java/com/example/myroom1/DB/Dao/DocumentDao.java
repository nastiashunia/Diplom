package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.DB.Model.Income;

import java.util.List;

@Dao
public interface DocumentDao {
    @Insert
    void insertDocument(Document document);

    @Delete
    void deleteDocument(Document document);

    @Update
    void updateDocument (Document document);

    @Query("SELECT * FROM document")
    List<Document> getAllDocument();

    /*@Query("SELECT Document.name FROM document WHERE id = :documentId")
    Document getByIdDocument(long documentId);*/
}
