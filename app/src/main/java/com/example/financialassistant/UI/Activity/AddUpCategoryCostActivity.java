package com.example.financialassistant.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUpCategoryCostActivity extends AppCompatActivity {
    @BindView(R.id.name)
    EditText name;

    private List<CategoryCost> categoryModels = new ArrayList<>();
    CategoryCost modelCategoryCost = new CategoryCost();
    private DatabaseHelper databaseHelper;


    private Context context;
    Long category;
    int operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_add_category);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle arguments = getIntent().getExtras();
        databaseHelper = App.getInstance().getDatabaseInstance();
        operation = arguments.getInt("addorup");
        if (operation == 1) {
            setTitle("Редактировать расход");
            category = arguments.getLong("categoryid");
            modelCategoryCost = databaseHelper.getCategoryCostDao().getCategoryCostById(category);
            name.setText(modelCategoryCost.name);
        }
        else {
            setTitle("Добавить расход");

        }
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        String strsumCost = name.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { name.setError("Введите наименование категории"); return; }
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        CategoryCost model = new CategoryCost();

        if (operation == 1) {
            model.id = category;
            model.name = name.getText().toString();
            databaseHelper.getCategoryCostDao().updateCategoryCost(model);
        }
        else {
            model.name = name.getText().toString();
            databaseHelper.getCategoryCostDao().insertCategoryCost(model);
        }
        finish();

    }


}

