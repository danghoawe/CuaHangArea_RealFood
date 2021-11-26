package com.example.cuahangarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiShipper;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinShipperBinding;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.Shipper;
import com.example.cuahangarea_realfood.screen.DangKyActivity;
import com.example.cuahangarea_realfood.screen.MapsActivity;
import com.example.cuahangarea_realfood.screen.ThongKeActivity;
import com.example.cuahangarea_realfood.screen.ThongTinCuaHangActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class ThongTinShipper extends AppCompatActivity {
    ActivityThongTinShipperBinding binding;
    private Uri uriAvatar, CMND_sau, CMND_truoc;
    String token = "";
    Validate validate = new Validate();
    Date ngaySinh;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Shipper shipper = null;
    int LAUNCH_SECOND_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinShipperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataDonHang = intent.getStringExtra("Shipper");
            Gson gson = new Gson();
            shipper = gson.fromJson(dataDonHang, Shipper.class);
            LoadData();
        }
        setEvent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                binding.edtDiaChi.setText(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
    private void LoadData() {
        binding.edtMatKhau.setVisibility(View.GONE);
        binding.edtNhapLaiMatKhau.setVisibility(View.GONE);
        firebase_manager.storageRef.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ThongTinShipper.this)
                        .load(uri.toString())
                        .into(binding.profileImage);
                uriAvatar = uri;
            }
        });

        binding.edtEmail.setText(shipper.geteMail());
        binding.edtHoten.setText(shipper.getHoVaTen());
        binding.btnNgaySinh.setText(shipper.getNgaySinh());
        binding.edtBienSo.setText(shipper.getMaSoXe());
        binding.edtDiaChi.setText(shipper.getDiaChi());
        binding.edtSoDT.setText(shipper.getSoDienThoai());
        firebase_manager.storageRef.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatTruoc").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ThongTinShipper.this)
                        .load(uri.toString())
                        .into(binding.imgCMNDTruoc);
                CMND_truoc = uri;
            }
        });
        firebase_manager.storageRef.child("Shipper").child(shipper.getiDShipper()).child("CMND_MatSau").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ThongTinShipper.this)
                        .load(uri.toString())
                        .into(binding.imgCMNDSau);
                CMND_sau = uri;
            }
        });

    }

    private void setEvent() {
        binding.txtGoToMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("oke","Oke");
                Toast.makeText(ThongTinShipper.this, "oke", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThongTinShipper.this, MapsActivity.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

            }
        });
        binding.btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                binding.profileImage.setImageBitmap(r.getBitmap());
                                uriAvatar = r.getUri();
                            }
                        })
                        .show(ThongTinShipper.this);
            }
        });
        binding.imgCMNDTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                binding.imgCMNDTruoc.setImageBitmap(r.getBitmap());
                                CMND_truoc = r.getUri();
                            }
                        })
                        .show(ThongTinShipper.this);
            }
        });

        binding.imgCMNDSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                binding.imgCMNDSau.setImageBitmap(r.getBitmap());
                                CMND_sau = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(ThongTinShipper.this);
            }
        });
        binding.btnNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                if (ngaySinh != null) {
                    cal.setTime(ngaySinh);
                }
                DatePickerDialog dpd = new DatePickerDialog(ThongTinShipper.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            ngaySinh = date;
                            binding.btnNgaySinh.setText(formatter.format(date));
                        }
                        , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });
        binding.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shipper == null) {
                    if (Validated_Form()) {
                        StorePassword storePassword = new StorePassword(ThongTinShipper.this);
                        KAlertDialog kAlertDialog = new KAlertDialog(ThongTinShipper.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                        kAlertDialog.show();
                        String email = firebase_manager.auth.getCurrentUser().getEmail();
                        String idCuaHang = firebase_manager.auth.getCurrentUser().getUid();
                        firebase_manager.auth.createUserWithEmailAndPassword(binding.edtEmail.getText().toString(), binding.edtNhapLaiMatKhau.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String uuid = authResult.getUser().getUid();
                                Shipper temp = new Shipper(uuid, binding.edtEmail.getText().toString(), binding.edtNhapLaiMatKhau.getText().toString(), binding.edtHoten.getText().toString(),
                                        binding.edtDiaChi.getText().toString(), "", binding.btnNgaySinh.getText().toString(), binding.edtBienSo.getText().toString(), binding.edtSoDT.getText().toString(), "Trực thuộc cửa hàng",idCuaHang, TrangThaiShipper.KhongHoatDong);
                                firebase_manager.Ghi_Shipper(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                        kAlertDialog.setContentText("Đã tạo tài khoản thành công");
                                        firebase_manager.Up2MatCMND_Shipper(CMND_truoc, CMND_sau, uuid);
                                        firebase_manager.auth.signOut();
                                        firebase_manager.auth.signInWithEmailAndPassword(email, storePassword.getPassword());
                                        if (uriAvatar != null) {
                                            firebase_manager.UpAvatarShipper(uriAvatar, uuid);
                                        }

                                    }
                                });
                                shipper = temp;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                kAlertDialog.changeAlertType(KAlertDialog.ERROR_TYPE);
                                kAlertDialog.setContentText("Email đã được sử dụng bởi người dùng khác");
                            }
                        });
                        ;

                    }
                } else {
                    if (Validated_Form_Update()) {
                        KAlertDialog kAlertDialog = new KAlertDialog(ThongTinShipper.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                        kAlertDialog.show();
                        String uuid = shipper.getiDShipper();
                        Shipper temp = new Shipper(uuid, binding.edtEmail.getText().toString(), binding.edtNhapLaiMatKhau.getText().toString(), binding.edtHoten.getText().toString(),
                                binding.edtDiaChi.getText().toString(), "", binding.btnNgaySinh.getText().toString(), binding.edtBienSo.getText().toString(), binding.edtSoDT.getText().toString(), "Trực thuộc cửa hàng",firebase_manager.auth.getUid(), TrangThaiShipper.KhongHoatDong);
                        firebase_manager.Ghi_Shipper(shipper).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setContentText("Đã lưu tài khoản thành công");
                                firebase_manager.Up2MatCMND_Shipper(CMND_truoc, CMND_sau, uuid);
                                if (uriAvatar != null) {
                                    firebase_manager.UpAvatarShipper(uriAvatar, uuid);
                                }
                            }
                        });
                        shipper = temp;
                    }
                }

            }
        });
    }

    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(binding.edtEmail) && validate.isEmail(binding.edtEmail)
                && !validate.isBlank(binding.edtHoten)
                && !validate.isBlank(binding.edtDiaChi) && validate.isPhone(binding.edtSoDT)
                && !validate.isBlank(binding.edtMatKhau) && !validate.lessThan6Char(binding.edtMatKhau)
                && !validate.isBlank(binding.edtBienSo) && !validate.lessThan6Char(binding.edtBienSo)
                && !validate.isBlank(binding.edtNhapLaiMatKhau) && !validate.lessThan6Char(binding.edtNhapLaiMatKhau)
        ) {
            binding.edtNhapLaiMatKhau.setError(null);
            if (binding.edtNhapLaiMatKhau.getText().toString().equals(binding.edtMatKhau.getText().toString())) {
                result = true;

                if (CMND_sau == null || CMND_truoc == null) {
                    Alerter.create(ThongTinShipper.this)
                            .setTitle("Lỗi")
                            .setText("Vui lòng tải lên 2 mặt CMND/CCCD để xác thưc")
                            .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                    result = false;
                }
            } else {
                result = false;
                binding.edtNhapLaiMatKhau.setError("Mật khẩu không trùng khớp");
            }
        }
        return result;
    }

    private boolean Validated_Form_Update() {
        boolean result = false;
        if (!validate.isBlank(binding.edtEmail) && validate.isEmail(binding.edtEmail)
                && !validate.isBlank(binding.edtHoten)
                && !validate.isBlank(binding.edtDiaChi) && validate.isPhone(binding.edtSoDT)
                && !validate.isBlank(binding.edtBienSo) && !validate.lessThan6Char(binding.edtBienSo)

        ) {
            result = true;
            if (CMND_sau == null || CMND_truoc == null) {
                Alerter.create(ThongTinShipper.this)
                        .setTitle("Lỗi")
                        .setText("Vui lòng tải lên 2 mặt CMND/CCCD để xác thưc")
                        .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                        .show();
                result = false;
            }
        }
        return result;
    }
}