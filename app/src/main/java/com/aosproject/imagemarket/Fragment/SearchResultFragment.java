package com.aosproject.imagemarket.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aosproject.imagemarket.Adapter.ImageAdapterDY;
import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.NetworkTask.ImageNetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {
    String urlAddr = null;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    ArrayList<JsonImageDY> images = null;
    String searched;
    ImageAdapterDY adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        recyclerView = view.findViewById(R.id.searched_recycler_view);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);

        searched = getActivity().getIntent().getExtras().getString("searched");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData() {
        urlAddr = ShareVar.macIP + "jsp/result_search_all.jsp?searched=" + searched;
        Log.i("JSP", urlAddr);
        try {
            ImageNetworkTaskDY networkTask = new ImageNetworkTaskDY(getContext(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            images = (ArrayList<JsonImageDY>) obj;
            if (images.size() == 0) {
                // 검색 결과가 없습니다
            } else {
                adapter = new ImageAdapterDY(getContext(), images);
                recyclerView.setAdapter(adapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
