package com.example.hci.ui.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hci.R;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyViewHolder> {
    private final DocumentsFragment.DocsFilterClass filter;
    private ArrayList<DocumentsFragment.Document> data;
    private ArrayList<DocumentsFragment.Document> filteredData;


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView label;
        TextView status;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.doc_name);
            status = itemView.findViewById(R.id.doc_status);
            label = itemView.findViewById(R.id.doc_label);
        }
    }

    DocumentListAdapter(ArrayList<DocumentsFragment.Document> savedDocs) {
        data = savedDocs;
        filteredData = savedDocs;
        filter = new DocumentsFragment.DocsFilterClass(savedDocs, this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doc_item, parent, false);

        return new DocumentListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (filteredData.get(position) != null) {
            (holder.name).setText(filteredData.get(position).getName());
            (holder.status).setText(filteredData.get(position).getStatus());
            (holder.label).setText(filteredData.get(position).getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    // set adapter filtered list
    public void setList(ArrayList<DocumentsFragment.Document> list) {
        this.filteredData = list;
    }
    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);
    }
}
