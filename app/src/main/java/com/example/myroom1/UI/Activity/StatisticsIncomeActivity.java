package com.example.myroom1.UI.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import com.example.myroom1.UI.Activity.adapter.SomeIncomeRecyclerAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsIncomeActivity extends AppCompatActivity implements SomeIncomeRecyclerAdapter.OnDeleteListener {

    int year;
    int month;
    int dayOfMonth;
    long timeMilli_month;
    long timeMilli_now;
    long timeMilli_week;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_income);
        setTitle("Статистика доходов");

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();

        Calendar c = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        Calendar mon = Calendar.getInstance();
        int m = c.get(Calendar.MONTH);
        int y = c.get(Calendar.YEAR);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int d_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        c.add(Calendar.DAY_OF_MONTH, -(d_week+1));
        mon.add(Calendar.DAY_OF_MONTH, -(d-1));
        d = c.get(Calendar.DAY_OF_MONTH);
        int dm = mon.get(Calendar.DAY_OF_MONTH);
        int dn = now.get(Calendar.DAY_OF_MONTH);
//        mon.set(y,m,0);
       // int d_week = c.get(Calendar.);
      //  c.set(year, month , dayOfMonth, 0 ,0);
        c.set(y,m,d,0,0);
        mon.set(y,m,dm,0,0);
        now.set(y,m,dn,0,0);

        timeMilli_now = now.getTimeInMillis();
        timeMilli_week = c.getTimeInMillis();
        timeMilli_month = mon.getTimeInMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
        recyclerAdapter.setOnDeleteListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

@Override
    public void onDelete(Income incomeModel) {
        databaseHelper.getIncomeDao().deleteIncome(incomeModel);
    }

    public void all_category(View view) {
        //getIncomeByManthOrWeek

    }

    public void month(View view) {
        super.onResume();
        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilli_now,timeMilli_month));
        recyclerAdapter.setOnDeleteListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void week(View view) {
        super.onResume();
        SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilli_now,timeMilli_week));
        recyclerAdapter.setOnDeleteListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
