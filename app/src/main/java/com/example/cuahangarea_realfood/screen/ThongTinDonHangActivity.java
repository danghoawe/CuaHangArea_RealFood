package com.example.cuahangarea_realfood.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.cuahangarea_realfood.adapter.DonHangAdapter;
import com.example.cuahangarea_realfood.adapter.DonHangInfoAdapter;
import com.example.cuahangarea_realfood.adapter.DonHang_BepAdapter;
import com.example.cuahangarea_realfood.adapter.ShipperAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinCuaHangBinding;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinDonHangBinding;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.DonHangInfo;
import com.example.cuahangarea_realfood.model.KhachHang;
import com.example.cuahangarea_realfood.model.Shipper;
import com.example.cuahangarea_realfood.model.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nordan.dialog.Animation;
import com.nordan.dialog.DialogType;
import com.nordan.dialog.NordanAlertDialog;

import java.util.ArrayList;

import karpuzoglu.enes.com.fastdialog.Animations;
import karpuzoglu.enes.com.fastdialog.FastDialog;
import karpuzoglu.enes.com.fastdialog.PositiveClick;

public class ThongTinDonHangActivity extends AppCompatActivity {
    ActivityThongTinDonHangBinding binding ;
    ArrayList<DonHangInfo> donHangInfos;
    DonHang donHang;
    ArrayList<Shipper>shippers = new ArrayList<>();
    ShipperAdapter shipperAdapter ;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataDonHang = intent.getStringExtra("donhang");
            Gson gson = new Gson();
            donHang = gson.fromJson(dataDonHang, DonHang.class);
            Toast.makeText(this, "oke", Toast.LENGTH_SHORT).show();
            donHangInfos = new ArrayList<>();
            LoadButton(donHang.getTrangThai());
            LoadData();
            SetEvent();
        }
    }

    private void SetEvent() {

        binding.btnXacNhanCoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_DaGiaoChoBep);
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
        binding.btnHoanTac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien);
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });

        binding.btnXacNhanHoanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.Shipper_DaTraHang);
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
            }
        });
        binding.btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new NordanAlertDialog.Builder(ThongTinDonHangActivity.this)
                        .setDialogType(DialogType.WARNING)
                        .setAnimation(Animation.SLIDE)
                        .isCancellable(true)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có chắc hủy đơn hàng ? ")
                        .setPositiveBtnText("Oke")
                        .onPositiveClicked(() -> {
                            DonHang temp = donHang;
                            temp.setTrangThai(TrangThaiDonHang.SHOP_HuyDonHang);
                            donHang =temp;
                            LoadData();
                            LoadButton(donHang.getTrangThai());
                            firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });})
                        .setNegativeBtnText("Hủy")
                        .onNegativeClicked(() -> {})
                        .build();
                dialog.show();

            }
        });

        binding.btnDatThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_DangChuanBihang);
                temp.setGhiChuCuaHang(binding.singleDayPicker.getDate().toString());
               donHang =temp;
               LoadData();
               LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });

        binding.btnDaChuanBiXOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_DaChuanBiXong);
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
        binding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastDialog dialog = FastDialog.w(ThongTinDonHangActivity.this)
                        .setTitleText("Thông báo")
                        .setText("Vui lòng nhập lí do hủy đơn")
                        .setHint("Please enter text")
                        .privateEditText()
                        .setAnimation(Animations.GROW_IN)
                        .positiveText("Hủy đơn")
                        .negativeText("Quay lại")
                        .create();
                dialog.positiveClickListener(new PositiveClick() {
                    @Override
                    public void onClick(View view) {
                        if (dialog.getInputText()!=null)
                        {
                            DonHang temp = donHang;
                            temp.setTrangThai(TrangThaiDonHang.Bep_DaHuyDonHang);
                            temp.setGhiChuCuaHang(dialog.getInputText());
                            donHang =temp;
                            LoadData();
                            LoadButton(donHang.getTrangThai());
                            Toast.makeText(ThongTinDonHangActivity.this, "Đơn hàng đã được hủy vì lí do: "+dialog.getInputText(), Toast.LENGTH_LONG).show();
                            firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(ThongTinDonHangActivity.this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        binding.btnGiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_DangGiaoShipper);
                temp.setIDShipper(shippers.get(binding.spDSShipper.getSelectedPosition()).getiDShipper());
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                Toast.makeText(ThongTinDonHangActivity.this, "Vui lòng chờ shipper đến lấy hàng", Toast.LENGTH_SHORT).show();
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
        binding.btnDaGiaoHangChoShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.Shipper_DaLayHang);
                donHang =temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                Toast.makeText(ThongTinDonHangActivity.this, "Shipper đã lấy hàng", Toast.LENGTH_SHORT).show();
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });
    }

    private void LoadButton( TrangThaiDonHang trangThai) {
        Toast.makeText(this, trangThai+"", Toast.LENGTH_SHORT).show();
        if (trangThai.equals(TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien)) {
            binding.btnXacNhanCoc.setVisibility(View.VISIBLE);
            binding.btnHuyDonHang.setVisibility(View.VISIBLE);
        } else {
            binding.btnXacNhanCoc.setVisibility(View.GONE);
            binding.btnHuyDonHang.setVisibility(View.GONE);
        }
        if (trangThai.equals(TrangThaiDonHang.SHOP_DangChuanBihang) ) {
            binding.btnHoanTac1.setVisibility(View.VISIBLE);

        } else {
            binding.btnHoanTac1.setVisibility(View.GONE);
        }
        if (trangThai.equals(TrangThaiDonHang.ChoShopXacNhan_Tien)) {
            binding.btnXacNhanTraTien.setVisibility(View.VISIBLE);

        }
        else {
            binding.btnXacNhanTraTien.setVisibility(View.GONE);
        }
        if (trangThai.equals(TrangThaiDonHang.ChoShopXacNhan_TraHang)) {
            binding.btnXacNhanHoanHang.setVisibility(View.VISIBLE);

        } else {
            binding.btnXacNhanHoanHang.setVisibility(View.GONE);
        }

        if (trangThai == TrangThaiDonHang.SHOP_DangChuanBihang) {
            binding.lnHuyLiDo.setVisibility(View.VISIBLE);

        } else {
            binding.lnHuyLiDo.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.SHOP_DaGiaoChoBep) {
            binding.lnDatThoiGian.setVisibility(View.VISIBLE);

        } else {
            binding.lnDatThoiGian.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.SHOP_DaChuanBiXong||trangThai == TrangThaiDonHang.Shipper_KhongNhanGiaoHang) {
            binding.lnShipper.setVisibility(View.VISIBLE);
            LoadShipper();

        } else {
            binding.lnShipper.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.SHOP_ChoXacNhanGiaoHangChoShipper) {
            binding.lnXacNhangShipper.setVisibility(View.VISIBLE);
            LoadShipper();

        } else {
            binding.lnXacNhangShipper.setVisibility(View.GONE);
        }
    }

    private void LoadShipper() {
        firebase_manager.mDatabase.child("Shipper").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                shippers.clear();
                for (DataSnapshot dataSnapshot: task.getResult().getChildren()
                ) {
                    Shipper shipper = dataSnapshot.getValue(Shipper.class);
                    shippers.add(shipper);
                }
                shipperAdapter = new ShipperAdapter(ThongTinDonHangActivity.this,R.layout.item_shipper,shippers);
                binding.spDSShipper.setAdapter(shipperAdapter);
            }
        });
    }
    private void LoadData() {
        firebase_manager.mDatabase.child("KhachHang").child(donHang.getIDKhachHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KhachHang khachHang = snapshot.getValue(KhachHang.class);
                binding.txtTenKhach.setText(khachHang.getTenKhachHang());
                binding.txtSoDienThoai.setText(khachHang.getSoDienThoai());
                binding.txtDiaChi.setText(khachHang.getDiaChi());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        binding.txtTrangThaiDonHang.setText(donHang.getTrangThai().toString());
        String gia = String.valueOf(donHang.getTongTien());
        binding.txtTongTien.setText(gia);
        DonHangInfoAdapter donHangAdapter = new DonHangInfoAdapter(this,R.layout.donhang_item_sanpham,donHangInfos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rcDonHangInfo.setAdapter(donHangAdapter);
        binding.rcDonHangInfo.setLayoutManager(linearLayoutManager);
        firebase_manager.mDatabase.child("DonHangInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangInfos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHangInfo donHangInfo = dataSnapshot.getValue(DonHangInfo.class);
                    if (donHang.getTrangThai() != TrangThaiDonHang.Shipper_GiaoThanhCong) {
                        if (donHangInfo.getIDDonHang().equals(donHang.getIDDonHang())) {
                            donHangInfos.add(donHangInfo);
                            donHangAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}