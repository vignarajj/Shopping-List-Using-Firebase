package com.shopp.listapp.adapters;

import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.shopp.listapp.DataHolder;
import com.shopp.listapp.Util;
import com.shopp.listapp.listeners.ViewClickListener;
import com.shopp.listapp.models.DataModel;

public class DataAdapter extends FirebaseRecyclerAdapter<DataModel, DataHolder> {
    Context context;
    ViewClickListener clickListener;

    public DataAdapter(Class<DataModel> modelClass, int layout, Class<DataHolder> holder, DatabaseReference reference, Context context, ViewClickListener clickListener) {
        super(modelClass, layout, holder, reference);
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    protected void populateViewHolder(DataHolder viewHolder, DataModel model, int position) {
        viewHolder.txt_note.setText(model.getmNote());
        viewHolder.txt_date.setText(model.getmDate());
        viewHolder.txt_amount.setText(Util.getInstance().getFormatedString(model.getaAmount()));
        viewHolder.txt_type.setText(model.getmType());

        viewHolder.cardView.setOnClickListener(view -> clickListener.getData(model));
        viewHolder.btn_delete.setOnClickListener(view -> {
            clickListener.delete(model);
        });
    }
}
