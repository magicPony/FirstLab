package com.example.taras.firstlab.RecyclerViewModules;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.taras.firstlab.R;

/**
 * Created by Taras on 07/12/2016.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView field;
    public int id;

    public CustomViewHolder(View itemView) {
        super(itemView);
        id = (int) itemView.getTag();
        field = (TextView) itemView.findViewById(R.id.et_field_LC);
    }
}
