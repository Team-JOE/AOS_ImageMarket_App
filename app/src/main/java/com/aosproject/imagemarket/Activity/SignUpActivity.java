package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aosproject.imagemarket.Fragment.CompleteSignUpFragment;
import com.aosproject.imagemarket.Fragment.SetEmailFragment;
import com.aosproject.imagemarket.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    private SetEmailFragment setEmailFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView back;
    //static String email, pwd, name, phoneNumber, bank, owner, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = findViewById(R.id.back_image_in_signup);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().hide();

        CompleteSignUpFragment testview = new CompleteSignUpFragment();//test 삭제 할 부분

        setEmailFragment = new SetEmailFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment_sign_up, setEmailFragment).commitAllowingStateLoss();

    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public void setPwd(String pwd){
//        this.pwd = pwd;
//    }
//    public void setName(String name){
//        this.name = name;
//    }
//    public void setPhoneNumber(String phoneNumber){
//        this.phoneNumber = phoneNumber;
//    }
//    public void setBank(String bank){
//        this.bank = bank;
//    }
//    public void setOwner(String owner){
//        this.owner = owner;
//    }
//    public void setAccount(String account){
//        this.account = account;
//    }
}