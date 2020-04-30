package com.example.myroom1.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.example.myroom1.App;
import com.example.myroom1.UI.Activity.adapter.SomeCostRecyclerAdapter;
import com.example.myroom1.UI.Activity.adapter.SomeIncomeRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CostActivity extends AppCompatActivity implements SomeCostRecyclerAdapter.OnDeleteListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

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
                startActivity(new Intent(this, AddCostActivity.class));
                break;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SomeCostRecyclerAdapter recyclerAdapter = new SomeCostRecyclerAdapter(this, databaseHelper.getCostDao().getAllCost());
        recyclerAdapter.setOnDeleteListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void onDelete(Cost costModel) {
        databaseHelper.getCostDao().deleteCost(costModel);
    }
}
