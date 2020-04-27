package com.example.myroom1.UI.Activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddIncomeActivity extends AppCompatActivity {

    @BindView(R.id.sumIncome)
    EditText sumIncome;
    @BindView(R.id.commentIncome)
    EditText commentIncome;
    @BindView(R.id.dateIncome)
    EditText dateIncome;
    @BindView(R.id.categoryIncome)
    EditText categoryIncome;
    @BindView(R.id.documentIncome)
    EditText documentIncome;

   /* Long date; cv = (CalendarView)findViewById(R.id.calendarView1); date = cv.getDate();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save)
    public void onSaveClick() {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Income model = new Income();
        model.comment = commentIncome.getText().toString();
        model.sum = Integer.parseInt(sumIncome.getText().toString());
        model.date = Long.parseLong(dateIncome.getText().toString());
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

  /*  cv.setOnDateChangeListener(new OnDateChangeListener(){
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            if(cv.getDate() != date){ date = cv.getDate();
    Toast.makeText(view.getContext(), "Year=" + year + " Month=" + month + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show(); } } });*/
}
