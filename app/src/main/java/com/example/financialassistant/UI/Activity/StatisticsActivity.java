package com.example.financialassistant.UI.Activity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import androidx.appcompat.app.AppCompatActivity;
        import com.example.myroom1.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("Статистика");
    }

    public void income (View view) {
        Intent intent1 = new Intent(this, StatisticsIncomeActivity.class);
        startActivity(intent1);
    }

    public void cost (View view) {
        Intent intent = new Intent(this, StatisticsCostActivity.class);
        startActivity(intent);
    }

}