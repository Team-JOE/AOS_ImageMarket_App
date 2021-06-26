package com.aosproject.imagemarket.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aosproject.imagemarket.Activity.SearchResultActivity;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.SearchHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SearchFragment extends Fragment {

    private TextInputLayout textInputLayout;
    private TextInputEditText searchTextField;
    private Button button;

    private SearchHistoryFragment historyFragment;
    private RecommendFragment recommendFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private SearchHelper helper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container,false);

        textInputLayout = view.findViewById(R.id.text_layout_search);
        searchTextField = view.findViewById(R.id.text_field_search);
        button = view.findViewById(R.id.button_cancel_search);

        historyFragment = new SearchHistoryFragment();
        recommendFragment = new RecommendFragment();

        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_search_tab_fragment, recommendFragment).commitAllowingStateLoss();

        helper = new SearchHelper(getContext());
        // 텍스트필드 실행
        searchTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textInputLayout.setPadding(16,16, 200,16);
                button.setVisibility(View.VISIBLE);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_search_tab_fragment, historyFragment).commitAllowingStateLoss();
            }
        });

        //텍스트필드 Return시
        searchTextField.setOnKeyListener(new View.OnKeyListener() {
            SQLiteDatabase DB;
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        // 데이터 추가
                        String searchedLabel = searchTextField.getText().toString();

                        try {
                            DB = helper.getWritableDatabase();
                            String query = "INSERT INTO searchdata(searched) VALUES ('" + searchedLabel + "');";
                            DB.execSQL(query);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 데이터 Intent
                        Intent intent = null;
                        intent = new Intent(getActivity(), SearchResultActivity.class);
                        intent.putExtra("searched", searchTextField.getText().toString());
                        startActivity(intent);
                }
                return true;
            }
        });

        //취소 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTextField.clearFocus();
                button.setVisibility(View.INVISIBLE);
                textInputLayout.setPadding(16,16, 16,16);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_search_tab_fragment, recommendFragment).commitAllowingStateLoss();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
