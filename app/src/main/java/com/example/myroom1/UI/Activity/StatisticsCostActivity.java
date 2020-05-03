package com.example.myroom1.UI.Activity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Spinner;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.myroom1.App;
        import com.example.myroom1.DB.DatabaseHelper;
        import com.example.myroom1.DB.Model.CategoryCost;
        import com.example.myroom1.DB.Model.CategoryIncome;
        import com.example.myroom1.DB.Model.Cost;
        import com.example.myroom1.DB.Model.Income;
        import com.example.myroom1.R;
        import com.example.myroom1.UI.Activity.adapter.SomeCostRecyclerAdapter;
        import com.example.myroom1.UI.Activity.adapter.SomeIncomeRecyclerAdapter;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class StatisticsCostActivity extends AppCompatActivity implements SomeCostRecyclerAdapter.OnDeleteListener {

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
    Boolean flag;
    int summa;

    private DatabaseHelper databaseHelper;

    private List<CategoryCost> categoryModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_cost);
        setTitle("Статистика расходов");
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();

        categoryModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
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

        week.add(Calendar.DATE, -(d_of_week-1));

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

    }

    @Override
    protected void onResume() {
        super.onResume();

       /* SomeIncomeRecyclerAdapter recyclerAdapter = new SomeIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
        recyclerAdapter.setOnDeleteListener(this);
        recyclerView.setAdapter(recyclerAdapter);*/
    }

    @Override
    public void onDelete(Cost costModel) {
        databaseHelper.getCostDao().deleteCost(costModel);
    }

    public void all_category(View view) {
        flag = false;

    }

    public void month(View view) {
        if (flag == true)
        {   SomeCostRecyclerAdapter recyclerAdapter = new SomeCostRecyclerAdapter(this, databaseHelper.getCostDao().getCostByMonthOrWeekFromCategory(timeMilli_month,timeMilli_now, idcategory));
            recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getCostDao().getSumByMonthOrWeekFromCategory(timeMilli_month,timeMilli_now, idcategory);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
        else {
            SomeCostRecyclerAdapter recyclerAdapter = new SomeCostRecyclerAdapter(this, databaseHelper.getCostDao().getCostByMonthOrWeek(timeMilli_month,timeMilli_now));
            recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);

            summa = databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_month,timeMilli_now);
            String str = String.valueOf(summa);
            sum.setText(str);
        }



    }

    public void week(View view) {
        if (flag == true)
        {SomeCostRecyclerAdapter recyclerAdapter = new SomeCostRecyclerAdapter(this, databaseHelper.getCostDao().getCostByMonthOrWeekFromCategory(timeMilli_week, timeMilli_now, idcategory));
            recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getCostDao().getSumByMonthOrWeekFromCategory(timeMilli_week, timeMilli_now, idcategory);
            String str = String.valueOf(summa);
            sum.setText(str);
        }
        else {
            SomeCostRecyclerAdapter recyclerAdapter = new SomeCostRecyclerAdapter(this, databaseHelper.getCostDao().getCostByMonthOrWeek(timeMilli_week, timeMilli_now));
            recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getCostDao().getSumAllByMonthOrWeek(timeMilli_week, timeMilli_now);
            String str = String.valueOf(summa);
            sum.setText(str);
        }


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

}