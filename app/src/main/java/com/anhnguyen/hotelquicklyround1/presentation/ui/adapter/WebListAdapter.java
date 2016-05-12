/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.presentation.ui.adapter;

import com.anhnguyen.hotelquicklyround1.R;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WebListAdapter extends RecyclerView.Adapter<WebListAdapter.ViewHolder>{


    // ===========================================================
    // Constants
    // ===========================================================

    private static final int FIRST_STYLE = 1;
    private static final int SECOND_STYLE = 2;

    private static final String TAG = "WebListAdapter";

    // ===========================================================
    // Fields
    // ===========================================================

    List<Web> items = new ArrayList<>();

    // ===========================================================
    // Constructors
    // ===========================================================

    public WebListAdapter(){

    }

    // ===========================================================
    // Methods from SuperClass/Interfaces
    // ===========================================================

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Web web = items.get(position);
        holder.textView.setText(web.title);
        HLog.d(TAG, "item name " + web.title + " web " + web);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Open webview " + items.get(position).url, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return FIRST_STYLE;
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Web item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void setItems(List<Web> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    // ===========================================================
    // Inner class
    // ===========================================================

    static class ViewHolder extends RecyclerView.ViewHolder{

        public final View view;
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textView = (TextView) view.findViewById(R.id.tv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText();
        }
    }


}
