package com.example.financialassistant.UI.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllStatisticsActivity extends AppCompatActivity {

    long timeMilli_month;
    long timeMilli_now;
    long timeMilli_week;
    long timeMilli_year;
    @BindView(R.id.sum_income_year)
    TextView sum_income_year;
    @BindView(R.id.sum_income_month)
    TextView sum_income_month;
    @BindView(R.id.sum_income_week)
    TextView sum_income_week;
    @BindView(R.id.sum_cost_year)
    TextView sum_cost_year;
    @BindView(R.id.sum_cost_month)
    TextView sum_cost_month;
    @BindView(R.id.sum_cost_week)
    TextView sum_cost_week;
    @BindView(R.id.raz_year)
    TextView raz_year;
    @BindView(R.id.raz_month)
    TextView raz_month;
    @BindView(R.id.raz_week)
    TextView raz_week;
    Long idcategory;
    String s;
    Boolean flag;
    int summa;
    String str;

    private DatabaseHelper databaseHelper;

    private List<CategoryIncome> categoryModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_statistics);
        setTitle("Статистика");
        ButterKnife.bind(this);
        databaseHelper = App.getInstance().getDatabaseInstance();

        Calendar week = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        Calendar mon = Calendar.getInstance();
        Calendar year = Calendar.getInstance();
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

        mon.add(Calendar.DATE, -(d_now + 1));

        int d_mon = mon.get(Calendar.DAY_OF_MONTH);
        int m_mon = mon.get(Calendar.MONTH);
        int y_mon = mon.get(Calendar.YEAR);

        week.set(y_week,m_week,d_week,0,0);
        mon.set(y_mon,m_mon,d_mon,0,0);
        now.set(y_now,m_now,d_now,0,0);

        year.set(y_now,0,1,0,0);

        timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет
        timeMilli_week = week.getTimeInMillis(); // дата за вычетом недели , т.е. начало недели , от какой даты идет отсчет
        timeMilli_month = mon.getTimeInMillis(); // дата начала месяца, 1 число месяца
        timeMilli_year = year.getTimeInMillis();
        summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_year,timeMilli_now);
        str = String.valueOf(summa);
        sum_income_year.setText(str);
        summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_month, timeMilli_now);
        str = String.valueOf(summa);
        sum_income_month.setText(str);
        summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_week,timeMilli_now);
        str = String.valueOf(summa);
        sum_income_week.setText(str);

        summa =databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_year,timeMilli_now);
        str = String.valueOf(summa);
        sum_cost_year.setText(str);
        summa = databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_month, timeMilli_now);
        str = String.valueOf(summa);
        sum_cost_month.setText(str);
        summa = databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_week,timeMilli_now);
        str = String.valueOf(summa);
        sum_cost_week.setText(str);

        summa = (databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_year,timeMilli_now))-(databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_year,timeMilli_now));
        str = String.valueOf(summa);
        raz_year.setText(str);
        summa =databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_month, timeMilli_now)- databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_month, timeMilli_now);
        str = String.valueOf(summa);
        raz_month.setText(str);
        summa =databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilli_week,timeMilli_now)- databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_week,timeMilli_now);
        str = String.valueOf(summa);
        raz_week.setText(str);
    }


}
