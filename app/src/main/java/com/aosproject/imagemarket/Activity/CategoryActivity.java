package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aosproject.imagemarket.Adapter.ImageAdapterDY;
import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.NetworkTask.ImageNetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    String urlAddr = null;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    ArrayList<JsonImageDY> images = null;
    String category;
    ImageAdapterDY adapterDY;
    TextView categoryTitle;
    ImageView back;
    // Adapter View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.category_recycler_view);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        categoryTitle = findViewById(R.id.title_category);
        back = findViewById(R.id.back_image_in_category);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String title = getIntent().getExtras().getString("title");
        categoryTitle.setText(title);

        category = getIntent().getExtras().getString("category");
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData() {
        urlAddr = ShareVar.macIP + "jsp/result_category_all.jsp?category=" + category;
        Log.i("JSP", urlAddr);
        try {
            ImageNetworkTaskDY networkTask = new ImageNetworkTaskDY(CategoryActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            images = (ArrayList<JsonImageDY>) obj;

            if (images.size() == 0) {
                // 검색결과가 없습니다.
            } else {
                adapterDY = new ImageAdapterDY(CategoryActivity.this, images);
                recyclerView.setAdapter(adapterDY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}