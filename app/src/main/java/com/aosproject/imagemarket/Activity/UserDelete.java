package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aosproject.imagemarket.NetworkTask.NetworkTaskRecommendList;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskUserDelete;
import com.aosproject.imagemarket.R;

import static com.aosproject.imagemarket.Util.ShareVar.loginEmail;
import static com.aosproject.imagemarket.Util.ShareVar.macIP;

public class UserDelete extends Activity {

    TextView profile_tv_user_delete;
    ImageView back;
    SharedPreferences auto = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);

        profile_tv_user_delete = findViewById(R.id.profile_tv_user_delete);
        back = findViewById(R.id.profile_iv_user_delete);

        profile_tv_user_delete.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profile_tv_user_delete:
                    String result = connectInsertData();
                    if(result.equals("0")) {
                        Toast.makeText(UserDelete.this, "회원 탈퇴를 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UserDelete.this, "회원 탈퇴 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        // Intent 첫화면
                        auto = getSharedPreferences("signInState", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.putString("emailId", null);
                        editor.commit();
                        Intent intentStart = new Intent(UserDelete.this, StartActivity.class);
                        startActivity(intentStart);
                    }
                    break;
                case R.id.profile_iv_user_delete:
                    finish();
                    break;
            }
        }
    };

    private String connectInsertData() {

        String urlAddr = macIP + "jsp/profile_userdelete.jsp?loginEmail=" + loginEmail;
        String result = null;

        try {
            NetworkTaskUserDelete networkTask = new NetworkTaskUserDelete(UserDelete.this, urlAddr);
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}