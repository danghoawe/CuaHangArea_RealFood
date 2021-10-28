package com.example.cuahangarea_realfood.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.cuahangarea_realfood.Validate;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinCuaHangBinding;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

public class ThongTinCuaHangActivity extends AppCompatActivity {
    ActivityThongTinCuaHangBinding binding;
    Firebase_Manager firebase_manager= new Firebase_Manager();
    CuaHang cuaHang;
    Uri uriAvatar,uriWallpaper;
    Validate validate = new Validate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinCuaHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lnLayout.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadData();
        setEvent();

    }

    private void setEvent() {
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate.isBlank(binding.edtEmail)&&validate.isEmail(binding.edtEmail)
                        &&!validate.isBlank(binding.edtDiaChi)&&!validate.isBlank(binding.edtHotenChu)
                        &&!validate.isBlank(binding.edtSoCMND)&&!validate.isBlank(binding.edtSoDT)
                        &&!validate.isBlank(binding.edtThongTinChiTiet)&&!validate.isBlank(binding.edtTenCuaHang)
                )
                {
                    CuaHang temp = new CuaHang(firebase_manager.auth.getUid(), binding.edtTenCuaHang.getText().toString()
                            , binding.edtHotenChu.getText().toString(), binding.edtThongTinChiTiet.getText().toString()
                            , binding.edtSoCMND.getText().toString(), binding.edtSoDT.getText().toString()
                            , "", "", cuaHang.getRating(), binding.edtEmail.getText().toString()
                            , cuaHang.getTrangThaiCuaHang(),binding.edtDiaChi.getText().toString());
                    cuaHang = temp;
                    CapNhatCuaHang(cuaHang);
                    if (uriAvatar!=null)
                    {
                        firebase_manager.UpAvatar(uriAvatar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                LoadData();
                            }
                        });
                    }
                    if (uriWallpaper!=null)
                    {
                        firebase_manager.UpWallPaper(uriWallpaper).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                LoadData();
                            }
                        });
                    }

                }
            }
        });

        binding.btnAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.AN);
                CapNhatCuaHang(cuaHang);
            }
        });
        binding.btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                CapNhatCuaHang(cuaHang);

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
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(ThongTinCuaHangActivity.this);
            }
        });

        binding.btnEditWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                binding.imgWallPaper.setImageBitmap(r.getBitmap());
                                uriWallpaper = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(ThongTinCuaHangActivity.this);
            }
        });
    }
    public void CapNhatCuaHang (CuaHang cuaHang){
        KAlertDialog kAlertDialog = new KAlertDialog(ThongTinCuaHangActivity.this,KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
        kAlertDialog.show();
        kAlertDialog.showConfirmButton(false);
        firebase_manager.Ghi_CuaHang(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                kAlertDialog.setContentText("Trạng thái đã cửa hàng đã được cập nhật!");
                kAlertDialog.showConfirmButton(false);
                LoadData();
            }
        });
    }

    private void LoadData() {
       firebase_manager. mDatabase.child("CuaHang").child(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CuaHang temp = dataSnapshot.getValue(CuaHang.class);
                cuaHang = temp;
                binding.edtDiaChi.setText( temp.getDiaChi());
                binding.edtTenCuaHang.setText(temp.getTenCuaHang());
                binding.edtEmail.setText(temp.getEmail());
                binding.edtSoDT.setText(temp.getSoDienThoai());
                binding.edtSoCMND.setText(temp.getSoCMND());
                binding.edtHotenChu.setText(temp.getChuSoHuu());
                binding.edtThongTinChiTiet.setText(temp.getThongTinChiTiet());
                binding.progessbar.setVisibility(View.GONE);
                binding.lnLayout.setVisibility(View.VISIBLE);
                if (temp.getTrangThaiCuaHang()==TrangThaiCuaHang.AN)
                {
                    binding.btnHienthi.setVisibility(View.VISIBLE);
                }
                else {
                    binding.btnHienthi.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       firebase_manager.storageRef.child("CuaHang").child(firebase_manager.auth.getUid()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               Glide.with(ThongTinCuaHangActivity.this)
                       .load(uri.toString())
                       .into(binding.profileImage);

           }
       });
        firebase_manager.storageRef.child("CuaHang").child(firebase_manager.auth.getUid()).child("WallPaper").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ThongTinCuaHangActivity.this)
                        .load(uri.toString())
                        .into(binding.imgWallPaper);
            }
        });

    }
}