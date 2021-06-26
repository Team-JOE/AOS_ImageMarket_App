package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.aosproject.imagemarket.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.MessageDigest;

public class StartActivity extends AppCompatActivity {

    Button signinButton, signUpButton;
    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        signinButton = findViewById(R.id.button_sign_in_dy);
        signUpButton = findViewById(R.id.button_sign_up_dy);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //SharedPreferences 받아오기.
        SharedPreferences auto = getSharedPreferences("signInState", Activity.MODE_PRIVATE);

        String loginEmail = auto.getString("emailId", null);

        if (loginEmail != null) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }



//    private void HashKey() {
//        try {
//            PackageInfo pkinfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : pkinfo.signatures) {
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                String result = new String(Base64.encode(messageDigest.digest(), 0));
//                Log.d("해시", result);
//            }
//        }
//        catch (Exception e) {
//        }
//    }
}