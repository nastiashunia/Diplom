package com.example.financialassistant.UI.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.financialassistant.DB.Model.Cost;
import com.example.financialassistant.DB.Model.Document;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCostActivity extends AppCompatActivity {

    @BindView(R.id.sumCost)
    EditText sumCost;
    @BindView(R.id.commentCost)
    EditText commentCost;
    @BindView(R.id.dateCost)
    CalendarView dateCost;
    @BindView(R.id.date)
    TextView dateT;
    @BindView(R.id.save)
    Button save;

    Long date;
    long timeMilli2;
    @BindView(R.id.categoryCost)
    Spinner spinerCategory;
    @BindView(R.id.documentCost)
    Spinner spinerDocument;
    long idperiod;

    private List<Document> documentModels = new ArrayList<>();
    private List<CategoryCost> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    String s,d;
    long idcategory, iddocument;
    private Context context;
    boolean flag = false;
    int count_click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        ButterKnife.bind(this);
        setTitle("Добавить расход");
        dateCost = (CalendarView)findViewById(R.id.dateCost);

        dateCost.setVisibility(View.GONE);
        dateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count_click == 0){
                    count_click = 1;
                dateCost.setVisibility(View.VISIBLE);
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
                flag = true;
                count_click = 0;
            }});
                }
                else {
                    dateCost.setVisibility(View.GONE);
                    count_click = 0;
                }
            }
        });


        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        List<String> strings = getNamesFromListCategory(categoryModels);
        Collections.sort(strings);
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
        documents.add("");
        ArrayAdapter documentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, documents);
        spinerDocument.setAdapter(documentAdapter);
        Collections.sort(documents);
        int index2 = documents.indexOf("");

        spinerDocument.setSelection(index2);
        spinerDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d = parent.getSelectedItem().toString();
                getNameIdDocument(documentModels);
                if (d != "")
                    upDocument();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        errorSave();
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
                idperiod = c.id_period;
                return;
            }
        }
    }


    @OnClick(R.id.save)
    public void onSaveClick() {
        errorSave();
    }

    public void errorSave() {

        String strsumCost = sumCost.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { sumCost.setError("Введите сумму расхода в виде цифр"); return; }
        if (flag == false){
            showToast();}
        else enter();


    }

    public void showToast() {
      //создаём и отображаем текстовое уведомление
       Toast toast = Toast.makeText(getApplicationContext(),
              "Выберите дату!",
              Toast.LENGTH_SHORT);
       toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void enter(){
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Cost model = new Cost();
        model.comment = commentCost.getText().toString();
        model.sum = Integer.parseInt(sumCost.getText().toString());
        model.date = timeMilli2;
        model.categoryCostId = idcategory;

        if ("".equals(d)){
            model.documentId = -1;  }
        else
        {model.documentId = iddocument;}
        databaseHelper.getCostDao().insertCost(model);

        finish();
    }


    public void upDocument() {
        long month = 2592000000l;
        long year = 31536000000l;


        if (idperiod == 2){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            String yes = "Редактировать документ";
            String no = "Отмена";
            alert.setTitle("Редактировать");
            alert.setMessage("Перевести напоминание на месяц вперед?");

            alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Document document = new Document();
                    document = databaseHelper.getDocumentDao().getDocumentById(iddocument);
                    document.repeat_date = document.repeat_date + month ;
                    databaseHelper.getDocumentDao().updateDocument(document);
                    Toast.makeText(getApplicationContext(), "Напоминание обновлено", Toast.LENGTH_SHORT).show();
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

        if (idperiod == 3) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            String yes = "Редактировать документ";
            String no = "Отмена";
            alert.setTitle("Редактировать");
            alert.setMessage("Перевести напоминание на год вперед?");

            alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Document document = new Document();
                    document = databaseHelper.getDocumentDao().getDocumentById(iddocument);
                    document.repeat_date = document.repeat_date + year;
                    databaseHelper.getDocumentDao().updateDocument(document);
                    Toast.makeText(getApplicationContext(), "Напоминание обновлено", Toast.LENGTH_SHORT).show();
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

    }
}
