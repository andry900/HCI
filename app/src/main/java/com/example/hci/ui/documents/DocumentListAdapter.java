package com.example.hci.ui.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hci.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyViewHolder> {
    private final ArrayList<DocumentsFragment.Document> data;

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
        if (data.get(position) != null) {
            (holder.name).setText(data.get(position).getName());
            (holder.status).setText(data.get(position).getStatus());
            (holder.label).setText(data.get(position).getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
