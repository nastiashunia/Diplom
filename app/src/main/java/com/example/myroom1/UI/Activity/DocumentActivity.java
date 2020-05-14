package com.example.myroom1.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myroom1.App;
import com.example.myroom1.UI.Activity.adapter.SomeDocumentRecyclerAdapter;
import com.example.myroom1.UI.Activity.adapter.SomeIncomeRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentActivity extends AppCompatActivity implements SomeDocumentRecyclerAdapter.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SomeDocumentRecyclerAdapter recyclerAdapter;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        setTitle("Документы");
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
                startActivity(new Intent(this, AddDocumentActivity.class));
                break;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

         recyclerAdapter = new SomeDocumentRecyclerAdapter(this, databaseHelper.getDocumentDao().getAllDocument());
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void onDelete(Document documentModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String yes = "Удалить документ";
        String no = "Отмена";
        alert.setTitle("Удалить");
        alert.setMessage("Вы действительно хотите удалить эту запись?");
        alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                databaseHelper.getDocumentDao().deleteDocument(documentModel);
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
    public void onUp(Document documentModel) {
        long i = documentModel.id;
        Intent intent1 = new Intent(this, UpDocumentActivity.class);
        intent1.putExtra("documentid",i );
        startActivity(intent1);
    }
}
