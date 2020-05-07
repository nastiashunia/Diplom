package com.example.myroom1.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryIncome;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myroom1.App;
import com.example.myroom1.UI.Activity.adapter.SomeIncomeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeActivity extends AppCompatActivity implements SomeIncomeRecyclerAdapter.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.name_category_search)
    //EditText name_category_search;
    Spinner name_category_search;
    Long idcategory;
    String s;
    String namecategory;
    Boolean flag;

    private DatabaseHelper databaseHelper;

    private List<CategoryIncome> categoryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setTitle("Доходы");
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();


        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        List<String> strings = getNamesFromListCategory(categoryModels);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        name_category_search.setAdapter(categoryAdapter);

        name_category_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                getNameIdCategory(categoryModels);
                poisk();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                startActivity(new Intent(this, AddIncomeActivity.class));
                break;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
       // recyclerAdapter.setOnDeleteListener(this);
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onDelete(Income incomeModel) {
        databaseHelper.getIncomeDao().deleteIncome(incomeModel);
    }
    @Override
    public void onUp(Income incomeModel) {
        long i = incomeModel.id;
        //databaseHelper.getIncomeDao().getIncomeById(i);
        Intent intent1 = new Intent(this, UpIncomeActivity.class);
        intent1.putExtra("incomeid",i );
        startActivity(intent1);
    }

    public void search(View view) {

        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
       // recyclerAdapter.setOnDeleteListener(this);
        recyclerAdapter.setOnClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);

    }
    public void poisk(){
        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByIdCategory(idcategory));
//        recyclerAdapter.setOnDeleteListener(this);
        recyclerAdapter.setOnClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> getNamesFromListCategory(List<CategoryIncome> categoryModels){
        List<String> stringList = new ArrayList<>();

        for (CategoryIncome c: categoryModels){
            stringList.add(c.name);
        }
        return stringList;
    }

    private void getNameIdCategory(List<CategoryIncome> categoryModels){
        for (CategoryIncome c: categoryModels){
            if(s.equals(c.name)){
                idcategory = c.id;
                return;
            }

        }
    }
}
