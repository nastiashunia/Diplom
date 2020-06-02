package com.example.financialassistant.UI.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.Document;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SomeDocumentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Document> documentModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private OnDeleteListener onDeleteListener;
    private OnClickListener onClickListener;
    private Context context;
    Long date_start;
    Long date_finish;
int i;
    public SomeDocumentRecyclerAdapter(Context context, List<Document> documentModels) {
        this.context = context;
        this.documentModels = documentModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_some_document, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        date_start = documentModels.get(position).start_date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String sDate = sdf.format(date_start);

        date_finish=documentModels.get(position).finish_date;
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
        String fDate = sdf1.format(date_finish);
       // viewHolder.commentIncome.setText(documentModels.get(position).name);

        viewHolder.name_document.setText(documentModels.get(position).name);
        viewHolder.date_start_document.setText(sDate);
        viewHolder.date_finish_document.setText(fDate);
    }

    @Override
    public int getItemCount() {
        return documentModels.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.delete)
        public ImageButton delete;
        @BindView(R.id.up)
        public ImageButton up;

        @BindView(R.id.date_start_document)
        public TextView date_start_document;
        @BindView(R.id.date_finish_document)
        public TextView date_finish_document;
        @BindView(R.id.name_document)
        public TextView name_document;


        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            delete.setOnClickListener(view -> {
                onClickListener.onDelete(documentModels.get(getAdapterPosition()));
                i = getAdapterPosition();
             //   documentModels.remove(getAdapterPosition());
              //  notifyItemRemoved(getAdapterPosition());
            });
            up.setOnClickListener(view -> {
                onClickListener.onUp(documentModels.get(getAdapterPosition()));
                // incomeModels.remove(getAdapterPosition());
                //notifyItemRemoved(getAdapterPosition());
            });
        }
    }
    public interface OnClickListener {
        void onDelete(Document documentModel);
        void onUp(Document documentModel);
    }
    public void setOnClickListener(SomeDocumentRecyclerAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(Document documentModel);
    }

    public  void  f() {
        documentModels.remove(i);
        notifyItemRemoved(i);
    }


}