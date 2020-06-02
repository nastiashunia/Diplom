package com.example.financialassistant.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.Cost;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpCostActivity extends AppCompatActivity {
    @BindView(R.id.sumCost)
    EditText sumCost;
    @BindView(R.id.commentCost)
    EditText commentCost;
    @BindView(R.id.dateCost)
    CalendarView dateCost;

    @BindView(R.id.date)
    TextView date;

    //Long date;
    long timeMilli2;
    @BindView(R.id.categoryCost)
    Spinner spinerCategory;
    @BindView(R.id.documentCost)
    Spinner spinerDocument;

    private List<Document> documentModels = new ArrayList<>();
    private List<CategoryCost> categoryModels = new ArrayList<>();
    Cost modelCost = new Cost();
    private DatabaseHelper databaseHelper;

    String s;
    String d;
    Long idcategory;
    String namecategory;
    String namedocument;
    Long iddocument;
    private Context context;
    Long cost;
    Boolean flag;
    String selectedDate;
    int mYear;
    int mMonth;
    int mDay;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_cost);

        ButterKnife.bind(this);
        setTitle("Редактировать расход");
        Intent intent = getIntent();
        Bundle arguments = getIntent().getExtras();
        cost = arguments.getLong("costid");

        databaseHelper = App.getInstance().getDatabaseInstance();
        modelCost = databaseHelper.getCostDao().getCostById(cost);
        long dateI = modelCost.date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String sDate = sdf.format(dateI);
        date.setText(sDate);
        dateCost = (CalendarView)findViewById(R.id.dateCost);
        dateCost.setVisibility(View.GONE);

        flag = true;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCost.setVisibility(View.VISIBLE);
                dateCost.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        flag = false;

                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;

                        selectedDate = new StringBuilder().append(mDay)
                                .append(".").append(mMonth + 1).append(".").append(mYear)
                                .append(" ").toString();

                        Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();


                        dateCost.setVisibility(View.GONE);
                        c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth, 0, 0);
                        timeMilli2 = c.getTimeInMillis();
                        c = Calendar.getInstance();
                        date.setText(selectedDate);

                        //date.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        if (flag == true)
        {
            timeMilli2 = modelCost.date;
            date.setText(sDate);
        }

        sumCost.setText(String.valueOf(modelCost.sum));
        commentCost.setText(modelCost.comment);

        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        List<String> strings = getNamesFromListCategory(categoryModels);
        idcategory = modelCost.categoryCostId;
        getNamecategory(categoryModels);
        int index1 = strings.indexOf(namecategory);
        Collections.sort(strings);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinerCategory.setAdapter(categoryAdapter);
        spinerCategory.setSelection(index1);

        spinerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                getNameIdCategory(categoryModels);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        documentModels = databaseHelper.getDocumentDao().getAllDocument();
        List<String> documents = getNamesFromListDocument(documentModels);

        iddocument = modelCost.documentId;
        documents.add("");
        int index2 = 0;
        getNamedocument(documentModels);
        if(iddocument == -1)
        {
            index2 = documents.indexOf("");
        }
        else {

            index2 = documents.indexOf(namedocument);
        }
        Collections.sort(documents);
        ArrayAdapter documentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, documents);
        spinerDocument.setAdapter(documentAdapter);
        spinerDocument.setSelection(index2);

        spinerDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d = parent.getSelectedItem().toString();
                getNameIdDocument(documentModels);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    private List<String> getNamesFromListCategory(List<CategoryCost> categoryModels){
        List<String> stringList = new ArrayList<>();

        for (CategoryCost c: categoryModels){
            stringList.add(c.name);
        }
        return stringList;
    }

    private void getNameIdCategory(List<CategoryCost> categoryModels){
        for (CategoryCost c: categoryModels){
            if(s.equals(c.name)){
                idcategory = c.id;
                return;
            }

        }
    }

    private void getNamecategory(List<CategoryCost> categoryModels){
        for (CategoryCost c: categoryModels){
            if(idcategory.equals(c.id)){
                namecategory = c.name;
                return;
            }
        }
    }

    private void getNamedocument(List<Document> documentModels){
        for (Document c: documentModels){
            if(iddocument.equals(c.id)){
                namedocument = c.name;
                return;
            }

        }
    }

    private List<String> getNamesFromListDocument(List<Document> documentModels){
        List<String> stringList = new ArrayList<>();

        for (Document c: documentModels){
            stringList.add(c.name);
        }
        return stringList;
    }

    private void getNameIdDocument(List<Document> documentModels){
        for (Document c: documentModels){
            if(d.equals(c.name)){
                iddocument = c.id;
                return;
            }

        }
    }


    @OnClick(R.id.save)
    public void onSaveClick() {

        String strsumCost = sumCost.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { sumCost.setError("Введите сумму расхода в виде цифр"); return; }

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Cost model = new Cost();
        model.id = cost;
        model.comment = commentCost.getText().toString();
        model.sum = Integer.parseInt(sumCost.getText().toString());
        model.date = timeMilli2;
        model.categoryCostId = idcategory;
        if ("".equals(d)){
            model.documentId = -1;  }
        else
        {model.documentId = iddocument;}
        databaseHelper.getCostDao().updateCost(model);

        finish();
    }


}
