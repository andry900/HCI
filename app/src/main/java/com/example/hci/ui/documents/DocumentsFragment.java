package com.example.hci.ui.documents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci.MainActivity;
import com.example.hci.R;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;

public class DocumentsFragment extends Fragment {
    static ArrayList<Document> savedDocs;
    public static ArrayList<NotifyPerson.Person> personsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_documents, container, false);
        personsList = null;
        RecyclerView docList = root.findViewById(R.id.docs_list);
        Button newDocBtn = root.findViewById(R.id.newDoc_btn);
        if (savedDocs == null) {
            savedDocs = new ArrayList<>(); 
            savedDocs.add(new Document("February Energy Bill", "UTILITY", "payed",
                    null, null, Float.valueOf("256.98")));
            savedDocs.add(new Document("February telephone Bill", "UTILITY", "payed",
                    null, null, Float.valueOf("123.68")));
            savedDocs.add(new Document("telephone contract", "CONTRACT", "payed",
                    null, null, null));
        }


        docList.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        docList.setLayoutManager(lm);
        DocumentListAdapter mAdapter = new DocumentListAdapter(savedDocs);
        docList.setAdapter(mAdapter);

        newDocBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseLabel.class);
            startActivityForResult(intent,2);
        });

        RecyclerItemClickSupport.addTo(docList).setOnItemClickListener(
                (recyclerView, position, v) -> {
                    DocumentVisualization docFrag = new DocumentVisualization();
                    Bundle b = new Bundle();
                    b.putInt("item", position);
                    docFrag.setArguments(b);
                    getActivity().findViewById(R.id.app_bar_search).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, docFrag,
                                    "visualizeDoc")
                            .addToBackStack(null)
                            .commit();
                });

        SearchView sv = getActivity().findViewById(R.id.search_field);
        sv.setQueryHint("Search documents by name");
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setIconified(false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filterList(query);
                sv.clearFocus();
                hideKeyboard(getActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mAdapter.setList(savedDocs);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
        mAdapter.filterList(String.valueOf(sv.getQuery()));

        return root;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String selector = Objects.requireNonNull(data).getStringExtra("label");
                getActivity().findViewById(R.id.app_bar_search).setVisibility(View.GONE);
                getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                InsertNewDocument newDocFragment = new InsertNewDocument();
                Bundle bundle = new Bundle();
                bundle.putString("label", selector);
                newDocFragment.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, newDocFragment, "newDocFragment")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    public static class DocsFilterClass extends Filter {

        private ArrayList<Document> docList;
        private ArrayList<Document> filteredDocsList;
        private DocumentListAdapter adapter;

        public DocsFilterClass(ArrayList<Document> docList, DocumentListAdapter adapter) {
            this.adapter = adapter;
            this.docList = docList;
            this.filteredDocsList = new ArrayList();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredDocsList.clear();
            final FilterResults results = new FilterResults();

            //here you need to add proper items do filteredContactList
            for (final Document item : docList) {
                if (item.getName().toLowerCase().trim().contains(constraint)) {
                    filteredDocsList.add(item);
                }
            }

            results.values = filteredDocsList;
            results.count = filteredDocsList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.setList(filteredDocsList);
            adapter.notifyDataSetChanged();
        }
    }

    public static class Document{

        private String name;
        private String label;
        private String status;
        private Bitmap img;
        private String utility;
        private Float amount;

        Document(String name, String label, String status, Bitmap img, String utility, Float amount) {
            this.name = name;
            this.label = label;
            this.status = status;
            this.img = img;
            this.utility = utility;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Bitmap getImg() {
            return img;
        }

        public void setImg(Bitmap img) {
            this.img = img;
        }

        public String getUtility() {
            return utility;
        }

        public void setUtility(String utility) {
            this.utility = utility;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }
}
