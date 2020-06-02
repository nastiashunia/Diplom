package com.example.financialassistant.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagramActivity extends AppCompatActivity {
    PieChart pieChart;
    DatabaseHelper databaseHelper;
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private List<Income> incomeModels = new ArrayList<>();
    List<PieEntry> yValues = new ArrayList<>();
    Long idcategory;
    String namecategory;



    Map<Long, Integer> hashMap1 = new HashMap<Long, Integer>();

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_diagram);
        pieChart = (PieChart) findViewById(R.id.piechart_1);
        databaseHelper = App.getInstance().getDatabaseInstance();
        //setPieChart();
        setPieChart1();

    }

    public void setPieChart() {
        // this.pieChart = pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(34, "Ilala"));
        yValues.add(new PieEntry(56, "Temeke"));
        yValues.add(new PieEntry(66, "Kinondoni"));
        yValues.add(new PieEntry(45, "Kigamboni"));

        PieDataSet dataSet = new PieDataSet(yValues, "Desease Per Regions");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }


    public void setPieChart1() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        databaseHelper = App.getInstance().getDatabaseInstance();
        incomeModels = databaseHelper.getIncomeDao().getAllIncome();
        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        getNamesFromListDocument(incomeModels);


        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (Map.Entry<Long, Integer> e : hashMap1.entrySet()) {
            idcategory = e.getKey();
            getNamecategory(categoryModels);
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

    private void getNamesFromListDocument(List<Income> incomeModels) {
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


    private void getNamecategory(List<CategoryIncome> categoryModels) {
        for (CategoryIncome c : categoryModels) {
            if (idcategory.equals(c.id)) {
                namecategory = c.name;
                return;
            }
        }
    }

}