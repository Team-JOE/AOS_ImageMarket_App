package com.aosproject.imagemarket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aosproject.imagemarket.Bean.CartHK;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapterHK extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<CartHK> cartImages = null;
    private LayoutInflater inflater = null;
    ShareVar shareVar = new ShareVar();

    public CartAdapterHK(Context mContext, int layout, ArrayList<CartHK> cartImages) {
        this.mContext = mContext;
        this.layout = layout;
        this.cartImages = cartImages;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return cartImages.size();
    }

    @Override
    public Object getItem(int position) {
        return cartImages.get(position).getCartNo();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);
        ImageView imageFilepath = convertView.findViewById(R.id.cart_iv_imageFile);
        TextView sellName = convertView.findViewById(R.id.cart_tv_sellUserName);
        TextView imageTitle = convertView.findViewById(R.id.cart_tv_imageTitle);
        TextView imagePrice = convertView.findViewById(R.id.cart_tv_imagePrice);

        Glide.with(convertView)
                .load(shareVar.macIP + "image/" + cartImages.get(position).getImageFilepath())
                .override(150, 150)
                .centerCrop()
                .into(imageFilepath);
        sellName.setText(cartImages.get(position).getSellName());
        imageTitle.setText(cartImages.get(position).getImageTitle());
        imagePrice.setText(Integer.toString(cartImages.get(position).getImagePrice()) + "원");

        return convertView;
    }
}
