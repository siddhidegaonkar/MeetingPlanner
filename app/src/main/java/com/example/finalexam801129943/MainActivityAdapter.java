package com.example.finalexam801129943;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {


    ArrayList<MeetingDomain> meetingDomains;
    Context context;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;


    public MainActivityAdapter(ArrayList<MeetingDomain> meetingDomains, Context context){
        this.meetingDomains=meetingDomains;
        this.context=context;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public ArrayList<MeetingDomain> getMeetingDomains() {
        return meetingDomains;
    }

    @NonNull
    @Override
    public MainActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainactivityrecycler, parent, false);
        MainActivityAdapter.ViewHolder viewHolder = new MainActivityAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.ViewHolder holder, int position) {

        holder.MeetingName.setText(meetingDomains.get(position).getTitle().toString());
        holder.MeetingPlace.setText(meetingDomains.get(position).getPlace().toString());
        holder.MeetingDate.setText(meetingDomains.get(position).getDate().toString());
        holder.MeetingTime.setText(meetingDomains.get(position).getTime().toString());

    }

    @Override
    public int getItemCount() {
        return meetingDomains.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {


        TextView MeetingName ;
        TextView MeetingPlace ;
        TextView MeetingDate;
        TextView MeetingTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MeetingName = itemView.findViewById(R.id.tv_name);
            MeetingPlace = itemView.findViewById(R.id.tv_place);
            MeetingDate = itemView.findViewById(R.id.tv_date);
            MeetingTime = itemView.findViewById(R.id.tv_time);

            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(null, v, getAdapterPosition(), 0);
            return true;
        }
    }

}
