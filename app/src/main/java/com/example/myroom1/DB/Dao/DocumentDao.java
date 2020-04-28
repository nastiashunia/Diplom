package com.example.myroom1.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myroom1.DB.Model.Document;

import java.util.List;

@Dao
public interface DocumentDao {
    @Insert
    void insert(Document document);

    @Delete
    void delete(Document document);

    @Query("SELECT * FROM document")
    List<Document> getAllDocument();

    /*@Query("SELECT Document.name FROM document WHERE id = :documentId")
    Document getByIdDocument(long documentId);*/
}
