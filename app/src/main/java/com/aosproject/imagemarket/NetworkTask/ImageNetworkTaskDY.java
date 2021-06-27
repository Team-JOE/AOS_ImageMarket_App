package com.aosproject.imagemarket.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aosproject.imagemarket.Bean.JsonImageDY;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageNetworkTaskDY extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ArrayList<JsonImageDY> images;

    ProgressDialog progressDialog = null;

    String where = null;

    public ImageNetworkTaskDY(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.images = images;
        this.images = new ArrayList<JsonImageDY>();
        this.progressDialog = progressDialog;
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");

                }
                switch (where) {
                    case "select":
                        parserSelect(stringBuffer.toString());
                        break;
                    default:
                        result = parserAction(stringBuffer.toString());
                        break;

                }
            }

        } catch (Exception e) {
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

        switch (where) {
            case "select":
                return images;
            default:
                return result;
        }
    }

    private void parserSelect(String str){
        try{
            images.clear();
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("image_info"));
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int imageCode = jsonObject1.getInt("code");
                String imageFilepath = jsonObject1.getString("filepath");
                Log.i("Image", imageFilepath);
                JsonImageDY image = new JsonImageDY(imageCode, imageFilepath);

                images.add(image);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parserAction(String str){
        String returnValue = null;
        try{
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");


        }catch (Exception e){
            e.printStackTrace();
        }

        return returnValue;
    }
}
