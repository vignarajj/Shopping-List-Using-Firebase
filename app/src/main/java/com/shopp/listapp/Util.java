package com.shopp.listapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

public class Util {
    public static Util instance = null;
    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyBoard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    public void showErrorToast(Context context, String message){
        Toasty.error(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessToast(Context context, String message){
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public String getFormatedString(double val){
        DecimalFormat df = new DecimalFormat("###.00");
        return df.format(val);
    }
}
