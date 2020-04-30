package com.example.myroom1.UI.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryIncome;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddDocumentActivity extends AppCompatActivity {

    @BindView(R.id.name_document)
    EditText name_document;
    @BindView(R.id.date_start_document)
    CalendarView date_start_document;
    @BindView(R.id.date_finish_document)
    CalendarView date_finish_document;

    @BindView(R.id.date_f)
    EditText date_f;
    @BindView(R.id.date_s)
    EditText date_s;


    long timeMilliStart;
    long timeMilliFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        ButterKnife.bind(this);
        date_start_document = (CalendarView)findViewById(R.id.date_start_document);

        date_start_document.setOnDateChangeListener(new OnDateChangeListener(){

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
            }});

        date_finish_document = (CalendarView)findViewById(R.id.date_finish_document);

        date_finish_document.setOnDateChangeListener(new OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;

                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth + 1).append(".").append(mYear)
                        .append(" ").toString();

                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                date_finish_document.setVisibility(View.GONE);
                Calendar f = Calendar.getInstance();
                f.set(year, month , dayOfMonth, 0 ,0);
                timeMilliFinish = f.getTimeInMillis();

                date_f.setText(selectedDate);
                date_f.setVisibility(View.VISIBLE);
            }});
    }


    @OnClick(R.id.save)
    public void onSaveClick() {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Document model = new Document();
        model.name = name_document.getText().toString();
        model.start_date = timeMilliStart;
        model.finish_date = timeMilliFinish;

        databaseHelper.getDocumentDao().insertDocument(model);

        finish();
    }
}
