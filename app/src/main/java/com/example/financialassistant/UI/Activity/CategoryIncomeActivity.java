package com.example.financialassistant.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.UI.Activity.adapter.SomeIncomeCategoryRecyclerAdapter;
import com.example.myroom1.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryIncomeActivity extends AppCompatActivity implements SomeIncomeCategoryRecyclerAdapter.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SomeIncomeCategoryRecyclerAdapter recyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Menu menu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_income);
        setTitle("Категории доходов");
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_button, menu);
        menu.findItem(R.id.action_category).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add: {
                int operation = 0;
                Intent intent1 = new Intent(this, AddUpCategoryIncomeActivity.class);
                intent1.putExtra("addorup",operation );
                startActivity(intent1);
                break;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerAdapter = new SomeIncomeCategoryRecyclerAdapter(this, databaseHelper.getCategoryIncomeDao().getAllCategoryIncome());
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void onDelete(CategoryIncome categoryModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String yes = "Удалить категорию";
        String no = "Отмена";
        alert.setTitle("Удалить");
        alert.setMessage("Удаление этой категории приведет к удалению всех доходов с этой категорией. Удалить?");
        alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                databaseHelper.getCategoryIncomeDao().deleteCategoryIncome(categoryModel);
                databaseHelper.getIncomeDao().deleteByCategory(categoryModel.id);

                recyclerAdapter.f();
                Toast.makeText(getApplicationContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton(no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                }
        );
        alert.show();
    }

    @Override
    public void onUp(CategoryIncome categoryModel) {
        long i = categoryModel.id;
        int operation = 1;
        Intent intent1 = new Intent(this, AddUpCategoryIncomeActivity.class);
        intent1.putExtra("categoryid",i );
        intent1.putExtra("addorup",operation );
        startActivity(intent1);
    }
}
