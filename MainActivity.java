package com.example.taras.firstlab;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.taras.firstlab.RecyclerViewModules.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyRecyclerViewAdapter recyclerViewAdapter;
    public static ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();

        final RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_AM);
        recyclerViewAdapter = new MyRecyclerViewAdapter(new IContentRefresher() {
            @Override
            public void refresh() {
                recyclerViewAdapter.initList(MagicCast.toStringList(getData()));
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        recyclerView        .setLayoutManager(new LinearLayoutManager(this));
        recyclerView        .setItemAnimator(new DefaultItemAnimator());
        recyclerView        .setAdapter(recyclerViewAdapter);

        findViewById(R.id.btn_add_AM)   .setOnClickListener(this);
        findViewById(R.id.btn_search_AM).setOnClickListener(this);
        updateContent(MagicCast.toStringList(getData()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_AM :
                Intent intent = new Intent(this, CreateActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.btn_search_AM :
                EditText etRequest = (EditText) findViewById(R.id.et_request_AM);
                String request = "";

                if (!TextUtils.isEmpty(etRequest.getText())) {
                    request = etRequest.getText().toString();
                }

                updateContent(MagicCast.getSearchResults(MagicCast.toStringList(getData()), request));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateContent(MagicCast.toStringList(getData()));
    }

    private void updateContent(ArrayList<String> content) {
        recyclerViewAdapter.initList(content);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private Cursor getData() {
        return contentResolver.query(ApiConst.CONTENT_URI, null, null, null, null);
    }
}
