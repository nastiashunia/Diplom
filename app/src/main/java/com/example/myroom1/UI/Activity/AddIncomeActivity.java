package com.example.myroom1.UI.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryIncome;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.LongStream;

public class AddIncomeActivity extends AppCompatActivity {

    @BindView(R.id.sumIncome)
    EditText sumIncome;
    @BindView(R.id.commentIncome)
    EditText commentIncome;
    @BindView(R.id.dateIncome)
    CalendarView dateIncome;
    /*@BindView(R.id.categoryIncome)
    EditText categoryIncome;*/
    @BindView(R.id.documentIncome)
    EditText documentIncome;
    @BindView(R.id.date)
    EditText dateT;

    Long date;
    Long i;
    long timeMilli2;
    @BindView(R.id.categoryIncome)
    Spinner spiner1;

    @BindView(R.id.category)
    TextView category;

    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    String s;
    long idcategory;
    private Context context;
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

                dateT.setText(selectedDate);
                dateT.setVisibility(View.VISIBLE);
            }});
        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        List<String> strings = getNamesFromList(categoryModels);

        ArrayAdapter monAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spiner1.setAdapter(monAdapter);

        spiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = parent.getSelectedItem().toString();
                getNameId(categoryModels);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
     //   initSpinner();
    }

    private List<String> getNamesFromList(List<CategoryIncome> categoryModels){
        List<String> stringList = new ArrayList<>();

        for (CategoryIncome c: categoryModels){
            stringList.add(c.name);
        }
        return stringList;
    }

    private void getNameId(List<CategoryIncome> categoryModels){
        for (CategoryIncome c: categoryModels){
            if(s.equals(c.name)){
                idcategory = c.id;
                return;
            }
        }
    }


    @OnClick(R.id.save)
    public void onSaveClick() {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        Income model = new Income();
        model.comment = commentIncome.getText().toString();
        model.sum = Integer.parseInt(sumIncome.getText().toString());
        model.date = timeMilli2;
        //model.categoryIncomeId = Long.parseLong(categoryIncome.getText().toString());
        model.categoryIncomeId = idcategory;
        model.documentId = Long.parseLong(documentIncome.getText().toString());


        databaseHelper.getIncomeDao().insertIncome(model);

        finish();
    }


}
/*<com.chivorn.smartmaterialspinner.SmartMaterialSpinner
        android:id="@+id/spinner1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:smsp_errorText="This is error text. You can show it as single line or multiple lines using attr smsp_multilineError"
        app:smsp_floatingLabelColor="#1976D2"
        app:smsp_floatingLabelText="Floating Label Text"
        app:smsp_hint="Hint Text"
        app:smsp_hintColor="#388E3C"
        app:smsp_itemColor="#512DA8"
        app:smsp_itemListColor="#7C4DFF"
        app:smsp_itemListHintBackgroundColor="#808080"
        app:smsp_itemListHintColor="#FFFFFF"
        app:smsp_multilineError="false"
        app:smsp_selectedItemListColor="#FF5252" />*/