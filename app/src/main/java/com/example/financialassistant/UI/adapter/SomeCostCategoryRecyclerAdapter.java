package com.example.financialassistant.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryCost;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SomeCostCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryCost> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private SomeCostCategoryRecyclerAdapter.OnDeleteListener onDeleteListener;
    private SomeCostCategoryRecyclerAdapter.OnClickListener onClickListener;
    private Context context;
    int i;
    public SomeCostCategoryRecyclerAdapter(Context context, List<CategoryCost> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_category, parent, false);
        return new SomeCostCategoryRecyclerAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final SomeCostCategoryRecyclerAdapter.NewsViewHolder viewHolder = (SomeCostCategoryRecyclerAdapter.NewsViewHolder) holder;
        viewHolder.name_category.setText(categoryModels.get(position).name);
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.delete)
        public ImageButton delete;
        @BindView(R.id.up)
        public ImageButton up;

        @BindView(R.id.name_category)
        public TextView name_category;


        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            delete.setOnClickListener(view -> {
                onClickListener.onDelete(categoryModels.get(getAdapterPosition()));
                i = getAdapterPosition();
            });
            up.setOnClickListener(view -> {
                onClickListener.onUp(categoryModels.get(getAdapterPosition()));
            });
        }
    }
    public interface OnClickListener {
        void onDelete(CategoryCost categoryModel);
        void onUp(CategoryCost categoryModel);
    }
    public void setOnClickListener(SomeCostCategoryRecyclerAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnDeleteListener(SomeCostCategoryRecyclerAdapter.OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(CategoryCost categoryModel);
    }

    public  void  f() {
        categoryModels.remove(i);
        notifyItemRemoved(i);
    }
}