package com.example.newsapp.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.interfaces.OnClick;

import java.util.List;

public class SearchRecyclerView extends RecyclerView.Adapter<SearchRecyclerView.SearchViewHolder> {

    List<String> data;
    OnClick listener;

    public SearchRecyclerView(List<String> data, OnClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchRecyclerView.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.field,null,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerView.SearchViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView txt_field;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_field=itemView.findViewById(R.id.txt_field);
            itemView.setOnClickListener(view -> {
                //send the data to the SearchedNewsFragment
                listener.onClickItem(data.get(getAdapterPosition()));
            });
        }
        void onBind(String field){
            txt_field.setText(field);
        }
    }
}
