package com.example.myroom1.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryCost {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
}
