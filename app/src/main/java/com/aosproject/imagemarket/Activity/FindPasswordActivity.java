package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.aosproject.imagemarket.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FindPasswordActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputEditText editText;
    Button button;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_find_password);


        textInputLayout = findViewById(R.id.layout_find_email);
        editText = findViewById(R.id.edit_text_find_email);
        button = findViewById(R.id.button_find_password);
        back = findViewById(R.id.back_image_in_find_password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();

                String newPwd = "";

                // 이메일 보내기
            }
        });
    }
}