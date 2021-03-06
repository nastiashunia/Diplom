package com.example.financialassistant.UI.Activity;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddIncomeActivity extends AppCompatActivity {

    @BindView(R.id.sumIncome)
    EditText sumIncome;
    @BindView(R.id.commentIncome)
    EditText commentIncome;
    @BindView(R.id.dateIncome)
    CalendarView dateIncome;
   /*@BindView(R.id.documentIncome)
    EditText documentIncome;*/
    @BindView(R.id.date)
    TextView dateT;
    @BindView(R.id.save)
    Button save;

    Long date;
    long timeMilli2;
    @BindView(R.id.categoryIncome)
    Spinner spinerCategory;
    @BindView(R.id.documentIncome)
    Spinner spinerDocument;


    private List<Document> documentModels = new ArrayList<>();
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    String s;
    String d;
    long idcategory;
    long iddocument;
    long idperiod;
    private Context context;
    int count_click = 0;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        ButterKnife.bind(this);
        dateIncome = (CalendarView)findViewById(R.id.dateIncome);
        dateIncome.setVisibility(View.GONE);
       // save.setVisibility(View.GONE);
        setTitle("Добавить доход");

        /*String strsumCost = sumIncome.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { sumIncome.setError("Введите сумму дохода в виде цифр"); return; }*/

        dateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count_click == 0){
                    count_click = 1;
                    dateIncome.setVisibility(View.VISIBLE);
                    dateIncome.setOnDateChangeListener(new OnDateChangeListener(){

                        @Override
                        public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                            int mYear = year;
                            int mMonth = month;
                            int mDay = dayOfMonth;

                            String selectedDate = new StringBuilder().append(mDay)
                                    .append(".").append(mMonth + 1).append(".").append(mYear)
                                    .append(" ").toString();

                            Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                            date = dateIncome.getDate();
                            dateIncome.setVisibility(View.GONE);
                            Calendar c = Calendar.getInstance();
                            c.set(year, month , dayOfMonth, 0 ,0);
                            timeMilli2 = c.getTimeInMillis();
                            dateT.setText(selectedDate);
                            //dateT.setVisibility(View.VISIBLE);
                            flag = true;
                            count_click = 0;
                        }});
                }
                else {
                    dateIncome.setVisibility(View.GONE);
                    count_click = 0;
                    }

            }
        });
        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
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
       // documents.add("");
        ArrayAdapter documentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, documents);
        spinerDocument.setAdapter(documentAdapter);
        int index2 = documents.indexOf("");
        Collections.sort(documents);
        spinerDocument.setSelection(index2);
        spinerDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d = parent.getSelectedItem().toString();
                getNameIdDocument(documentModels);
               /* if (d != "")
                upDocument();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

       // errorSave();
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

    private List<String> getNamesFromListDocument(List<Document> documentModels){
        List<String> stringList = new ArrayList<>();
        stringList.add("");
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
        //errorSave();
        String strsumCost = sumIncome.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { sumIncome.setError("Введите сумму дохода в виде цифр"); return; }
        //upDocument();
        if (flag == false){
            showToast();}
        else enter();
    }


   /*public void upDocument() {
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

    }*/

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

        Income model = new Income();
        model.comment = commentIncome.getText().toString();
        model.sum = Integer.parseInt(sumIncome.getText().toString());
        model.date = timeMilli2;
        model.categoryIncomeId = idcategory;
        if ("".equals(d)){
            model.documentId = -1;  }
        else
        {model.documentId = iddocument;}

        databaseHelper.getIncomeDao().insertIncome(model);

        finish();
    }
}
