package com.example.myroom1.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryCost;
import com.example.myroom1.DB.Model.Cost;
import com.example.myroom1.DB.Model.Document;
import com.example.myroom1.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;



public class SomeStatisticsCostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Cost> costModels = new ArrayList<>();
    private List<CategoryCost> categoryModels = new ArrayList<>();
    private List<Document> documentModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private Context context;

    Long date;
    Long idcategory;
    String namecategory;
    Long iddocument;
    String namedocument;

    public SomeStatisticsCostRecyclerAdapter(Context context, List<Cost> costModels) {
        this.context = context;
        this.costModels = costModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_cost, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        date = costModels.get(position).date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String sDate = sdf.format(date);

        idcategory = costModels.get(position).categoryCostId;
        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryModels = databaseHelper.getCategoryCostDao().getAllCategoryCost();
        getNamecategory(categoryModels);

        iddocument = costModels.get(position).documentId;
        documentModels = databaseHelper.getDocumentDao().getAllDocument();
        getNamedocument(documentModels);

        viewHolder.commentCost.setText(costModels.get(position).comment);
        viewHolder.sumCost.setText(String.valueOf(costModels.get(position).sum));
        // viewHolder.categoryIncome.setText(String.valueOf(incomeModels.get(position).categoryIncomeId));
        viewHolder.categoryCost.setText(namecategory);
        viewHolder.dateCost.setText(sDate);
        //viewHolder.documentIncome.setText(String.valueOf(incomeModels.get(position).documentId));
        viewHolder.documentCost.setText(namedocument);
    }

    @Override
    public int getItemCount() {
        return costModels.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.sumCost)
        public TextView sumCost;
        @BindView(R.id.commentCost)
        public TextView commentCost;
        @BindView(R.id.dateCost)
        public TextView dateCost;
        @BindView(R.id.categoryCost)
        public TextView categoryCost;
        @BindView(R.id.documentCost)
        public TextView documentCost;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getNamecategory(List<CategoryCost> categoryModels){
        for (CategoryCost c: categoryModels){
            if(idcategory.equals(c.id)){
                namecategory = c.name;
                return;
            }
        }
    }
    private void getNamedocument(List<Document> documentModels){
        for (Document c: documentModels){
            if(iddocument.equals(c.id)){
                namedocument = c.name;
                return;
            }
        }
    }

}