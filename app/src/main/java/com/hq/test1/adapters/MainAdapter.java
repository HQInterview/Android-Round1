package com.hq.test1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hq.test1.utils.DataModel;
import com.hq.test1.R;

import java.util.ArrayList;

/**
 * Created by nami on 4/5/16.
 */
public class MainAdapter extends BaseAdapter {
    private ArrayList<DataModel> data = new ArrayList<>();

    public MainAdapter() {
    }

    static class ViewHolder {
        TextView titleTV;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.
                    inflate(R.layout.row_layout, parent, false);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.row_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.titleTV.setText(data.get(position).getTitle());
        return convertView;
    }

    public void setData(ArrayList<DataModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}