package com.example.cuahangarea_realfood;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtMatKhau;
    TextView txtDangKy;
    Button btnDangNhap;
    FirebaseAuth auth;
    KAlertDialog kAlertDialog;
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
        kAlertDialog = new KAlertDialog(this);
        Context context = this;
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DangKyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validated_Form()) {
                    kAlertDialog.setTitleText("Loading... ");
                    kAlertDialog.show();
                    kAlertDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE);
                    auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtMatKhau.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(LoginActivity.this, Home.class);
                            kAlertDialog.dismiss();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                            kAlertDialog.setContentText(e.getMessage());
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
    }
}