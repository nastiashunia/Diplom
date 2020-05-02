package com.example.myroom1.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.R;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private static final String MY_SETTINGS = "my_settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = App.getInstance().getDatabaseInstance();
        setTitle("Financial assistant");


        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            databaseHelper.run_category_income();
            databaseHelper.run_category_cost();
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();
        }
}

    public void addi(View view) {
        Intent intent1 = new Intent(this, IncomeActivity.class);
        startActivity(intent1);
    }

    public void adds(View view) {
        Intent intent = new Intent(this, CostActivity.class);
        startActivity(intent);
    }

    public void doc(View view) {
        Intent intent1 = new Intent(this, DocumentActivity.class);
        startActivity(intent1);
    }

    public void statistics(View view) {
        Intent intent1 = new Intent(this, StatisticsActivity.class);
        startActivity(intent1);
    }
}