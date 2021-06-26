package com.aosproject.imagemarket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aosproject.imagemarket.Adapter.RecommendListAdapter;
import com.aosproject.imagemarket.Bean.RecommendListBean;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskRecommendList;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.RecommendListClickListener;

import java.util.ArrayList;

import static com.aosproject.imagemarket.Util.ShareVar.loginEmail;
import static com.aosproject.imagemarket.Util.ShareVar.macIP;

public class RecommendList extends Activity implements RecommendListClickListener {

    String urlAddr = null;
    ArrayList<RecommendListBean> recommendlist;
    RecommendListAdapter adapter;

    ListView profile_lv_recommendlist_list;
    ImageView profile_iv_recommendlist_back;
    LinearLayout profile_layout_recommendlist_noitem, profile_layout_recommendlist_yesitem, profile_layout_recommendlist_buylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);

        profile_iv_recommendlist_back = findViewById(R.id.profile_iv_recommendlist_back);
        profile_lv_recommendlist_list = findViewById(R.id.profile_lv_recommendlist_list);
        profile_layout_recommendlist_noitem = findViewById(R.id.profile_layout_recommendlist_noitem);
        profile_layout_recommendlist_yesitem = findViewById(R.id.profile_layout_recommendlist_yesitem);
        profile_layout_recommendlist_buylist = findViewById(R.id.profile_layout_recommendlist_buylist);

        urlAddr = macIP + "jsp/profile_recommendlist.jsp?loginEmail=" + loginEmail;

        profile_iv_recommendlist_back.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectGetData();
    }

    private void connectGetData() {
        try {

            NetworkTaskRecommendList networkTask = new NetworkTaskRecommendList(RecommendList.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            recommendlist = (ArrayList<RecommendListBean>) obj;

            if(recommendlist.size() == 0) {
                profile_layout_recommendlist_yesitem.setVisibility(View.INVISIBLE);
                profile_layout_recommendlist_noitem.setVisibility(View.VISIBLE);
                profile_layout_recommendlist_buylist.setOnClickListener(onClickListener);
            }else {
                profile_layout_recommendlist_yesitem.setVisibility(View.VISIBLE);
                profile_layout_recommendlist_noitem.setVisibility(View.INVISIBLE);

                adapter = new RecommendListAdapter(RecommendList.this, R.layout.recommendlist_innerlist, recommendlist, this::onRecommendListClickListener);
                profile_lv_recommendlist_list.setAdapter(adapter);
            }


        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profile_iv_recommendlist_back:
                    finish();
                    break;
                case R.id.profile_layout_recommendlist_buylist:
                    Intent intent = new Intent(RecommendList.this, BuyList.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    // 업데이트
    @Override
    public void onRecommendListClickListener(boolean isClicked) {
        onResume();
    }
}