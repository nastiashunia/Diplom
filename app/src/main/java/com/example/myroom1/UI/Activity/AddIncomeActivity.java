package com.example.myroom1.UI.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.LongStream;

public class AddIncomeActivity extends AppCompatActivity {

    @BindView(R.id.sumIncome)
    EditText sumIncome;
    @BindView(R.id.commentIncome)
    EditText commentIncome;
    @BindView(R.id.dateIncome)
    CalendarView dateIncome;
    @BindView(R.id.categoryIncome)
    EditText categoryIncome;
    @BindView(R.id.documentIncome)
    EditText documentIncome;
    @BindView(R.id.date)
    EditText dateT;

    Long date;
    long timeMilli2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        dateIncome = (CalendarView)findViewById(R.id.dateIncome);

        dateIncome.setOnDateChangeListener(new OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;

                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth + 1).append(".").append(mYear)
                        .append(" ").toString();

                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

                date = dateIncome.getDate();
                dateIncome.setVisibility(View.GONE);
                Calendar c = Calendar.getInstance();
                c.set(year, month , dayOfMonth, 0 ,0);
                timeMilli2 = c.getTimeInMillis();
               // SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
               // String sDate = sdf.format(date);

                dateT.setText(selectedDate);
                dateT.setVisibility(View.VISIBLE);
            }});
       /* cv.setOnDateChangeListener(new OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            { if(cv.getDate() != date)
            { date = cv.getDate();
            Toast.makeText(view.getContext(), "Year=" + year + " Month=" + month + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show(); } } });
   */
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Income model = new Income();
        model.comment = commentIncome.getText().toString();
        model.sum = Integer.parseInt(sumIncome.getText().toString());
        model.date = timeMilli2;
        model.categoryIncomeId = Long.parseLong(categoryIncome.getText().toString());
        model.documentId = Long.parseLong(documentIncome.getText().toString());

        /*DataModel model = new DataModel();
        model.setTitle(title.getText().toString());
        model.setDescription(description.getText().toString());
        databaseHelper.getDataDao().insert(model);*/

      /*  model.setTitle(title.getText().toString());
        model.setDescription(description.getText().toString());*/
        databaseHelper.getIncomeDao().insertIncome(model);

        finish();
    }

    //Настраиваем слушателя смены даты:


  /*  cv.setOnDateChangeListener(new OnDateChangeListener(){
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            if(cv.getDate() != date){ date = cv.getDate();
    Toast.makeText(view.getContext(), "Year=" + year + " Month=" + month + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show(); } } });*/
}
