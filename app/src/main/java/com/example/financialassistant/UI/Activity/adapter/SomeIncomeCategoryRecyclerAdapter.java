package com.example.financialassistant.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.CategoryIncome;
import com.example.myroom1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SomeIncomeCategoryRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryIncome> categoryModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private SomeIncomeCategoryRecyclerAdapter.OnDeleteListener onDeleteListener;
    private SomeIncomeCategoryRecyclerAdapter.OnClickListener onClickListener;
    private Context context;
    int i;
    public SomeIncomeCategoryRecyclerAdapter(Context context, List<CategoryIncome> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_category, parent, false);
        return new SomeIncomeCategoryRecyclerAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final SomeIncomeCategoryRecyclerAdapter.NewsViewHolder viewHolder = (SomeIncomeCategoryRecyclerAdapter.NewsViewHolder) holder;
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
                //   documentModels.remove(getAdapterPosition());
                //  notifyItemRemoved(getAdapterPosition());
            });
            up.setOnClickListener(view -> {
                onClickListener.onUp(categoryModels.get(getAdapterPosition()));
                // incomeModels.remove(getAdapterPosition());
                //notifyItemRemoved(getAdapterPosition());
            });
        }
    }
    public interface OnClickListener {
        void onDelete(CategoryIncome categoryModel);
        void onUp(CategoryIncome categoryModel);
    }
    public void setOnClickListener(SomeIncomeCategoryRecyclerAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnDeleteListener(SomeIncomeCategoryRecyclerAdapter.OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(CategoryIncome categoryModel);
    }

    public  void  f() {
        categoryModels.remove(i);
        notifyItemRemoved(i);
    }
}
