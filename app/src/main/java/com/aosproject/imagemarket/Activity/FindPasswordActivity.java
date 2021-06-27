package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.aosproject.imagemarket.NetworkTask.FindPwdNetworkTask;
import com.aosproject.imagemarket.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class FindPasswordActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputEditText editText;
    Button button;
    ImageView back;
    String newPwd;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_find_password);
        newPwd = findPwd();


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

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }
        });
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText.getText().toString();

                sendEmail();
            }
        });
    }

    private void sendEmail(){
        String user = email; // 받는사람
        String subject = "Im Joy 비밀번호 재설정"; // 제목
        String message = "임시 비밀번호는 " + newPwd + "입니다. 비밀번호를 재설정해주세요."; // 내용


        FindPwdNetworkTask networkTask = new FindPwdNetworkTask(FindPasswordActivity.this, user, subject, message);
        networkTask.execute();
    }

    private String findPwd() {
        String newPwd = "";
        for (int i=1; i<=7; i++) {
            Random rand = new Random();
            int random = rand.nextInt(10);
            String str = Integer.toString(random);
            newPwd += str;
        }

        return newPwd;
    }
}