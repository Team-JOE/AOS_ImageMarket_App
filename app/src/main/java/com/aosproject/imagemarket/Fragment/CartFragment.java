package com.aosproject.imagemarket.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aosproject.imagemarket.Adapter.CartAdapterHK;
import com.aosproject.imagemarket.Bean.CartHK;
import com.aosproject.imagemarket.NetworkTask.CartNetworkTaskHK;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    ShareVar shareVar = new ShareVar();
    String urlAddr = null;
    ArrayList<CartHK> cartItems;
    CartAdapterHK cartAdapter;
    ImageView ivBack;
    ListView cartList;
    LinearLayout bottomLayout;
    TextView btnAll, tvCount, tvPrice, btnDelete, btnDeal;



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
        try{
            CartNetworkTaskHK networkTask = new CartNetworkTaskHK(getContext(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            cartItems = (ArrayList<CartHK>) obj;

            cartAdapter = new CartAdapterHK(getContext(), R.layout.cart_list_layout, cartItems);
            cartList.setAdapter(cartAdapter);

        }catch (Exception e){
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

        bottomLayout.setVisibility(View.GONE);

        // button
        ivBack = view.findViewById(R.id.cart_iv_btn_back);
        btnAll = view.findViewById(R.id.cart_tv_btn_all);
        btnDelete = view.findViewById(R.id.cart_tv_btn_delete);
        btnDeal = view.findViewById(R.id.cart_tv_btn_deal);

        ivBack.setOnClickListener(onButtonClickListener);
        btnAll.setOnClickListener(onButtonClickListener);
        btnDelete.setOnClickListener(onButtonClickListener);
        btnDeal.setOnClickListener(onButtonClickListener);
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
                case R.id.cart_tv_btn_all:
                    bottomLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.cart_tv_btn_delete:
                    break;
                case R.id.cart_tv_btn_deal:
                    break;
            }
        }
    };
}
