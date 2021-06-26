package com.aosproject.imagemarket.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.aosproject.imagemarket.Bean.JsonUserDY;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTaskDY extends AsyncTask<Integer, String, Object> {

    Context context =null;
    String mAddr = null;
    ArrayList<JsonUserDY> users = null;
    ProgressDialog progressDialog =null;

    // Network Task 를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;

    public NetworkTaskDY(Context context, String mAddr, String where){
        this.context = context;
        this.mAddr = mAddr;
        this.users = users;
        this.users = new ArrayList<JsonUserDY>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        // 어떤 모양으로 할 것인지 결정
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("down...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String result = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strLine = bufferedReader.readLine();

                    if (strLine == null) break;
                    stringBuffer.append(strLine + "\n");
                }

                if (where.equals("select")) {
                    parser(stringBuffer.toString());
                }
                else {
                    result = parserAction(stringBuffer.toString());
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (where.equals("select")){
            return users;
        } else {
            return result;
        }
    }

    private String parserAction(String str){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
            Log.d("Data", " :inset");
        }catch (Exception e){
            e.printStackTrace();
            Log.d("Data Error", "error");
        }
        return returnValue;
    }

    private void parser(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("users_info"));
            users.clear();

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String email = jsonObject1.getString("email");
                String pwd = jsonObject1.getString("pwd");
                String name = jsonObject1.getString("name");
                String phoneNumber = jsonObject1.getString("phoneNumber");
                String bank = jsonObject1.getString("bank");
                String owner = jsonObject1.getString("owner");
                String account = jsonObject1.getString("account");
                JsonUserDY user = new JsonUserDY(email, pwd, name, phoneNumber, bank, owner, account);
                users.add(user);
                }
            } catch (Exception e){
            e.printStackTrace();
        }
    }

//    private void parserEmail(String str) {
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//            email = jsonObject.getString("email");
//            Log.i("Result is ", email);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
