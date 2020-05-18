package com.example.financialassistant.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryCost {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public static final String[] CATEGORY_COST_START_NAME ={
            "Продукты", "Красота","Одежда","Подарки","Визаж","Обучение","Машина","Ремонт",
            "Строительство","Обувь","Благотворительность","Съемки","Комуналка","Аренда","Съем",
            "Cпорт","Детское питание","Дети","Игрушки","Сад","Школа","Университет","Вклад","Займ",
            "Родителям","Кино","Книги","Театр","Игровая комната","Развлечения","Путешествия","Отпуск","Лечение"};
}
