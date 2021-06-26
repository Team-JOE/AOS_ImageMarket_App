package com.aosproject.imagemarket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aosproject.imagemarket.Bean.JsonImageDY;
import com.aosproject.imagemarket.R;
import com.aosproject.imagemarket.Util.ShareVar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class ImageAdapterDY extends RecyclerView.Adapter<ImageAdapterDY.ViewHolder> {
    private Context context;

    private ArrayList<JsonImageDY> images = null;

    public ImageAdapterDY(Context context, ArrayList<JsonImageDY> images) {
        this.context = context;
        this.images = images;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;

        public ViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.search_image_view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(ShareVar.macIP + "image/" + images.get(position).getImageFilepath()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
