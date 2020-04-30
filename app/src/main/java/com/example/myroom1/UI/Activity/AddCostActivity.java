package com.example.myroom1.UI.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryCost;
import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCostActivity extends AppCompatActivity {

    @BindView(R.id.sumCost)
    EditText sumCost;
    @BindView(R.id.commentCost)
    EditText commentCost;
    @BindView(R.id.dateCost)
    CalendarView dateCost;
    @BindView(R.id.date)
    EditText dateT;

    Long date;
    long timeMilli2;
    @BindView(R.id.categoryCost)
    Spinner spinerCategory;
    @BindView(R.id.documentCost)
    Spinner spinerDocument;


    private List<Document> documentModels = new ArrayList<>();
    private List<CategoryCost> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    String s;
    String d;
    long idcategory;
    long iddocument;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        ButterKnife.bind(this);
        dateCost = (CalendarView)findViewById(R.id.dateCost);

        dateCost.setOnDateChangeListener(new OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;

                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth + 1).append(".").append(mYear)
                        .append(" ").toString();

                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                date = dateCost.getDate();
                dateCost.setVisibility(View.GONE);
                Calendar c = Calendar.getInstance();
                c.set(year, month , dayOfMonth, 0 ,0);
                timeMilli2 = c.getTimeInMillis();

                dateT.setText(selectedDate);
                dateT.setVisibility(View.VISIBLE);
            }});
        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        List<String> strings = getNamesFromListCategory(categoryModels);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinerCategory.setAdapter(categoryAdapter);

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

        ArrayAdapter documentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, documents);
        spinerDocument.setAdapter(documentAdapter);

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
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Cost model = new Cost();
        model.comment = commentCost.getText().toString();
        model.sum = Integer.parseInt(sumCost.getText().toString());
        model.date = timeMilli2;
        model.categoryCostId = idcategory;
        //model.documentId = Long.parseLong(documentCost.getText().toString());
        model.documentId = iddocument;

        databaseHelper.getCostDao().insertCost(model);

        finish();
    }


}
