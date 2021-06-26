package com.aosproject.imagemarket.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aosproject.imagemarket.Adapter.SearchHistoryAdapter;
import com.aosproject.imagemarket.Bean.HistoryModel;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.SearchHelper;

import java.util.ArrayList;

public class SearchHistoryFragment extends Fragment {


    ListView listView;
    SearchHelper searchHelper;
    SearchHistoryAdapter adapter;
    ArrayList<HistoryModel> histories = new ArrayList<HistoryModel>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        listView = view.findViewById(R.id.list_history);
        searchHelper = new SearchHelper(getActivity());
        adapter = new SearchHistoryAdapter(getContext(), histories, R.layout.card_search_history);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        SQLiteDatabase DB;
        try {
            histories.clear();
            DB = searchHelper.getReadableDatabase();
            String query = "SELECT id, searched FROM searchdata;";
            Cursor cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String searchData = cursor.getString(1);

                HistoryModel history = new HistoryModel(id, searchData);
                histories.add(history);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            adapter = new SearchHistoryAdapter(getContext(), histories, R.layout.card_search_history);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
