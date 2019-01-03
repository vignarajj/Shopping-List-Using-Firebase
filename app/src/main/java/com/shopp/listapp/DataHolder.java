package com.shopp.listapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DataHolder extends RecyclerView.ViewHolder {

    public TextView txt_type, txt_note, txt_amount, txt_date;
    public CardView cardView;
    public ImageView btn_delete;

    public DataHolder(@NonNull View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.card_data);
        txt_type = (TextView)itemView.findViewById(R.id.txt_type);
        txt_amount = (TextView)itemView.findViewById(R.id.txt_amount);
        txt_date = (TextView)itemView.findViewById(R.id.txt_date);
        txt_note = (TextView)itemView.findViewById(R.id.txt_note);
        btn_delete = (ImageView)itemView.findViewById(R.id.btn_delete);
    }
}
