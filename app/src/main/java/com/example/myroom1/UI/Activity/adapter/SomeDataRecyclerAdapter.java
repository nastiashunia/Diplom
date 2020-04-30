package com.example.myroom1.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom1.App;
import com.example.myroom1.DB.DatabaseHelper;
import com.example.myroom1.DB.Model.CategoryIncome;
import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SomeDataRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Income> incomeModels = new ArrayList<>();
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private OnDeleteListener onDeleteListener;
    private Context context;
    Long date;
    Long idcategory;
    String namecategory;

    public SomeDataRecyclerAdapter(Context context, List<Income> incomeModels) {
        this.context = context;
        this.incomeModels = incomeModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_data, parent, false);
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
        viewHolder.commentIncome.setText(incomeModels.get(position).comment);
        viewHolder.sumIncome.setText(String.valueOf(incomeModels.get(position).sum));
       // viewHolder.categoryIncome.setText(String.valueOf(incomeModels.get(position).categoryIncomeId));
        viewHolder.categoryIncome.setText(namecategory);
        viewHolder.dateIncome.setText(sDate);
        viewHolder.documentIncome.setText(String.valueOf(incomeModels.get(position).documentId));
    }

    @Override
    public int getItemCount() {
        return incomeModels.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.delete)
        public TextView delete;

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
                onDeleteListener.onDelete(incomeModels.get(getAdapterPosition()));
                incomeModels.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
        }
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(Income incomeModel);
    }

    private void getNamecategory(List<CategoryIncome> categoryModels){
        for (CategoryIncome c: categoryModels){
            if(idcategory.equals(c.id)){
                namecategory = c.name;
                return;
            }
        }
    }
}