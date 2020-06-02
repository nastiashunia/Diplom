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
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpIncomeActivity extends AppCompatActivity {
    @BindView(R.id.sumIncome)
    EditText sumIncome;
    @BindView(R.id.commentIncome)
    EditText commentIncome;
    @BindView(R.id.dateIncome)
    CalendarView dateIncome;

    @BindView(R.id.date)
    TextView date;

    //Long date;
    long timeMilli2;
    @BindView(R.id.categoryIncome)
    Spinner spinerCategory;
    @BindView(R.id.documentIncome)
    Spinner spinerDocument;

    private List<Document> documentModels = new ArrayList<>();
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    Income modelIncome = new Income();
    private DatabaseHelper databaseHelper;

    String s;
    String d;
    Long idcategory;
    String namecategory;
    String namedocument;
    Long iddocument;
    private Context context;
    Long income;
    Boolean flag;
    String selectedDate;
    int mYear;
    int mMonth;
    int mDay;
    Calendar c;
    String sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_income);

        ButterKnife.bind(this);
        setTitle("Редактировать доход");
        Intent intent = getIntent();
        Bundle arguments = getIntent().getExtras();
        income = arguments.getLong("incomeid");

        databaseHelper = App.getInstance().getDatabaseInstance();
        modelIncome = databaseHelper.getIncomeDao().getIncomeById(income);
        long dateI = modelIncome.date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sDate = sdf.format(dateI);
        date.setText(sDate);
        dateIncome = (CalendarView)findViewById(R.id.dateIncome);
        dateIncome.setVisibility(View.GONE);

        flag = true;

        date.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        dateIncome.setVisibility(View.VISIBLE);
        dateIncome.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

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


                dateIncome.setVisibility(View.GONE);
                //Calendar c = Calendar.getInstance();
                c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                timeMilli2 = c.getTimeInMillis();

                date.setText(selectedDate);
                //GetDate(flag);
                //date.setVisibility(View.VISIBLE);
                //flag = true;
            }
        });
    }
});

        if (flag == true)
        {
            timeMilli2 = modelIncome.date;
            date.setText(sDate);
        }
       /* else {
            c.set(mYear,mMonth, mDay, 0, 0);
            timeMilli2 = c.getTimeInMillis();
            date.setText(selectedDate);
        }*/

        sumIncome.setText(String.valueOf(modelIncome.sum));
        commentIncome.setText(modelIncome.comment);

        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        List<String> strings = getNamesFromListCategory(categoryModels);
        Collections.sort(strings);
        idcategory = modelIncome.categoryIncomeId;
        getNamecategory(categoryModels);
        int index1 = strings.indexOf(namecategory);


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
        Collections.sort(documents);
        iddocument = modelIncome.documentId;

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

    private void getNamecategory(List<CategoryIncome> categoryModels){
        for (CategoryIncome c: categoryModels){
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
            //namedocument = "";
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

        String strsumCost = sumIncome.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { sumIncome.setError("Введите сумму дохода в виде цифр"); return; }

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        Income model = new Income();
        model.id = income;
        model.comment = commentIncome.getText().toString();
        model.sum = Integer.parseInt(sumIncome.getText().toString());
        model.date = timeMilli2;
        model.categoryIncomeId = idcategory;
        if ("".equals(d)){
            model.documentId = -1;  }
        else
        {model.documentId = iddocument;}
        databaseHelper.getIncomeDao().updateIncome(model);

        finish();
    }


}
