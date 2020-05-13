package com.example.myroom1.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpDocumentActivity extends AppCompatActivity {
    Long document;
    @BindView(R.id.name_document)
    EditText name_document;
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
    Document modelDocument = new Document();
   // long date_start, date_finish;
    String sDate, fDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_up_document);

    ButterKnife.bind(this);
    setTitle("Редактировать документ");
    Intent intent = getIntent();
    Bundle arguments = getIntent().getExtras();
    document = arguments.getLong("documentid");

        databaseHelper = App.getInstance().getDatabaseInstance();
        modelDocument = databaseHelper.getDocumentDao().getDocumentById(document);
       // long dateI = modelDocument.start_date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sDate = sdf.format(modelDocument.start_date);
        date_s.setText(sDate);

        fDate = sdf.format(modelDocument.finish_date);
        date_f.setText(fDate);

        name_document.setText(modelDocument.name);

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

        if (flag == false)
        {
            timeMilliStart = modelDocument.start_date;
            date_s.setText(sDate);
        }

        if (flag1 == false)
        {
            timeMilliFinish = modelDocument.finish_date;
            date_f.setText(fDate);
        }
}


    @OnClick(R.id.save)
    public void onSaveClick() {
        //errorSave();
        String strsumCost = name_document.getText().toString();
        if(TextUtils.isEmpty(strsumCost)) { name_document.setError("Введите наименование документа"); return; }

        if (timeMilliStart > timeMilliFinish)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Дата начала больше даты окончания срока действия документа!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else
            enter();
    }

    public void enter(){


        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();


        Document model = new Document();
        model.id = document;
        model.name = name_document.getText().toString();
        model.start_date = timeMilliStart;
        model.finish_date = timeMilliFinish;

        databaseHelper.getDocumentDao().updateDocument(model);

        finish();

    }

   /* public void showToast() {
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

        databaseHelper.getDocumentDao().insertDocument(model);

        finish();
    }*/
}
