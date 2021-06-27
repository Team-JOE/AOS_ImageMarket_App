package com.aosproject.imagemarket.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aosproject.imagemarket.Bean.UserModel;
import com.aosproject.imagemarket.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SetPwdFragment extends Fragment {

    TextInputLayout textInputLayout;
    TextInputEditText editText;
    Button nextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_pwd, container, false);

        textInputLayout = view.findViewById(R.id.layout_set_pwd);
        editText = view.findViewById(R.id.edit_text_set_pwd);
        nextButton = view.findViewById(R.id.button_go_set_name);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //조건문
                if (editText.getText().toString().length() < 8) {
                    textInputLayout.setError("비밀번호는 8자 이상이어야 합니다");
                } else {
                    //show next
                    Fragment fragment = new SetNameFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_fragment_sign_up, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    //set string
                    UserModel user = new UserModel();
                    user.setPwd(editText.getText().toString());
                }
            }
        });

        return view;
    }
}
