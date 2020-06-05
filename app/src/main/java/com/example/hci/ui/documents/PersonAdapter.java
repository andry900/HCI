package com.example.hci.ui.documents;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hci.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonAdapter extends RecyclerView.Adapter<com.example.hci.ui.documents.PersonAdapter.MyViewHolder> {
        private final ArrayList<NotifyPerson.Person> data;

        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView type;
            ImageView img;
            CheckBox selected;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name =  itemView.findViewById(R.id.name_person);
                type = itemView.findViewById(R.id.person_type);
                img = itemView.findViewById(R.id.person_img);
                selected = itemView.findViewById(R.id.sel_persons);
            }
        }

        PersonAdapter(ArrayList<NotifyPerson.Person> savedDocs) {
            data = savedDocs;
        }

        @NonNull
        @Override
        public com.example.hci.ui.documents.PersonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_item, parent, false);

            return new com.example.hci.ui.documents.PersonAdapter.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.hci.ui.documents.PersonAdapter.MyViewHolder holder, int position) {
            if (data.get(position) != null) {
                (holder.name).setText(data.get(position).getName());
                (holder.type).setText(data.get(position).getType());
                (holder.selected).setChecked(data.get(position).getSel());
                Bitmap tmp_img = data.get(position).getImg();
                if (tmp_img!= null) {
                    (holder.img).setImageBitmap(tmp_img);
                }
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }
