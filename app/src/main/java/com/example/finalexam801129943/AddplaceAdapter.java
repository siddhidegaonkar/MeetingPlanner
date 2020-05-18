package com.example.finalexam801129943;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddplaceAdapter extends RecyclerView.Adapter<AddplaceAdapter.ViewHolder> {




    ArrayList<String> cititesArrayList;
    Context context;

    public AddplaceAdapter(ArrayList<String> cititesArrayList, Context context){
        this.cititesArrayList = cititesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddplaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.addplaceadapter, parent, false);
        AddplaceAdapter.ViewHolder viewHolder = new AddplaceAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final AddplaceAdapter.ViewHolder holder, final int position) {
        holder.AdapterCitiesName.setText(cititesArrayList.get(position).toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof AddPlace) {
                    ((AddPlace)context).setText(cititesArrayList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cititesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView AdapterCitiesName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AdapterCitiesName = itemView.findViewById(R.id.AdapterCitiesName);


        }

    }
}
