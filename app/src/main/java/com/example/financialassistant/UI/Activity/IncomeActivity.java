package com.example.financialassistant.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.financialassistant.App;
import com.example.financialassistant.UI.Activity.adapter.SomeIncomeRecyclerAdapter;

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
    SomeIncomeRecyclerAdapter recyclerAdapter;
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
        /*recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);*/
    }

    @Override
    public void onDelete(Income incomeModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String yes = "Удалить доход";
        String no = "Отмена";
        alert.setTitle("Удалить");
        alert.setMessage("Вы действительно хотите удалить эту запись?");
        alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                databaseHelper.getIncomeDao().deleteIncome(incomeModel);
                recyclerAdapter.f();

                Toast.makeText(getApplicationContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton(no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                }
        );
        alert.show();
    }

    @Override
    public void onUp(Income incomeModel) {
        long i = incomeModel.id;
        Intent intent1 = new Intent(this, UpIncomeActivity.class);
        intent1.putExtra("incomeid",i );
        startActivity(intent1);
    }

    public void search(View view) {

         recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }
    public void poisk(){
         recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByIdCategory(idcategory));
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
