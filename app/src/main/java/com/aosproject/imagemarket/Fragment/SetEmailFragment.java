package com.aosproject.imagemarket.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aosproject.imagemarket.Bean.UserModel;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SetEmailFragment extends Fragment {

    TextInputLayout textInputLayout;
    TextInputEditText editText;
    Button nextButton;
    String email;

    String urlAddr = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_set_email, container, false);

        textInputLayout = view.findViewById(R.id.layout_set_email);
        editText = view.findViewById(R.id.edit_text_set_email);
        nextButton = view.findViewById(R.id.button_go_set_pwd);

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
        nextButton.setOnClickListener(onClickListener);

        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            email = editText.getText().toString();
            urlAddr = ShareVar.macIP + "/jsp/userEmailSelect.jsp?" + "email=" + email;
            Log.e("URL",urlAddr);
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                textInputLayout.setError("올바른 이메일 주소가 아닙니다");
            } else {
                String result = connectSelectData();
                    if(result.equals("exist")) {
                    // 토스트로 이미 가입한 이메일 입니다
                    Toast.makeText(getContext(),"이미 Image Market에 가입된 이메일 주소입니다.",Toast.LENGTH_SHORT).show();
                    } else {
                    //show next
                    Fragment fragment = new SetPwdFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_fragment_sign_up, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    //set string
                    UserModel user = new UserModel();
                    user.setEmail(editText.getText().toString());
                }
        }
        }
    };

    private String connectSelectData() {
        String result = null;
        try {
            NetworkTaskDY networkTask = new NetworkTaskDY(getActivity(), urlAddr, "selectEmail");
            Object obj = networkTask.execute().get();
            result = (String)obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    };
}
