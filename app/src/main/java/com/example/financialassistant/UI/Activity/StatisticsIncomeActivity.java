package com.example.financialassistant.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.UI.Activity.adapter.SomeStatisticsIncomeRecyclerAdapter;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsIncomeActivity extends AppCompatActivity  {
    @BindView(R.id.date_start_document)
    CalendarView date_start_document;
    @BindView(R.id.date_finish_document)
    CalendarView date_finish_document;

    @BindView(R.id.date_f)
    TextView date_f;
    @BindView(R.id.date_s)
    TextView date_s;

    Boolean flag2 = false;
    Boolean flag1 = false;

    long timeMilliStart;
    long timeMilliFinish;

    long timeMilli_month;
    long timeMilli_now;
    long timeMilli_week;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.name_category_search)
    Spinner name_category_search;
    @BindView(R.id.sum)
    TextView sum;
    Long idcategory;
    String s;
    Boolean flag, start = false;
    int summa;
    long start_period;
    long finish_period;
    private DatabaseHelper databaseHelper;

    private List<CategoryIncome> categoryModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_income);
        setTitle("Статистика доходов");



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
                flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        Calendar week = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        Calendar mon = Calendar.getInstance();
        int m_now = now.get(Calendar.MONTH);
        int y_now = now.get(Calendar.YEAR);
        int d_now = now.get(Calendar.DAY_OF_MONTH);
        int d_of_week;

        int a = now.get(Calendar.DAY_OF_WEEK);
         if (a == 1)
             d_of_week = 7;
         else d_of_week = a - 1;

        week.add(Calendar.DATE, -(d_of_week));

        int d_week = week.get(Calendar.DAY_OF_MONTH);
        int m_week = week.get(Calendar.MONTH);
        int y_week = week.get(Calendar.YEAR);

        mon.add(Calendar.DATE, -(d_now));

        int d_mon = mon.get(Calendar.DAY_OF_MONTH);
        int m_mon = mon.get(Calendar.MONTH);
        int y_mon = mon.get(Calendar.YEAR);

        week.set(y_week,m_week,d_week,0,0);
        mon.set(y_mon,m_mon,d_mon,0,0);
        now.set(y_now,m_now,d_now,0,0);

        timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет
        timeMilli_week = week.getTimeInMillis(); // дата за вычетом недели , т.е. начало недели , от какой даты идет отсчет
        timeMilli_month = mon.getTimeInMillis(); // дата начала месяца, 1 число месяца


       /* Intent intent = getIntent();
        Bundle arguments = getIntent().getExtras();
        if(start == true){
        start_period = arguments.getLong("start");
        finish_period = arguments.getLong("finish");
        start = arguments.getBoolean("flag_start");

        period();}*/

        date_start_document.setVisibility(View.GONE);
        date_finish_document.setVisibility(View.GONE);

        date_start_document = (CalendarView)findViewById(R.id.date_start_document);

        date_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_start_document.setVisibility(View.VISIBLE);
                date_start_document.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                        int mYear = year;
                        int mMonth = month;
                        int mDay = dayOfMonth;

                        String selectedDate = new StringBuilder().append(mDay)
                                .append(".").append(mMonth + 1).append(".").append(mYear)
                                .append(" ").toString();

                        Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                        date_start_document.setVisibility(View.GONE);
                        Calendar c = Calendar.getInstance();
                        c.set(year, month , dayOfMonth, 0 ,0);
                        timeMilliStart = c.getTimeInMillis();

                        date_s.setText(selectedDate);
                        date_s.setVisibility(View.VISIBLE);
                        flag2 = true;
                    }});
            }
        });

        date_finish_document = (CalendarView)findViewById(R.id.date_finish_document);

        date_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_finish_document.setVisibility(View.VISIBLE);
                date_finish_document.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

                    @Override
                    public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                        int mYear = year;
                        int mMonth = month;
                        int mDay = dayOfMonth;

                        String selectedDate1 = new StringBuilder().append(mDay)
                                .append(".").append(mMonth + 1).append(".").append(mYear)
                                .append(" ").toString();

                        Toast.makeText(getApplicationContext(), selectedDate1, Toast.LENGTH_SHORT).show();

                        date_finish_document.setVisibility(View.GONE);
                        Calendar f = Calendar.getInstance();
                        f.set(year, month , dayOfMonth, 0 ,0);
                        timeMilliFinish = f.getTimeInMillis();

                        date_f.setText(selectedDate1);
                        date_f.setVisibility(View.VISIBLE);
                        flag1 = true;
                        period();
                    }});
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void all_category(View view) {
        flag = false;

    }

    public void month(View view) {
        if (flag == true)
        {   SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeekFromCategory(timeMilli_month,timeMilli_now, idcategory));
         //   recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumByMonthOrWeekFromCategory(timeMilli_month,timeMilli_now, idcategory);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
        else {
            SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilli_month,timeMilli_now));
          //  recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);

            summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_month,timeMilli_now);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
    }

    public void week(View view) {
        if (flag == true)
        {SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeekFromCategory(timeMilli_week, timeMilli_now, idcategory));
           // recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumByMonthOrWeekFromCategory(timeMilli_week, timeMilli_now, idcategory);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
        else {
            SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilliStart, timeMilliFinish));
          //  recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilliStart, timeMilliFinish);
            String str = String.valueOf(summa);
            sum.setText(str);
        }


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

    public void some() {
  /*      if (start == false)
        {
            Intent intent1 = new Intent(this, PeriodActivity.class);
            startActivity(intent1);
            start = true;
        }
        else
        {
            Intent intent = getIntent();
            Bundle arguments = getIntent().getExtras();
            start_period = arguments.getLong("start");
            finish_period = arguments.getLong("finish");
            start = arguments.getBoolean("flag_start");
            period();
        }*/
    }
    public void period() {
        if (flag == true)
        {SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeekFromCategory(start_period, finish_period, idcategory));
            // recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumByMonthOrWeekFromCategory(start_period, finish_period, idcategory);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
        else {
            SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilliStart, timeMilliFinish));
            //  recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilliStart, timeMilliFinish);
            String str = String.valueOf(summa);
            sum.setText(str);
        }


    }
}
/*<Button
                    style="@style/MyButtonStyle"
                            android:id="@+id/some"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_above="@id/recyclerView"
                            android:layout_weight="1"
                            android:onClick="some"
                            android:text="За период"
                            android:layout_marginLeft="10dp"/>*/