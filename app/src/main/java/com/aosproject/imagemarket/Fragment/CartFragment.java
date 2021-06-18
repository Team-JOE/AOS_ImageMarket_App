package com.aosproject.imagemarket.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aosproject.imagemarket.Activity.DealActivityHK;
import com.aosproject.imagemarket.Adapter.CartAdapterHK;
import com.aosproject.imagemarket.Bean.CartHK;
import com.aosproject.imagemarket.NetworkTask.CartNetworkTaskHK;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    boolean clickStatus = false;
    ShareVar shareVar = new ShareVar();
    String urlAddr = null;
    ArrayList<CartHK> cartItems;
    CartAdapterHK cartAdapter;
    ImageView ivBack, ivDown, ivUp;
    ListView cartList;
    LinearLayout yesItemLayout, noItemLayout, bottomLayout, bottomLayoutBig, bottomLayoutSmall;
    TextView btnAll, tvCount, tvPrice, btnDelete, btnDeal, tvPriceSmall;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        addListener(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    // Cart Adapter 연결
    public void connectGetData(){

        try {
            CartNetworkTaskHK networkTask = new CartNetworkTaskHK(getContext(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            cartItems = (ArrayList<CartHK>) obj;

            if(cartItems.size()==0){
                noItemLayout.setVisibility(View.VISIBLE);
                yesItemLayout.setVisibility(View.INVISIBLE);
            }else {
                yesItemLayout.setVisibility(View.VISIBLE);
                noItemLayout.setVisibility(View.INVISIBLE);

                cartAdapter = new CartAdapterHK(getContext(), R.layout.cart_list_layout, cartItems);
                cartList.setAdapter(cartAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // layout widget connect
    private void addListener(View view){
        urlAddr = shareVar.macIP + "jsp/cart_select_all_HK.jsp?loginEmail=" + shareVar.loginEmail;
        cartList = view.findViewById(R.id.cart_lv_list);
        bottomLayout = view.findViewById(R.id.cart_layout_bottom);
        tvCount = view.findViewById(R.id.cart_tv_total_count);
        tvPrice = view.findViewById(R.id.cart_tv_total_price);
        tvPriceSmall = view.findViewById(R.id.cart_tv_total_price_small);
        yesItemLayout = view.findViewById(R.id.cart_layout_yesItem);
        noItemLayout = view.findViewById(R.id.cart_layout_noItem);

        // button
        ivBack = view.findViewById(R.id.cart_iv_btn_back);
        btnAll = view.findViewById(R.id.cart_tv_btn_all);
        btnDelete = view.findViewById(R.id.cart_tv_btn_delete);
        btnDeal = view.findViewById(R.id.cart_tv_btn_deal);
        bottomLayoutBig = view.findViewById(R.id.cart_layout_bottom_big);
        bottomLayoutSmall = view.findViewById(R.id.cart_layout_bottom_small);

        // button
        ivBack.setOnClickListener(onButtonClickListener);
        btnAll.setOnClickListener(onButtonClickListener);
        btnDelete.setOnClickListener(onButtonClickListener);
        btnDeal.setOnClickListener(onButtonClickListener);
        bottomLayoutBig.setOnClickListener(onButtonClickListener);
        bottomLayoutSmall.setOnClickListener(onButtonClickListener);
    }

    // button Action
    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cart_iv_btn_back:
//                    ???????
                    onPause();
                    onStop();
                    break;

                // 상품 전체 선택 버튼 클릭 시
                case R.id.cart_tv_btn_all:
                    if(clickStatus == false){
                        bottomLayoutSmall.setVisibility(View.GONE);
                        bottomLayout.setVisibility(View.VISIBLE);
                        btnAll.setText("상품 전체 해제");
                        tvCount.setText(cartItems.size() + "개");
                        tvPrice.setText(cartTotalPrice() + "원");
                        tvPriceSmall.setText(cartTotalPrice() +"원");
                        cartList.setBackgroundColor(Color.LTGRAY);
                        clickStatus = true;
                    }else{
                        bottomLayoutSmall.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.GONE);
                        btnAll.setText("상품 전체 선택");
                        tvCount.setText("0개");
                        tvPrice.setText("0원");
                        tvPriceSmall.setText("0원");
                        cartList.setBackgroundColor(Color.TRANSPARENT);
                        clickStatus = false;
                    }
                    break;
                case R.id.cart_layout_bottom_big:
                    bottomLayout.setVisibility(View.GONE);
                    bottomLayoutSmall.setVisibility(View.VISIBLE);
                    break;
                case R.id.cart_layout_bottom_small:
                    bottomLayoutSmall.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    break;
                // 삭제하기 버튼 클릭시 다이어로그 보여준 후 삭제
                case R.id.cart_tv_btn_delete:
                    if(tvCount.getText().toString().equals("0개")){
                        Snackbar.make(getView(), "삭제할 이미지를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
//                        new AlertDialog.Builder(getContext())
//                                .setMessage("삭제할 이미지를 선택해주세요")
//                                .setCancelable(false)
//                                .setPositiveButton("OK", null)
//                                .show();
                    }else {
                        new AlertDialog.Builder(getContext())
                                .setMessage("장바구니를 비우시겠습니까?")
                                .setCancelable(false)
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Snackbar.make(getView(), "장바구니를 비웠습니다.", Snackbar.LENGTH_SHORT).show();
    //                                    urlAddr = "http://" + macIP + ":8080/first/namecardDeleteReturn.jsp?namecardNo=" + namecardNo;
    //                                    connectDelete();
    //
    //                                    String result = connectDelete();
    //
    //                                    if(result.equals("1")){
    //                                        Toast.makeText(DetailViewActivity.this, eName + "님의 정보가 휴지통으로 이동되었습니다.", Toast.LENGTH_LONG).show();
    //                                    }else{
    //                                        Toast.makeText(DetailViewActivity.this, "삭제에 실패했습니다.", Toast.LENGTH_LONG).show();
    //                                    }
    //                                    finish();
                                    }
                                })
                                .show();
                    }
                    break;
                case R.id.cart_tv_btn_deal:
                    if(tvCount.getText().toString().equals("0개")){
                        Snackbar.make(getView(), "주문할 이미지를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
//                        new AlertDialog.Builder(getContext())
//                                .setMessage("삭제할 이미지를 선택해주세요")
//                                .setCancelable(false)
//                                .setPositiveButton("OK", null)
//                                .show();
                    }else {
                        Intent intent = new Intent(getContext(), DealActivityHK.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    // total price 구하기
    private int cartTotalPrice(){
        int totalPrice = 0;

        for(int i=0; i<cartItems.size(); i++){
            totalPrice += cartItems.get(i).getImagePrice();
        }
        return totalPrice;
    }
}
