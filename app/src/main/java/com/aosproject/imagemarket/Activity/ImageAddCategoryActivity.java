package com.aosproject.imagemarket.Activity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import com.aosproject.imagemarket.R;
import com.google.android.material.snackbar.Snackbar;
public class ImageAddCategoryActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter = null;
    Spinner spinner = null;
    Button button;
    String filepath, title, detail, fileformat, img_path = null;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add_category);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        filepath = intent.getStringExtra("filepath");
        title = intent.getStringExtra("title");
        detail = intent.getStringExtra("detail");
        fileformat = intent.getStringExtra("fileformat");
        img_path = intent.getStringExtra("img_path");
        adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.add_spinner_category);
        button = findViewById(R.id.add_category_btn_next);
        imageView = findViewById(R.id.add_category_ivbtn_back);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getItemAtPosition(position).equals("이미지 카테고리 선택")){
                    button.setEnabled(false);
                }else {
                    button.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button.setOnClickListener(onClickListener);
        imageView.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_category_btn_next:
                    if(spinner.getSelectedItem().toString().equals("이미지 카테고리 선택")){
                        Snackbar.make(v, "이미지 카테고리를 선택해주세요!", Snackbar.LENGTH_SHORT).show();
                    }else if (spinner.getSelectedItem().toString().equals("사진")){
                        Intent intent = new Intent(ImageAddCategoryActivity.this, ImageAddTagActivity.class);
                        intent.putExtra("filepath", filepath);
                        intent.putExtra("title", title);
                        intent.putExtra("detail", detail);
                        intent.putExtra("fileformat", fileformat);
                        intent.putExtra("category", 1);
                        intent.putExtra("img_path", img_path);
                        startActivity(intent);
                    }else if (spinner.getSelectedItem().toString().equals("일러스트")){
                        Intent intent = new Intent(ImageAddCategoryActivity.this, ImageAddTagActivity.class);
                        intent.putExtra("filepath", filepath);
                        intent.putExtra("title", title);
                        intent.putExtra("detail", detail);
                        intent.putExtra("fileformat", fileformat);
                        intent.putExtra("category", 2);
                        intent.putExtra("img_path", img_path);
                        startActivity(intent);
                    }else if (spinner.getSelectedItem().toString().equals("캘리그라피")){
                        Intent intent = new Intent(ImageAddCategoryActivity.this, ImageAddTagActivity.class);
                        intent.putExtra("filepath", filepath);
                        intent.putExtra("title", title);
                        intent.putExtra("detail", detail);
                        intent.putExtra("fileformat", fileformat);
                        intent.putExtra("category", 3);
                        intent.putExtra("img_path", img_path);
                        startActivity(intent);
                    }
                    break;
                case R.id.add_category_ivbtn_back:
                    finish();
                    break;
            }
        }
    };
}