package com.example.financialassistant.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
        //(foreignKeys = @ForeignKey(entity = CategoryCost.class, parentColumns = "id", childColumns = "categorycost_id" ))
public class Cost {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String comment;

    public int sum;

    public long date;

    @ColumnInfo(name = "categorycost_id")
    public long categoryCostId;

    @ColumnInfo(name = "document_id")
    public long documentId;
}
