package com.aosproject.imagemarket.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aosproject.imagemarket.Activity.CategoryActivity;
import com.aosproject.imagemarket.Activity.SearchResultActivity;
import com.aosproject.imagemarket.Adapter.PopularImageAdapter;
import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.NetworkTask.ImageNetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;

import java.util.ArrayList;

public class RecommendFragment extends Fragment {

    FrameLayout photo, illust, calligraphy;
    GridView gridView;
    PopularImageAdapter adapter;
    ArrayList<JsonImageDY> images = new ArrayList<JsonImageDY>();
    String urlAddr = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend,container,false);

        photo = view.findViewById(R.id.layout_show_photo);
        illust = view.findViewById(R.id.layout_show_illust);
        calligraphy = view.findViewById(R.id.layout_show_calligraphy);
        gridView = view.findViewById(R.id.grid_popular);

        urlAddr = ShareVar.macIP +"/jsp/popular_select.jsp";
        Log.i("JSP",urlAddr);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show photo search
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", "1");
                intent.putExtra("title","사진");
                startActivity(intent);
            }
        });

        illust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show illust search
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", "2");
                intent.putExtra("title","일러스트");
                startActivity(intent);
            }
        });

        calligraphy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show calligraphy
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", "3");
                intent.putExtra("title","캘리그라피");
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Popular", urlAddr);
        connectGetData();
    }

    private void connectGetData(){
        try {
            ImageNetworkTaskDY networkTask = new ImageNetworkTaskDY(getContext(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            images = (ArrayList<JsonImageDY>) obj;
            adapter = new PopularImageAdapter(getContext(), R.layout.card_grid_popular, images);
            gridView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
