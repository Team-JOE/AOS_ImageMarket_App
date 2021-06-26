package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.Fragment.SearchResultFragment;
import com.aosproject.imagemarket.NetworkTask.ImageNetworkTaskDY;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.SearchHelper;
import com.aosproject.imagemarket.Util.ShareVar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private String urlAddr = null;
    private TextInputEditText editText;
    private SearchHelper helper;
    private Button back;

    private SearchResultFragment searchResultFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().hide();

        editText = findViewById(R.id.textfield_result_search);
        back = findViewById(R.id.button_back_to_search);
        helper = new SearchHelper(SearchResultActivity.this);

        String searchedText= getIntent().getExtras().getString("searched");

        editText.setText(searchedText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            SQLiteDatabase DB;
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        // 데이터 추가
                        String searchedLabel = editText.getText().toString();
                        Log.e("SqlData",searchedLabel);
                        try {
                            DB = helper.getWritableDatabase();
                            String query = "INSERT INTO searchdata(searched) VALUES ('" + searchedLabel + "');";
                            DB.execSQL(query);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 데이터 Intent
                        Intent intent = null;
                        intent = new Intent(SearchResultActivity.this, SearchResultActivity.class);
                        intent.putExtra("searched", editText.getText().toString());
                        startActivity(intent);
                }
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchResultFragment = new SearchResultFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment_search_result, searchResultFragment).commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}