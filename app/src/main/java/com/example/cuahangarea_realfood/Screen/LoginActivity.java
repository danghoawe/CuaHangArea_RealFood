package com.example.cuahangarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtMatKhau;
    TextView txtDangKy,txtQuenMatKhau;
    Button btnDangNhap;
    FirebaseAuth auth;

    Validate validate = new Validate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        this.getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    private void setEvent() {
        Context context = this;
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DangKyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuenMatKhauActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KAlertDialog kAlertDialog = new KAlertDialog(context);

                if (Validated_Form()) {
                    kAlertDialog.setTitleText("Loading... ");
                    kAlertDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.show();

                    auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtMatKhau.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull  Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                kAlertDialog.dismiss();
                                startActivity(intent);
                            }
                            else {
                                kAlertDialog.setTitleText("Sai tài khoản hoặc mật khẩu");
                                kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                            }
                        }
                    });

                }
            }
        });
    }

    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtEmail) && validate.isEmail(edtEmail)
                &&!validate.isBlank(edtMatKhau)
                && !validate.lessThan6Char(edtMatKhau)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private void setControl() {
        txtDangKy = findViewById(R.id.txtDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
    }
}