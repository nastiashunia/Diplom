package com.example.financialassistant.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Cost;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagramActivity extends AppCompatActivity {
    PieChart pieChart;
    PieChart pieChartCost;
    DatabaseHelper databaseHelper;
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private List<Income> incomeModels = new ArrayList<>();
    private List<CategoryCost> categoryCostModels = new ArrayList<>();
    private List<Cost> costModels = new ArrayList<>();
    Long idcategory;
    String namecategory;
    @BindView(R.id.period)
    Spinner period;
    Long idcategoryCost;
    String namecategoryCost;

    List<String> strings = new ArrayList<>();
    String s;

    /*@BindView(R.id.piechart_1)
    PieChart piechart_1;*/
    Map<Long, Integer> hashMap1 = new HashMap<Long, Integer>();
    Map<Long, Integer> hashMapCost = new HashMap<Long, Integer>();

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_diagram);
        ButterKnife.bind(this);
        pieChart = (PieChart) findViewById(R.id.piechart_1);
        pieChartCost = (PieChart) findViewById(R.id.piechart_2);
        databaseHelper = App.getInstance().getDatabaseInstance();
        //setPieChart();



        strings.add("Текущая неделя");
        strings.add("Текущий месяц");
        strings.add("Текущий год");


        Collections.sort(strings);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        period.setAdapter(categoryAdapter);

        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                hashMap1.clear();
                hashMapCost.clear();

                setPieChartIncome();
                setPieChartCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    public void setPieChartIncome() {

        pieChart.setVisibility(View.VISIBLE);

        long timeMilli_now;
        long timeMilli_week;

        Calendar now = Calendar.getInstance();
        int m_now = now.get(Calendar.MONTH);
        int y_now = now.get(Calendar.YEAR);
        int d_now = now.get(Calendar.DAY_OF_MONTH);
        now.set(y_now,m_now,d_now,0,0);
        timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет

        int d_of_week,a;

        int d_week,m_week,y_week;
        Calendar week = Calendar.getInstance();


        switch (s) {
            case  "Текущая неделя":
                a = now.get(Calendar.DAY_OF_WEEK);
                if (a == 1)
                    d_of_week = 7;
                else d_of_week = a - 1;

                week.add(Calendar.DATE, -(d_of_week));
                d_week = week.get(Calendar.DAY_OF_MONTH);
                m_week = week.get(Calendar.MONTH);
                y_week = week.get(Calendar.YEAR);
                week.set(y_week,m_week,d_week,0,0);
                break;
            case "Текущий месяц":
                week.add(Calendar.DATE, -(d_now + 1));
                d_week = week.get(Calendar.DAY_OF_MONTH);
                m_week = week.get(Calendar.MONTH);
                y_week = week.get(Calendar.YEAR);
                week.set(y_week,m_week,d_week,0,0);
                break;
            case "Текущий год":
                week.set(y_now,0,1,0,0);
                break;
        }

        timeMilli_week = week.getTimeInMillis();

        incomeModels = databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilli_week,timeMilli_now);


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        getListIncome(incomeModels);


        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (Map.Entry<Long, Integer> e : hashMap1.entrySet()) {
            idcategory = e.getKey();
            getNamecategoryIncome(categoryModels);
            yValues.add(new PieEntry(e.getValue(), namecategory));
        }



        PieDataSet dataSet = new PieDataSet(yValues, "Категории доходов");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);

    }


    public void setPieChartCost() {

        pieChartCost.setVisibility(View.VISIBLE);

        long timeMilli_now;
        long timeMilli_week;

        Calendar now = Calendar.getInstance();
        int m_now = now.get(Calendar.MONTH);
        int y_now = now.get(Calendar.YEAR);
        int d_now = now.get(Calendar.DAY_OF_MONTH);
        now.set(y_now,m_now,d_now,0,0);
        timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет

        int d_of_week,a;

        int d_week,m_week,y_week;
        Calendar week = Calendar.getInstance();


        switch (s) {
            case  "Текущая неделя":
                a = now.get(Calendar.DAY_OF_WEEK);
                if (a == 1)
                    d_of_week = 7;
                else d_of_week = a - 1;

                week.add(Calendar.DATE, -(d_of_week));
                d_week = week.get(Calendar.DAY_OF_MONTH);
                m_week = week.get(Calendar.MONTH);
                y_week = week.get(Calendar.YEAR);
                week.set(y_week,m_week,d_week,0,0);
                break;
            case "Текущий месяц":
                week.add(Calendar.DATE, -(d_now + 1));
                d_week = week.get(Calendar.DAY_OF_MONTH);
                m_week = week.get(Calendar.MONTH);
                y_week = week.get(Calendar.YEAR);
                week.set(y_week,m_week,d_week,0,0);
                break;
            case "Текущий год":
                week.set(y_now,0,1,0,0);
                break;
        }

        timeMilli_week = week.getTimeInMillis();

        costModels = databaseHelper.getCostDao().getCostByMonthOrWeek(timeMilli_week,timeMilli_now);


        pieChartCost.setUsePercentValues(true);
        pieChartCost.getDescription().setEnabled(true);
        pieChartCost.setExtraOffsets(5, 10, 5, 5);
        pieChartCost.setDragDecelerationFrictionCoef(0.9f);
        pieChartCost.setTransparentCircleRadius(61f);
        pieChartCost.setHoleColor(Color.WHITE);
        pieChartCost.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryCostModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        getListCost(costModels);


        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (Map.Entry<Long, Integer> e : hashMapCost.entrySet()) {
            idcategoryCost = e.getKey();
            getNamecategoryCost(categoryCostModels);
            yValues.add(new PieEntry(e.getValue(), namecategoryCost));
            int k = 1;
        }



        PieDataSet dataSet = new PieDataSet(yValues, "Категории расходов");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChartCost.setData(pieData);

    }
    private void getListIncome(List<Income> incomeModels) {
        for (Income c : incomeModels) {
            if (hashMap1.containsKey(c.categoryIncomeId))
            {
                hashMap1.put(c.categoryIncomeId, hashMap1.get(c.categoryIncomeId)+c.sum);
            }
            else {
                hashMap1.put(c.categoryIncomeId, c.sum);
            }
        }
    }


    private void getNamecategoryIncome(List<CategoryIncome> categoryModels) {
        for (CategoryIncome c : categoryModels) {
            if (idcategory.equals(c.id)) {
                namecategory = c.name;
                return;
            }
        }
    }

    private void getListCost(List<Cost> costModels) {
        for (Cost c : costModels) {
            if (hashMapCost.containsKey(c.categoryCostId))
            {
                hashMapCost.put(c.categoryCostId, hashMap1.get(c.categoryCostId)+c.sum);
            }
            else {
                hashMapCost.put(c.categoryCostId, c.sum);
            }
        }
    }


    private void getNamecategoryCost(List<CategoryCost> categoryModels) {
        for (CategoryCost c : categoryModels) {
            if (idcategoryCost.equals(c.id)) {
                namecategoryCost = c.name;
                return;
            }
        }
    }

}
 


