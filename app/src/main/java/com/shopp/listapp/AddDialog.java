package com.shopp.listapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopp.listapp.listeners.SaveListener;
import com.shopp.listapp.models.DataModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDialog extends AlertDialog {

    @BindView(R.id.txt_titledialog)
    TextView txt_titledialog;
    @BindView(R.id.edit_type)
    EditText editType;
    @BindView(R.id.edit_amt)
    EditText editAmt;
    @BindView(R.id.edit_note)
    EditText editNote;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.layout_edit)
    LinearLayout layout_edit;

    Util util;
    SaveListener listener;
    int flag = 0;
    DataModel model;

    // if flag 0 save, flag 1 edit and delete
    public AddDialog(Context context, int flag, DataModel model, SaveListener listener) {
        super(context);
        this.listener = listener;
        this.flag = flag;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.input_data);
        util = Util.getInstance();
        ButterKnife.bind(this);
        setCancelable(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (flag == 0) {
            txt_titledialog.setText("Add Shopping List");
            btnSave.setText("Save");
        } else if (flag == 1) {
            txt_titledialog.setText("Update Shopping List");
            btnSave.setText("Update");
            if (model != null) {
                String type = model.getmType();
                String amt = util.getFormatedString(model.getaAmount());
                String note = model.getmNote();

                editType.setText(type);
                editType.setSelection(type.length());

                editAmt.setText(amt);
                editAmt.setSelection(amt.length());

                editNote.setText(note);
                editNote.setSelection(note.length());
            }
        }
    }

    @OnClick(R.id.btn_save)
    public void save() {
        String type = editType.getText().toString().trim();
        String amount = editAmt.getText().toString().trim();
        String note = editNote.getText().toString().trim();

        if (TextUtils.isEmpty(type)) {
            util.showErrorToast(getContext(), "Enter valid type");
            return;
        }

        if (TextUtils.isEmpty(amount)) {
            util.showErrorToast(getContext(), "Enter valid amount");
            return;
        }

        if (TextUtils.isEmpty(note)) {
            util.showErrorToast(getContext(), "Enter valid notes");
            return;
        }

        if(flag==0) {
            listener.doAction(0, type, amount, note);
            dismiss();
        }

        if(flag==1){
            listener.doAction(1, type, amount, note);
            dismiss();
        }
    }

    @OnClick(R.id.btn_cancel)
    public void closeDialog() {
        dismiss();
    }


}
