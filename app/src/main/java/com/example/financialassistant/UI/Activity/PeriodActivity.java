package com.example.financialassistant.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.Document;
import com.example.myroom1.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeriodActivity extends AppCompatActivity {
    @BindView(R.id.date_start_document)
    CalendarView date_start_document;
    @BindView(R.id.date_finish_document)
    CalendarView date_finish_document;

    @BindView(R.id.date_f)
    TextView date_f;
    @BindView(R.id.date_s)
    TextView date_s;

    Boolean flag = false;
    Boolean flag1 = false;

    long timeMilliStart;
    long timeMilliFinish;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);
        setTitle("Выбор периода статистики");

        ButterKnife.bind(this);
        databaseHelper = App.getInstance().getDatabaseInstance();

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
                        flag = true;
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
                    }});
            }
        });
    }


    @OnClick(R.id.save)
    public void onSaveClick() {
        //errorSave();

        if (flag == false ){
            showToast();}
        else {if (flag1 == false ){
            showToast();}
        else if (timeMilliStart > timeMilliFinish)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Дата начала больше даты окончания периода!",
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
                "Выберите дату начала и окончания периода!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void enter(){
        //long i = incomeModel.id;
        Intent intent1 = new Intent(this, StatisticsIncomeActivity.class);
        intent1.putExtra("start",timeMilliStart );
        intent1.putExtra("finish",timeMilliFinish );
        Boolean start = true;
        intent1.putExtra("flag_start",start);

        startActivity(intent1);
    }
}

