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

public class SetAccountFragment extends Fragment {

    String urlAddr,myIP = null;
    String email, pwd, name, phone, bank, owner, account = null;
    EditText ownerEditText, accountEditText;
    Button doneButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_account, container, false);
        ownerEditText = view.findViewById(R.id.edit_text_set_owner);
        accountEditText = view.findViewById(R.id.edit_text_set_account);
        doneButton = view.findViewById(R.id.button_complete_sign_up);

        urlAddr = ShareVar.macIP + "/jsp/userInsertReturn.jsp?";

        doneButton.setOnClickListener(saveAll);

        accountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0 || ownerEditText.getText().toString().trim().length() == 0){
                    doneButton.setText("건너뛰기");
                } else {
                    doneButton.setText("완료");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || ownerEditText.getText().toString().trim().length() == 0){
                    doneButton.setText("건너뛰기");
                } else {
                    doneButton.setText("완료");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0 || ownerEditText.getText().toString().trim().length() == 0){
                    doneButton.setText("건너뛰기");
                } else {
                    doneButton.setText("완료");
                }
            }
        });
        return view;
    }

    // 버튼 눌렀을때 키보드 내려가게
    View.OnClickListener saveAll = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Insert user data
            UserModel user = new UserModel();
            email = user.getEmail();
            pwd = user.getPwd();
            name = user.getName();
            phone = user.getPhoneNumber();
            if (ownerEditText.getText().toString().length()==0 || accountEditText.getText().toString().length()==0) {
                bank = "'none'";
                owner = "'none'";
                account = "'none'";
            } else {
                bank = "카카오뱅크";
                owner = ownerEditText.getText().toString();
                account = accountEditText.getText().toString();
            }
            urlAddr = urlAddr + "email=" + email + "&pwd=" + pwd +"&name=" + name + "&phone=" + phone + "&bank=" + bank + "&owner=" + owner + "&accountnum=" + account;
            String result = connectInsertData();
            if(result.equals("1")){
                Fragment fragment = new CompleteSignUpFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fragment_sign_up, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getActivity(), "입력실패.", Toast.LENGTH_SHORT).show();
            }
        }
    };

//    View.OnClickListener saveInfo = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //Insert user data
//            UserModel user = new UserModel();
//            email = user.getEmail();
//            pwd = user.getPwd();
//            name = user.getName();
//            phone = user.getPhoneNumber();
//            urlAddr = urlAddr + "email=" + email + "&pwd=" + pwd +"&name=" + name + "&phone=" + phone+ "&bank='none'&owner='none'&account='none'";
//            String result = connectInsertData();
//            if(result.equals("1")){
//                Fragment fragment = new CompleteSignUpFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_fragment_sign_up, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            } else {
//                Toast.makeText(getActivity(), "입력실패.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

    private String connectInsertData(){
            String result = null;

        try {
            NetworkTaskDY networkTask = new NetworkTaskDY(getContext(), urlAddr, "insert");
            Object obj = networkTask.execute().get();
            result = (String)obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Result",result);
        return result;
    }
}
