package com.example.footsalmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyPageAdapter extends RecyclerView.Adapter<MyPageAdapter.ViewHolder> {
    Context context;
    ArrayList<viewerData> viewerDatas = new ArrayList<>();



    public MyPageAdapter(Context context, ArrayList<viewerData> viewerDatas) {
        this.context = context;
        this.viewerDatas = viewerDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypage_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        viewerData viewerData = viewerDatas.get(position);
        holder.image.setImageBitmap(viewerData.getImage());
        holder.text.setText(viewerData.getNickName());

    }

    @Override
    public int getItemCount() {

        return viewerDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public final View viewItem;
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            image = itemView.findViewById(R.id.image_MyPage);
            text = itemView.findViewById(R.id.text_MyPage);

        }

    }
}

