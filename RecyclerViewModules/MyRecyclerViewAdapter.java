package com.example.taras.firstlab.RecyclerViewModules;

import android.content.ContentUris;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.firstlab.ApiConst;
import com.example.taras.firstlab.IContentRefresher;
import com.example.taras.firstlab.MagicCast;
import com.example.taras.firstlab.MainActivity;
import com.example.taras.firstlab.R;

import java.util.ArrayList;

/**
 * Created by Taras on 07/12/2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private static ArrayList<String> strings = new ArrayList<>();
    private IContentRefresher refresher;

    public MyRecyclerViewAdapter(IContentRefresher refresher) {
        this.refresher = refresher;
    }

    public void initList(ArrayList<String> strings) {
        MyRecyclerViewAdapter.strings = strings;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        itemView.setTag(0);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        String s = strings.get(position);
        holder.field.setText(MagicCast.getValue(s));
        holder.id = MagicCast.getId(s);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri uri = ContentUris.withAppendedId(ApiConst.CONTENT_URI, holder.id);
                int cnt = MainActivity.contentResolver.delete(uri, null, null);
                Log.v("tarastaras", Integer.toString(cnt));
                refresher.refresh();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
}
