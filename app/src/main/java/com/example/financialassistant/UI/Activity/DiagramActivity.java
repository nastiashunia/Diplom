package com.example.financialassistant.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
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
Boolean flag = false;

    long timeMilliStart;
    long timeMilliFinish;

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
        setTitle("Финансовая диаграмма");

        strings.add("Текущая неделя");
        strings.add("Текущий месяц");
        strings.add("Текущий год");
        strings.add("Прошедшая неделя");
        strings.add("Прошедший месяц");
        strings.add("Прошедший год");
        strings.add("");


        Collections.sort(strings);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        period.setAdapter(categoryAdapter);

        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                hashMap1.clear();
                hashMapCost.clear();

                if (s != ""){
                    flag = true;
                    setPieChartIncome();
                    setPieChartCost();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

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
                        some();
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
                        some();
                    }});
            }
        });

    }



    public void setPieChartIncome() {
        pieChart.setVisibility(View.VISIBLE);

        long timeMilli_now;
        long timeMilli_week;

        if(flag){

            Calendar now = Calendar.getInstance();

            int m_now = now.get(Calendar.MONTH);
            int y_now = now.get(Calendar.YEAR);
            int d_now = now.get(Calendar.DAY_OF_MONTH);
            now.set(y_now,m_now,d_now,0,0);
            //timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет

            int d_of_week,a;

            int d_week,m_week,y_week;
            Calendar week = Calendar.getInstance();


            switch (s) {
                case  "Текущая неделя":

                    a = now.get(Calendar.DAY_OF_WEEK);
                    if (a == 1)
                        d_of_week = 7;
                    else d_of_week = a - 1;

                    now.add(Calendar.DATE, + 1);

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
                case "Прошедшая неделя":
                    a = now.get(Calendar.DAY_OF_WEEK);
                    if (a == 1)
                        d_of_week = 7;
                    else d_of_week = a - 1;

                    week.add(Calendar.DATE, -(d_of_week + 7));
                    d_week = week.get(Calendar.DAY_OF_MONTH);
                    m_week = week.get(Calendar.MONTH);
                    y_week = week.get(Calendar.YEAR);
                    week.set(y_week,m_week,d_week,0,0);

                    now.add(Calendar.DATE, -(d_of_week ));
                    m_now = now.get(Calendar.MONTH);
                    y_now = now.get(Calendar.YEAR);
                    d_now = now.get(Calendar.DAY_OF_MONTH);
                    now.set(y_now,m_now,d_now,0,0);

                    break;
                case "Прошедший месяц":

                    now.add(Calendar.DATE, -(d_now));
                    m_now = now.get(Calendar.MONTH);
                    y_now = now.get(Calendar.YEAR);
                    d_now = now.get(Calendar.DAY_OF_MONTH);
                    now.set(y_now,m_now,d_now,0,0);

                    week.add(Calendar.DATE, -(d_now + 1));
                    week.add(Calendar.DATE, -(d_now + 1));
                    d_week = week.get(Calendar.DAY_OF_MONTH);
                    m_week = week.get(Calendar.MONTH);
                    y_week = week.get(Calendar.YEAR);
                    week.set(y_week,m_week,d_week,0,0);
                    break;
                case "Прошедший год":
                    week.set(y_now - 1,0,1,0,0);
                    now.set(y_now,0,1,0,0);
                    break;
            }
            timeMilli_now = now.getTimeInMillis();
            timeMilli_week = week.getTimeInMillis();

            incomeModels = databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilli_week,timeMilli_now);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String sDate = sdf.format(timeMilli_week);
            String fDate = sdf.format(timeMilli_now);
            timeMilliStart = timeMilli_week;
            timeMilliFinish = timeMilli_now;
            flag1=true;
            flag2=true;

            date_s.setText(sDate);
            date_f.setText(fDate);
        }


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        pieChart.setDrawEntryLabels(false);

        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        getListIncome(incomeModels);


        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (Map.Entry<Long, Integer> e : hashMap1.entrySet()) {
            idcategory = e.getKey();
            getNamecategoryIncome(categoryModels);
            yValues.add(new PieEntry(e.getValue(), namecategory));
        }

        final int[] MY_COLORS = {Color.rgb(240, 128, 128),Color.rgb(220, 20, 60),Color.rgb(127, 255, 0),Color.rgb(65, 105, 225),Color.rgb(255, 0, 0),Color.rgb(0, 0, 128),
                Color.rgb(50, 205, 50),Color.rgb(102, 205, 170),Color.rgb(139, 0, 139),Color.rgb(112, 128, 144),Color.rgb(0, 250, 154),Color.rgb(233, 150, 122),
                Color.rgb(160, 82, 45),Color.rgb(0, 128, 128),Color.rgb(0, 128, 0),Color.rgb(255, 69, 0),Color.rgb(186, 85, 211),Color.rgb(46, 139, 87),};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);


        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setTextSize(16);

        PieDataSet dataSet = new PieDataSet(yValues,  " - Категории доходов");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors); //COLORFUL_COLORS
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);



    }


    public void setPieChartCost() {

        pieChartCost.setVisibility(View.VISIBLE);

        long timeMilli_now;
        long timeMilli_week;

        if (flag) {

            Calendar now = Calendar.getInstance();

            int m_now = now.get(Calendar.MONTH);
            int y_now = now.get(Calendar.YEAR);
            int d_now = now.get(Calendar.DAY_OF_MONTH);
            now.set(y_now,m_now,d_now,0,0);
            //timeMilli_now = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет

            int d_of_week,a;

            int d_week,m_week,y_week;
            Calendar week = Calendar.getInstance();


            switch (s) {
                case  "Текущая неделя":

                    a = now.get(Calendar.DAY_OF_WEEK);
                    if (a == 1)
                        d_of_week = 7;
                    else d_of_week = a - 1;

                    now.add(Calendar.DATE, + 1);

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
                case "Прошедшая неделя":
                    a = now.get(Calendar.DAY_OF_WEEK);
                    if (a == 1)
                        d_of_week = 7;
                    else d_of_week = a - 1;

                    week.add(Calendar.DATE, -(d_of_week + 7));
                    d_week = week.get(Calendar.DAY_OF_MONTH);
                    m_week = week.get(Calendar.MONTH);
                    y_week = week.get(Calendar.YEAR);
                    week.set(y_week,m_week,d_week,0,0);

                    now.add(Calendar.DATE, -(d_of_week ));
                    m_now = now.get(Calendar.MONTH);
                    y_now = now.get(Calendar.YEAR);
                    d_now = now.get(Calendar.DAY_OF_MONTH);
                    now.set(y_now,m_now,d_now,0,0);

                    break;
                case "Прошедший месяц":

                    now.add(Calendar.DATE, -(d_now));
                    m_now = now.get(Calendar.MONTH);
                    y_now = now.get(Calendar.YEAR);
                    d_now = now.get(Calendar.DAY_OF_MONTH);
                    now.set(y_now,m_now,d_now,0,0);

                    week.add(Calendar.DATE, -(d_now + 1));
                    week.add(Calendar.DATE, -(d_now + 1));
                    d_week = week.get(Calendar.DAY_OF_MONTH);
                    m_week = week.get(Calendar.MONTH);
                    y_week = week.get(Calendar.YEAR);
                    week.set(y_week,m_week,d_week,0,0);
                    break;
                case "Прошедший год":
                    week.set(y_now - 1,0,1,0,0);
                    now.set(y_now,0,1,0,0);
                    break;
            }
            timeMilli_now = now.getTimeInMillis();
            timeMilli_week = week.getTimeInMillis();

            costModels = databaseHelper.getCostDao().getCostByMonthOrWeek(timeMilli_week,timeMilli_now);
        }




        pieChartCost.setUsePercentValues(true);
        pieChartCost.getDescription().setEnabled(false);
        pieChartCost.setExtraOffsets(5, 10, 5, 5);
        pieChartCost.setDragDecelerationFrictionCoef(0.9f);
        pieChartCost.setTransparentCircleRadius(61f);
        pieChartCost.setHoleColor(Color.WHITE);
        pieChartCost.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        pieChartCost.setDrawEntryLabels(false);

        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryCostModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        getListCost(costModels);

        pieChartCost.getLegend().setWordWrapEnabled(true);
        pieChartCost.getLegend().setTextSize(16);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (Map.Entry<Long, Integer> e : hashMapCost.entrySet()) {
            idcategoryCost = e.getKey();
            getNamecategoryCost(categoryCostModels);
            yValues.add(new PieEntry(e.getValue(), namecategoryCost));
            int k = 1;
        }


        final int[] MY_COLORS = {Color.rgb(240, 128, 128),Color.rgb(220, 20, 60),Color.rgb(127, 255, 0),Color.rgb(65, 105, 225),Color.rgb(255, 0, 0),Color.rgb(0, 0, 128),
                Color.rgb(50, 205, 50),Color.rgb(102, 205, 170),Color.rgb(139, 0, 139),Color.rgb(112, 128, 144),Color.rgb(0, 250, 154),Color.rgb(233, 150, 122),
                Color.rgb(160, 82, 45),Color.rgb(0, 128, 128),Color.rgb(0, 128, 0),Color.rgb(255, 69, 0),Color.rgb(186, 85, 211),Color.rgb(46, 139, 87),};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);
        PieDataSet dataSet = new PieDataSet(yValues, " - Категории расходов");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);
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
        int i = 1;
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

        int i = 1;
    }


    private void getNamecategoryCost(List<CategoryCost> categoryModels) {
        for (CategoryCost c : categoryModels) {
            if (idcategoryCost.equals(c.id)) {
                namecategoryCost = c.name;
                return;
            }
        }
    }


    public void some() {
        int index2 = strings.indexOf("");
        period.setSelection(index2);
        if (flag2 == false || flag1 == false){
            showToast();}
        /*else {if (flag1 == false ){
            showToast();}*/
        /*else { if (timeMilliStart > timeMilliFinish)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Дата начала больше даты окончания периода!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            pieChartCost.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
        }*/
        else
        {
            incomeModels = databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilliStart,timeMilliFinish );
            costModels = databaseHelper.getCostDao().getCostByMonthOrWeek(timeMilliStart,timeMilliFinish);
            setPieChartCost();
            setPieChartIncome();
        }
        }

    public void showToast() {
        //создаём и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                "Выберите дату начала и окончания периода!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        pieChartCost.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
    }
   /* public void period() {

        incomeModels = databaseHelper.getIncomeDao().getIncomeByMonthOrWeek

            SomeStatisticsIncomeRecyclerAdapter recyclerAdapter = new SomeStatisticsIncomeRecyclerAdapter(this, databaseHelper.getIncomeDao().getIncomeByMonthOrWeek(timeMilliStart, timeMilliFinish));
            //  recyclerAdapter.setOnDeleteListener(this);
            recyclerView.setAdapter(recyclerAdapter);
            summa = databaseHelper.getIncomeDao().getSumAllByMonthOrWeek(timeMilliStart, timeMilliFinish);
            String str = String.valueOf(summa);
            sum.setText(str);
            recyclerView.setVisibility(View.VISIBLE);
    }*/

}

 /*  public void setPieChart() {
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
    }*/



        /*PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.DARK_GRAY);
        plot.setLabelBackgroundPaint(null);*/


