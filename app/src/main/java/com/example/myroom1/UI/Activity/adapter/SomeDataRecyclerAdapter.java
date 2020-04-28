package com.example.myroom1.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom1.DB.Model.Income;
import com.example.myroom1.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SomeDataRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Income> incomeModels = new ArrayList<>();
    private OnDeleteListener onDeleteListener;
    private Context context;

    public SomeDataRecyclerAdapter(Context context, List<Income> dataModels) {
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

        viewHolder.commentIncome.setText(incomeModels.get(position).comment);
        viewHolder.sumIncome.setText(String.valueOf(incomeModels.get(position).sum));
        viewHolder.categoryIncome.setText(String.valueOf(incomeModels.get(position).categoryIncomeId));
        viewHolder.dateIncome.setText(String.valueOf(incomeModels.get(position).date));
        viewHolder.documentIncome.setText(String.valueOf(incomeModels.get(position).documentId));
        //viewHolder.setText(Str.vof(commentIncome))= incomeModels.get(position).comment;

        /*viewHolder.description.setText(incomeModels.get(position).getDescription());*/
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
}