package com.shopp.listapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.edit_regemail)
    EditText editRegemail;
    @BindView(R.id.edit_regpwd)
    EditText editRegpwd;
    @BindView(R.id.edit_regconfpwd)
    EditText editRegconfpwd;
    @BindView(R.id.btn_reg)
    Button btnLogin;
    @BindView(R.id.txt_signin)
    TextView txtSignup;

    FirebaseAuth mFirebaseAuth;
    Util util;
    Context context;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        context = RegistrationActivity.this;
        mProgressDialog = new ProgressDialog(context);
        mFirebaseAuth = FirebaseAuth.getInstance();
        util = Util.getInstance();
    }

    @OnClick(R.id.btn_reg)
    public void signUp() {
        String email = editRegemail.getText().toString().trim();
        String pwd = editRegpwd.getText().toString();
        if (email.length() <= 6) {
            util.showErrorToast(context, "Enter valid mail address");
            return;
        }

        if (!validatePwd()) {
            util.showErrorToast(context, "Enter valid password");
            return;
        }

        mProgressDialog.setMessage("Processing..");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    util.showSuccessToast(context, "Successfully registered");
                    mProgressDialog.dismiss();
                } else {
                    util.showErrorToast(context, "Registration Failed, Try again!");
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    @OnClick(R.id.txt_signin)
    public void signIn() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public boolean validatePwd() {
        String editPwd = editRegpwd.getText().toString();
        String confPwd = editRegconfpwd.getText().toString();
        String error1 = "Password Can't be empty";
        String error2 = "Password must be more than 6 characters";
        if (editPwd.equals("") || confPwd.equals("")) {
            editRegpwd.setError(error1);
            editRegconfpwd.setError(error1);
            return false;
        }
        if (editPwd.length() <= 6 || confPwd.length() <= 6) {
            editRegpwd.setError(error2);
            editRegconfpwd.setError(error2);
            return false;
        }


        if (editPwd.equals(confPwd)) {
            return true;
        } else {
            return false;
        }
    }
}
