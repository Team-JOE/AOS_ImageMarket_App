package com.aosproject.imagemarket.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aosproject.imagemarket.Activity.BuyList;
import com.aosproject.imagemarket.Activity.ImageAddImageActivity;
import com.aosproject.imagemarket.Activity.ImgList;
import com.aosproject.imagemarket.Activity.MyPage;
import com.aosproject.imagemarket.Activity.RecommendList;
import com.aosproject.imagemarket.Activity.SellList;
import com.aosproject.imagemarket.Activity.SellReport;
import com.aosproject.imagemarket.Activity.StartActivity;
import com.aosproject.imagemarket.Activity.UserDelete;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskProfileMain;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;

import static com.aosproject.imagemarket.Util.ShareVar.loginEmail;
import static com.aosproject.imagemarket.Util.ShareVar.macIP;

public class ProfileFragment extends Fragment {

    LinearLayout profile_linearlayout_mypage, profile_layout_buy_list, profile_layout_sell_list, profile_layout_like_list;
    TextView profile_tv_name, profile_tv_buy_num, profile_tv_sell_num, profile_tv_like_num, profile_tv_img_list, profile_tv_img_add, profile_tv_sell_report, profile_tv_logout, profile_tv_user_delete;
    String name, buy, sell, recommend;
//    String urlAddr;
    SharedPreferences auto = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       setHasOptionsMenu(true);
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_linearlayout_mypage = view.findViewById(R.id.profile_linearlayout_mypage);
        profile_tv_name = view.findViewById(R.id.profile_tv_name);
        profile_layout_buy_list = view.findViewById(R.id.profile_layout_buy_list);
        profile_tv_buy_num = view.findViewById(R.id.profile_tv_buy_num);
        profile_layout_sell_list = view.findViewById(R.id.profile_layout_sell_list);
        profile_tv_sell_num = view.findViewById(R.id.profile_tv_sell_num);
        profile_layout_like_list = view.findViewById(R.id.profile_layout_like_list);
        profile_tv_like_num = view.findViewById(R.id.profile_tv_like_num);
        profile_tv_img_list = view.findViewById(R.id.profile_tv_img_list);
        profile_tv_img_add = view.findViewById(R.id.profile_tv_img_add);
        profile_tv_sell_report = view.findViewById(R.id.profile_tv_sell_report);
        profile_tv_logout = view.findViewById(R.id.profile_tv_logout);
        profile_tv_user_delete = view.findViewById(R.id.profile_tv_user_delete);

        profile_linearlayout_mypage.setOnClickListener(onClickListener);
        profile_layout_buy_list.setOnClickListener(onClickListener);
        profile_layout_sell_list.setOnClickListener(onClickListener);
        profile_layout_like_list.setOnClickListener(onClickListener);
        profile_tv_img_list.setOnClickListener(onClickListener);
        profile_tv_img_add.setOnClickListener(onClickListener);
        profile_tv_sell_report.setOnClickListener(onClickListener);
        profile_tv_logout.setOnClickListener(onClickListener);
        profile_tv_user_delete.setOnClickListener(onClickListener);

        return view;
    }

    // onResume에서 데이터를 불러와야 계속해서 정보를 바꿔도 리스트가 업데이트 됨
    @Override
    public void onResume() {
        super.onResume();

        connectGetData();

        profile_tv_name.setText(name);
        profile_tv_buy_num.setText(buy);
        profile_tv_sell_num.setText(sell);
        profile_tv_like_num.setText(recommend);
    }

    private void connectGetData() {
        try {
            NetworkTaskProfileMain networkTaskName = new NetworkTaskProfileMain(getActivity(), macIP + "jsp/profile_main_name.jsp?loginEmail=" + loginEmail, "profile_main");
            Object objName = networkTaskName.execute().get();
            name = (String) objName;

            NetworkTaskProfileMain networkTaskBuy = new NetworkTaskProfileMain(getActivity(), macIP + "jsp/profile_main_buy.jsp?loginEmail=" + loginEmail, "profile_main");
            Object objBuy = networkTaskBuy.execute().get();
            buy = (String) objBuy;

            NetworkTaskProfileMain networkTaskSell = new NetworkTaskProfileMain(getActivity(), macIP + "jsp/profile_main_sell.jsp?loginEmail=" + loginEmail, "profile_main");
            Object objSell = networkTaskSell.execute().get();
            sell = (String) objSell;

            NetworkTaskProfileMain networkTaskRecommend = new NetworkTaskProfileMain(getActivity(), macIP + "jsp/profile_main_recommend.jsp?loginEmail=" + loginEmail, "profile_main");
            Object objRecommend = networkTaskRecommend.execute().get();
            recommend = (String) objRecommend;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch(v.getId()) {
                case R.id.profile_linearlayout_mypage:
                    intent = new Intent(getActivity(), MyPage.class);
                    startActivity(intent);
                    break;
                case R.id.profile_layout_buy_list:
                    intent = new Intent(getActivity(), BuyList.class);
                    startActivity(intent);
                    break;
                case R.id.profile_layout_sell_list:
                    intent = new Intent(getActivity(), SellList.class);
                    startActivity(intent);
                    break;
                case R.id.profile_layout_like_list:
                    intent = new Intent(getActivity(), RecommendList.class);
                    startActivity(intent);
                    break;
                case R.id.profile_tv_img_list:
                    intent = new Intent(getActivity(), ImgList.class);
                    startActivity(intent);
                    break;
                case R.id.profile_tv_img_add:
                    // 혜지언니 이미지 등록 intent
                    intent = new Intent(getActivity(), ImageAddImageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.profile_tv_sell_report:
                    intent = new Intent(getActivity(), SellReport.class);
                    startActivity(intent);
                    break;
                case R.id.profile_tv_logout:
                    // 로그아웃
                    auto = getActivity().getSharedPreferences("signInState", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = auto.edit();
                    editor.putString("emailId", null);
                    editor.commit();
                    Intent intentStart = new Intent(getActivity(), StartActivity.class);
                    startActivity(intentStart);
                    break;
                case R.id.profile_tv_user_delete:
                    intent = new Intent(getActivity(), UserDelete.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
