package com.example.kosci;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private List<ResultItem> resultList;

    public ResultAdapter(Context context, List<ResultItem> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_result, parent, false);
        }

        ResultItem resultItem = resultList.get(position);

        TextView nickNameTextView = convertView.findViewById(R.id.nickNameTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView pointsTextView = convertView.findViewById(R.id.pointsTextView);

        nickNameTextView.setText(resultItem.getNickName());
        dateTextView.setText(resultItem.getDate());
        pointsTextView.setText(resultItem.getPoints());

        return convertView;
    }
}