package com.example.financialassistant.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DocumentPeriod {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public static final String[] DOCUMENT_PERIOD_START_NAME ={
            "Одноразовые", "Ежемесячные", "Ежегодные"};



}
