package com.example.mathchampionsleague;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder  extends RecyclerView.ViewHolder {

    TextView quoteview;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        quoteview = itemView.findViewById(R.id.quoteview);
    }
}
