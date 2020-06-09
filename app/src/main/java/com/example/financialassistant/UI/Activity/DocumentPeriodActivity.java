package com.example.financialassistant.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.DocumentPeriod;
import com.example.financialassistant.UI.adapter.ListAdapter;
import com.example.financialassistant.UI.adapter.SomeDocumentPeriodRecyclerAdapter;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentPeriodActivity extends AppCompatActivity implements SomeDocumentPeriodRecyclerAdapter.OnClickListener {


    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    List<String> expListTitle;
    HashMap<String, List<String>> expListDetail = new HashMap<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SomeDocumentPeriodRecyclerAdapter recyclerAdapter;
    private DatabaseHelper databaseHelper;
    List<String> one = new ArrayList<>();
    List<String> month = new ArrayList<>();
    List<String> year = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_period);
        setTitle("Документы");
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();



       // HashMap<String, List<String>> expListDetail = new HashMap<>();

        List<Document> documentModels = new ArrayList<>();
        documentModels = databaseHelper.getDocumentDao().getAllDocument();

        List<DocumentPeriod> documentPeriodModels = new ArrayList<>();
        documentPeriodModels = databaseHelper.getDocumentPeriodDao().getAllDocumentPeriod();


        getDocumentPeriod(documentPeriodModels, documentModels);


       /* one.add("Справка");
        one.add("Гарантия");
        one.add("Другое единовременное");
        month.add("Квитанции");
        month.add("Коммуникации");
        month.add("Кредит");
        month.add("Другое ежемесячное");
        month.add("Страховка");
        month.add("Обучение");
        month.add("Другое ежегодное");
        expListDetail.put("Единовременные", one);
        expListDetail.put("Ежемесячные", month);
        expListDetail.put("Ежегодные", year);*/
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new ListAdapter(this, expListTitle, expListDetail);

        expListView.setAdapter(expListAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                recyclerView.setVisibility(View.GONE);
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                recyclerView.setVisibility(View.GONE);
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition)
                                + " : " + expListDetail.get(expListTitle.get(groupPosition))
                                .get(childPosition), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                poisk(expListDetail.get(expListTitle.get(groupPosition)).get(childPosition));

                return false;
            }
        });
    }


    private List<String> getNames(List<Document> documentModels, Long nameId){
        List<String> stringList = new ArrayList<>();

        for (Document c: documentModels){
            Long id = c.id_period;
            if (id.equals(nameId))
            {
                stringList.add(c.name);
            }

        }
        return stringList;
    }

    private void getDocumentPeriod (List<DocumentPeriod> documentPeriodModels, List<Document> documentModels){
        int k = 1;
        String x1 = "Одноразовые";
        String x2 = "Ежемесячные";
        String x3 = "Ежегодные";

        for (DocumentPeriod c: documentPeriodModels)
        {
            if((c.name).equals(x1))
            {
                one = getNames(documentModels, c.id);
            }
            if((c.name).equals(x2))
            {
                month = getNames(documentModels, c.id);
            }
            if((c.name).equals(x3))
            {
                year = getNames(documentModels, c.id);
            }
            k = 0;
        }
        if (k == 0){
            expListDetail.put("Одноразовые", one);
            expListDetail.put("Ежемесячные", month);
            expListDetail.put("Ежегодные", year);
        }

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
                startActivity(new Intent(this, AddDocumentPeriodActivity.class));
                break;
            }
        }
        return false;
    }

    protected void poisk (String name){
        super.onResume();
        recyclerAdapter = new SomeDocumentPeriodRecyclerAdapter(this, databaseHelper.getDocumentDao().getDocumentByName(name));
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    /*@Override
    protected void onResume() {

        super.onResume();
        recyclerAdapter = new SomeDocumentPeriodRecyclerAdapter(this, databaseHelper.getDocumentDao().getAllDocument());
        recyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);

    }*/

    @Override
    public void onDelete(Document documentModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        String yes = "Удалить документ";
        String no = "Отмена";
        alert.setTitle("Удалить");
        alert.setMessage("Если вы удалите этот документ, то он удалится из доходов/расходов, введеных с ним ранее. Вы действительно хотите удалить эту запись?");
        alert.setPositiveButton(yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                long id = documentModel.id;
                databaseHelper.getDocumentDao().deleteDocument(documentModel);
                databaseHelper.getIncomeDao().updateIncomeForDeleteDocument(id, -1);
                databaseHelper.getCostDao().updateCostForDeleteDocument(id, -1);

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
        Intent intent1 = new Intent(this, UpDocumentPeriodActivity.class);
        intent1.putExtra("documentid",i );
        startActivity(intent1);
    }
}
