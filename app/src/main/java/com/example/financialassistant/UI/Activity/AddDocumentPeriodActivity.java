package com.example.financialassistant.UI.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.DocumentPeriod;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDocumentPeriodActivity extends AppCompatActivity {

    @BindView(R.id.name_document)
    EditText name_document;
    @BindView(R.id.date_start_document)
    CalendarView date_start_document;
    @BindView(R.id.date_finish_document)
    CalendarView date_finish_document;

    @BindView(R.id.date_replay)
    CalendarView date_replay;
    @BindView(R.id.period)
    Spinner period;

    @BindView(R.id.date_f)
    TextView date_f;
    @BindView(R.id.date_s)
    TextView date_s;

    @BindView(R.id.replayDate)
    TextView replayDate;

    Boolean flag = false;
    Boolean flag1 = false;
    Boolean flag2 = false;

    int count_click = 0;
    int count_click_1 = 0;
    int count_click_2 = 0;
    long timeMilliStart;
    long timeMilliFinish;
    long timeMilliReplay;

    private List<DocumentPeriod> documentPeriodModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    String s;
    String d;
    long idperiod;
String nameperiod;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        ButterKnife.bind(this);
        setTitle("Добавить документ");

        date_start_document.setVisibility(View.GONE);
        date_finish_document.setVisibility(View.GONE);
        date_replay.setVisibility(View.GONE);
        replayDate.setVisibility(View.GONE);

        databaseHelper = App.getInstance().getDatabaseInstance();

        documentPeriodModels = databaseHelper.getDocumentPeriodDao().getAllDocumentPeriod();
        List<String> strings = getNamesFromListPeriod(documentPeriodModels);

        ArrayAdapter documentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        period.setAdapter(documentAdapter);
        Collections.sort(strings);
        int index2 = strings.indexOf("Одноразовые");

        period.setSelection(index2);
        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                getNameIdPeriod(documentPeriodModels);
                show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });



        date_start_document = (CalendarView) findViewById(R.id.date_start_document);

        date_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_click == 0) {
                    count_click = 1;
                    date_start_document.setVisibility(View.VISIBLE);
                    date_start_document.setOnDateChangeListener(new OnDateChangeListener() {

                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                            int mYear = year;
                            int mMonth = month;
                            int mDay = dayOfMonth;

                            String selectedDate = new StringBuilder().append(mDay)
                                    .append(".").append(mMonth + 1).append(".").append(mYear)
                                    .append(" ").toString();

                            Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                            date_start_document.setVisibility(View.GONE);
                            Calendar c = Calendar.getInstance();
                            c.set(year, month, dayOfMonth, 0, 0);
                            timeMilliStart = c.getTimeInMillis();

                            date_s.setText(selectedDate);
                            date_s.setVisibility(View.VISIBLE);
                            flag = true;
                            count_click = 0;
                        }
                    });
                } else {
                    date_start_document.setVisibility(View.GONE);
                    count_click = 0;
                }
            }
        });

        date_finish_document = (CalendarView) findViewById(R.id.date_finish_document);

        date_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_click_1 == 0) {
                    count_click_1 = 1;
                    date_finish_document.setVisibility(View.VISIBLE);
                    date_finish_document.setOnDateChangeListener(new OnDateChangeListener() {

                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                            int mYear = year;
                            int mMonth = month;
                            int mDay = dayOfMonth;

                            String selectedDate1 = new StringBuilder().append(mDay)
                                    .append(".").append(mMonth + 1).append(".").append(mYear)
                                    .append(" ").toString();

                            Toast.makeText(getApplicationContext(), selectedDate1, Toast.LENGTH_SHORT).show();

                            date_finish_document.setVisibility(View.GONE);
                            Calendar f = Calendar.getInstance();
                            f.set(year, month, dayOfMonth, 0, 0);
                            timeMilliFinish = f.getTimeInMillis();

                            date_f.setText(selectedDate1);
                            date_f.setVisibility(View.VISIBLE);
                            flag1 = true;
                            count_click_1 = 0;
                        }
                    });
                } else {
                    date_finish_document.setVisibility(View.GONE);
                    count_click_1 = 0;
                }
            }
        });

        /*date_start_document.setVisibility(View.GONE);
        date_finish_document.setVisibility(View.GONE);

        date_replay.setVisibility(View.GONE);
        date_f.setVisibility(View.GONE);
        date_s.setVisibility(View.GONE);

        replayDate.setVisibility(View.GONE);

        flag = true;
        flag1 = true;*/




    }

    public void show (){
        String i = "Ежегодные";
        String j = "Ежемесячные";

        if (s.equals(i) || s.equals(j))
        {
        replayDate.setVisibility(View.VISIBLE);
        date_replay = (CalendarView) findViewById(R.id.date_replay);

        replayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_click_2 == 0) {
                    count_click_2 = 1;
                    date_replay.setVisibility(View.VISIBLE);
                    date_replay.setOnDateChangeListener(new OnDateChangeListener() {

                        @Override
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                            int mYear = year;
                            int mMonth = month;
                            int mDay = dayOfMonth;

                            String selectedDate1 = new StringBuilder().append(mDay)
                                    .append(".").append(mMonth + 1).append(".").append(mYear)
                                    .append(" ").toString();

                            Toast.makeText(getApplicationContext(), selectedDate1, Toast.LENGTH_SHORT).show();

                            date_replay.setVisibility(View.GONE);
                            Calendar f = Calendar.getInstance();
                            f.set(year, month, dayOfMonth, 0, 0);
                            timeMilliReplay = f.getTimeInMillis();

                            replayDate.setText(selectedDate1);
                            replayDate.setVisibility(View.VISIBLE);
                            flag2 = true;
                            count_click_2 = 0;
                        }
                    });
                } else {
                    date_replay.setVisibility(View.GONE);
                    count_click_2 = 0;
                }
            }
        });}
        else {
            replayDate.setVisibility(View.GONE);
            date_replay.setVisibility(View.GONE);
            flag2 = true;
        }
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        //errorSave();
        String k = "Одноразовые";
        String strsumCost = name_document.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { name_document.setError("Введите наименование документа"); return; }
        if (flag == false ){
            showToast();}
        else {if (flag1 == false ){
            showToast();}
            if(flag2 == false){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Выберите дату напоминания об оплате документа!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        else if (timeMilliStart > timeMilliFinish)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Дата начала больше даты окончания срока действия документа!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else
            enter();}
    }

    public void showToast() {
        //создаём и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                "Выберите дату начала и окончания срока действия документа!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void enter(){
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Document model = new Document();
        model.name = name_document.getText().toString();
        model.start_date = timeMilliStart;
        model.finish_date = timeMilliFinish;
        model.repeat_date = timeMilliReplay;
        model.id_period = idperiod;

        databaseHelper.getDocumentDao().insertDocument(model);

        finish();
    }


    private List<String> getNamesFromListPeriod(List<DocumentPeriod> documentPeriodModels){
        List<String> stringList = new ArrayList<>();

        for (DocumentPeriod c: documentPeriodModels){
            stringList.add(c.name);
        }
        return stringList;
    }

    private void getNameIdPeriod(List<DocumentPeriod> documentPeriodModels){
        for (DocumentPeriod c: documentPeriodModels){
            if(s.equals(c.name)){
                idperiod = c.id;
                return;
            }

        }
    }
}
