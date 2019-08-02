package com.example.footsalmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<viewerData> viewerDatas = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<viewerData> viewerDatas) {
        this.context = context;
        this.viewerDatas = viewerDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        viewerData viewerData = viewerDatas.get(position);
        holder.image.setImageBitmap(viewerData.getImage());
        holder.nickName.setText(viewerData.getNickName());
        holder.area.setText(viewerData.getArea());
        holder.skill.setText(viewerData.getSkill());
        holder.currentDate.setText(viewerData.getCurrentDate());

    }

    @Override
    public int getItemCount() {
        return viewerDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public final View viewItem;
        CircleImageView image;
        TextView nickName,skill,area,currentDate;

        public ViewHolder(View itemView) {
            super(itemView);
            viewItem = itemView;
            image = itemView.findViewById(R.id.image_User);
            nickName = itemView.findViewById(R.id.text_NickName);
            area = itemView.findViewById(R.id.text_Area);
            skill = itemView.findViewById(R.id.text_Skill);
            currentDate = itemView.findViewById(R.id.text_EnrollDate);

        }

    }
}

