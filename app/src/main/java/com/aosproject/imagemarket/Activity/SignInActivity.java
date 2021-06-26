package com.aosproject.imagemarket.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aosproject.imagemarket.NetworkTask.NetworkTaskDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    Button signInButton, naverButton, kakaoButton;
    TextInputLayout emailTextLayout, pwdTextLayout;
    TextInputEditText emailEditText, pwdEditText;
    TextView findPassword;
    String urlAddr,email,pwd = null;
    SharedPreferences auto = null;
    //Google Sign In
    private SignInButton googleButton;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;

    //Naver Sign In
    OAuthLogin naverOAuthLogin;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        mContext = getApplicationContext();

        auto = getSharedPreferences("signInState", Activity.MODE_PRIVATE);
        //urlAddr = ShareVar.macIP + "/jsp/login.jsp?";

        // 네이버 로그인 set
        naverOAuthLogin = OAuthLogin.getInstance();
        naverOAuthLogin.init(mContext,
                "aEj2hhhrs5o2JI3BNY3e",
                "ZzMZ4LV1zg",
                "Imjoy");

        //구글 로그인 set
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        auth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        emailTextLayout = findViewById(R.id.layout_text_sign_email);
        pwdTextLayout = findViewById(R.id.layout_text_sign_password);
        emailEditText = findViewById(R.id.edit_text_sign_email);
        pwdEditText = findViewById(R.id.edit_text_sign_pwd);

        signInButton = findViewById(R.id.button_do_sign_in);
        googleButton = findViewById(R.id.button_sign_in_by_google);
        naverButton = findViewById(R.id.button_sign_in_by_naver);
        kakaoButton = findViewById(R.id.button_sign_in_by_kakao);

        googleButton.setOnClickListener(googleSignIn);
        naverButton.setOnClickListener(naverSignIn);
        kakaoButton.setOnClickListener(kakaoSignIn);

        findPassword = findViewById(R.id.text_find_password);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                pwd = pwdEditText.getText().toString();
                urlAddr = ShareVar.macIP + "/jsp/login.jsp?";
                urlAddr = urlAddr + "email=" + email + "&pwd=" + pwd;
                String result = connectSignInData();
                if (result.equals("succes")){
                    //Set Share email
                    ShareVar.loginEmail = email;
                    //Set Share preferences
//                    SharedPreferences.Editor editor = auto.edit();
//                    editor.putString("emailId", email);
//                    editor.commit();
                    // Intent
                    Intent intent = null;
                    intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (result.equals("fail")){
                    pwdTextLayout.setError("올바르지 않은 비밀번호를 입력했습니다. 다시 시도해보세요.");
                } else {
                    emailTextLayout.setError("입력하신 이메일 주소에 연결된 계정이 존재하지 않습니다.");
                }
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(pwdEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(pwdEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(pwdEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }
        });

        pwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(emailEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(emailEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                } else if(emailEditText.getText().toString().trim().length()!=0){
                    signInButton.setEnabled(true);
                }
            }
        });

        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    View.OnClickListener googleSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        }
    };

    View.OnClickListener naverSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {
                    if (success) {
//                        String accessToken = naverOAuthLogin.getAccessToken(mContext);
//                        String refreshToken = naverOAuthLogin.getRefreshToken(mContext);
//                        long expireAt = naverOAuthLogin.getExpiresAt(mContext);
//                        String tokenType = naverOAuthLogin.getTokenType(mContext);

                    } else {
                        String errorCode = naverOAuthLogin.getLastErrorCode(mContext).getCode();
                        String errorDesc = naverOAuthLogin.getLastErrorDesc(mContext);
                        //Log.e("Error",errorCode +"&&" +errorDesc)
                        Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();;
                    }
                }
            };
            naverOAuthLogin.startOauthLoginActivity(SignInActivity.this, oAuthLoginHandler);

        }
    };

    View.OnClickListener kakaoSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                resultLogin(account);
            }
        }
    }

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String email = account.getEmail();
                            String name = account.getDisplayName();
                            String result = null;

                            // 구글 아이디 존재 여부 확인
                            urlAddr = ShareVar.macIP + "/jsp/userEmailSelect.jsp?" + "email=" + email;
                            result = connectSignInData();
                            if(result.equals("exist")) {
                                Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                                startActivity(intent);

                                // Set share id
                                ShareVar.loginEmail = email;

                            } else if(result.equals("none")) {
                                String pwd = "google";
                                urlAddr = ShareVar.macIP + "/jsp/userInsertGoogleReturn.jsp?" + "email=" + email + "&pwd=" + pwd +"&name=" + name;

                                result = connectSignInData();
                                if(result.equals("1")){
                                    //Set Share preferences
//                                SharedPreferences.Editor editor = auto.edit();
//                                editor.putString("emailId", email);
//                                editor.commit();
                                    //Intent
                                    Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                                    startActivity(intent);

                                    // Set share id
                                    ShareVar.loginEmail = email;

                                } else {
                                    Log.e("Error",": surver");
                                }
                            }
                        }
                    }
                });
    }

    private String connectSignInData(){
        String result = null;
        try {
            NetworkTaskDY networkTask = new NetworkTaskDY(SignInActivity.this, urlAddr, "login");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        //Log.d("Result",result);
        return result;
    }


}