package com.aosproject.imagemarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.aosproject.imagemarket.Activity.SearchResultActivity;
import com.aosproject.imagemarket.Bean.HistoryModel;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.SearchHelper;

import java.util.ArrayList;

public class SearchHistoryAdapter extends BaseAdapter {

    private Context context = null;
    private int layout = 0;
    private ArrayList<HistoryModel> data = null;
    private LayoutInflater inflater = null;
    private SearchHelper searchHelper;

    public SearchHistoryAdapter(Context context, ArrayList<HistoryModel> data, int layout) {
        this.context = context;
        this.data = data;
        this.layout = layout;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.text_search_history);

        textView.setText(data.get(position).getText());

        Button deleteButton = convertView.findViewById(R.id.button_delete_history);

        searchHelper = new SearchHelper(convertView.getContext());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase DB;
            @Override
            public void onClick(View v) {
                try {
                    DB = searchHelper.getWritableDatabase();
                    String query = "DELETE FROM searchdata WHERE id = " + data.get(position).getId() + ";";
                    DB.execSQL(query);
                    searchHelper.close();
                    data.remove(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchResultActivity.class);
                intent.putExtra("searched", data.get(position).getText());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
