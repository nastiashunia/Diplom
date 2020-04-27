package com.example.myroom1.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.example.myroom1.App;
import com.example.myroom1.UI.Activity.adapter.SomeDataRecyclerAdapter;
import com.example.myroom1.UI.Activity.AddIncomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                startActivity(new Intent(this, AddIncomeActivity.class));
                break;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SomeDataRecyclerAdapter recyclerAdapter = new SomeDataRecyclerAdapter(this, databaseHelper.getIncomeDao().getAllIncome());
        recyclerAdapter.setOnDeleteListener((SomeDataRecyclerAdapter.OnDeleteListener) this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    //@Override
    public void onDelete(Income incomeModel) {
        databaseHelper.getIncomeDao().deleteIncome(incomeModel);
    }
}
