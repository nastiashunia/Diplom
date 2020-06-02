package com.example.financialassistant.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.financialassistant.DB.Model.Document;
import com.example.financialassistant.DB.Model.Income;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SomeIncomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Income> incomeModels = new ArrayList<>();
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private List<Document> documentModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private OnClickListener onClickListener;
    private Context context;
    Long date;
    Long idcategory;
    String namecategory;
    Long iddocument;
    String namedocument;

    int i;

    public SomeIncomeRecyclerAdapter(Context context, List<Income> incomeModels) {
        this.context = context;
        this.incomeModels = incomeModels;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_income, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        date = incomeModels.get(position).date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String sDate = sdf.format(date);

        idcategory = incomeModels.get(position).categoryIncomeId;
        databaseHelper = App.getInstance().getDatabaseInstance();
        categoryModels = databaseHelper.getCategoryIncomeDao().getAllCategoryIncome();
        getNamecategory(categoryModels);
        if (incomeModels.get(position).documentId == -1)
        {
            viewHolder.documentIncome.setText("");
        }
        else {
            iddocument = incomeModels.get(position).documentId;
            documentModels = databaseHelper.getDocumentDao().getAllDocument();
            getNamedocument(documentModels);
            viewHolder.documentIncome.setText(namedocument);
        }
       /* iddocument = incomeModels.get(position).documentId;
        documentModels = databaseHelper.getDocumentDao().getAllDocument();
        getNamedocument(documentModels);*/

        viewHolder.commentIncome.setText(incomeModels.get(position).comment);
        viewHolder.sumIncome.setText(String.valueOf(incomeModels.get(position).sum));
       // viewHolder.categoryIncome.setText(String.valueOf(incomeModels.get(position).categoryIncomeId));
        viewHolder.categoryIncome.setText(namecategory);
        viewHolder.dateIncome.setText(sDate);
        //viewHolder.documentIncome.setText(String.valueOf(incomeModels.get(position).documentId));
        //viewHolder.documentIncome.setText(namedocument);
    }

    @Override
    public int getItemCount() {
        return incomeModels.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.delete)
        public ImageButton delete;
        @BindView(R.id.up)
        public ImageButton up;

        @BindView(R.id.sumIncome)
        public TextView sumIncome;
        @BindView(R.id.commentIncome)
        public TextView commentIncome;
        @BindView(R.id.dateIncome)
        public TextView dateIncome;
        @BindView(R.id.categoryIncome)
        public TextView categoryIncome;
        @BindView(R.id.documentIncome)
        public TextView documentIncome;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            delete.setOnClickListener(view -> {
                onClickListener.onDelete(incomeModels.get(getAdapterPosition()));
                i = getAdapterPosition();
                //incomeModels.remove(getAdapterPosition());
                //notifyItemRemoved(getAdapterPosition());
            }

            );
            up.setOnClickListener(view -> {
                onClickListener.onUp(incomeModels.get(getAdapterPosition()));
               // incomeModels.remove(getAdapterPosition());
                //notifyItemRemoved(getAdapterPosition());
            });
        }
    }

    public interface OnClickListener {
        void onDelete(Income incomeModel);
        void onUp(Income incomeModel);
    }
    public void setOnClickListener(SomeIncomeRecyclerAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public  void  f() {
        incomeModels.remove(i);
        notifyItemRemoved(i);
    }

    private void getNamecategory(List<CategoryIncome> categoryModels){
        for (CategoryIncome c: categoryModels){
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
