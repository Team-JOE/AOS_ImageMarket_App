package com.aosproject.imagemarket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aosproject.imagemarket.Activity.ImageDetailActivity;
import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopularImageAdapter extends BaseAdapter {

    private Context context = null;
    private int layout = 0;
    private ArrayList<JsonImageDY> data = null;
    private LayoutInflater inflater = null;

    public PopularImageAdapter(Context context, int layout, ArrayList<JsonImageDY> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getImageCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_popular);

        Glide.with(context)
                .load(ShareVar.macIP + "image/" + data.get(position).getImageFilepath())
                .override(110,110)
                .into(imageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("code", data.get(position).getImageCode());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
