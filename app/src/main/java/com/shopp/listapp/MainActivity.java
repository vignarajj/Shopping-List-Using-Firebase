package com.shopp.listapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_useremail)
    EditText editUseremail;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_signup)
    TextView txtSignup;

    Util util;
    Context context;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        util = Util.getInstance();
        context = MainActivity.this;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(context);
        if (mFirebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @OnClick(R.id.txt_signup)
    public void signUp() {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        finish();
    }

    @OnClick(R.id.btn_login)
    public void signIn() {
        if (validateLogin()) {
            String email = editUseremail.getText().toString().trim();
            String pwd = editPwd.getText().toString().trim();
            mProgress.setMessage("Processing...");
            mProgress.show();
            mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                        util.showSuccessToast(context, "Successfully login");
                    } else {
                        mProgress.dismiss();
                        util.showErrorToast(context, "Failed to login, Try again");
                    }
                }
            });
        }
    }

    public boolean validateLogin() {
        if (editUseremail.getText().toString().length() <= 6) {
            util.showErrorToast(context, "Enter valid email address");
            return false;
        } else if (editPwd.getText().toString().length() <= 6) {
            util.showErrorToast(context, "Enter valid password");
            return false;
        } else {
            return true;
        }
    }
}
