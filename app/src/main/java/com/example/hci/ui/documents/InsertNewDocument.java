package com.example.hci.ui.documents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hci.R;

public class InsertNewDocument extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_insert_document);

        Intent intent = getIntent();
        String selector = intent.getStringExtra("label");

        ((TextView)findViewById(R.id.textView2)).setText(selector);
    }

}
