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
import com.example.cuahangarea_realfood.TrangThai.LoaiThongBao;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiBaoCao;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiShipper;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiThongBao;
import com.example.cuahangarea_realfood.adapter.DonHangInfoAdapter;
import com.example.cuahangarea_realfood.adapter.DonHang_BepAdapter;
import com.example.cuahangarea_realfood.adapter.ShipperSpinnerAdapter;
import com.example.cuahangarea_realfood.databinding.ActivityThongTinDonHangBinding;
import com.example.cuahangarea_realfood.model.BaoCaoShipper;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.DonHangInfo;
import com.example.cuahangarea_realfood.model.KhachHang;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.model.Shipper;
import com.example.cuahangarea_realfood.model.ThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nordan.dialog.Animation;
import com.nordan.dialog.DialogType;
import com.nordan.dialog.NordanAlertDialog;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import karpuzoglu.enes.com.fastdialog.Animations;
import karpuzoglu.enes.com.fastdialog.FastDialog;
import karpuzoglu.enes.com.fastdialog.PositiveClick;

public class ThongTinDonHangActivity extends AppCompatActivity {
    ActivityThongTinDonHangBinding binding;
    ArrayList<DonHangInfo> donHangInfos;
    DonHang donHang;
    ArrayList<Shipper> shippers = new ArrayList<>();
    ShipperSpinnerAdapter shipperSpinnerAdapter;
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
                donHang = temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Alerter.create(ThongTinDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Đơn hàng đã giao cho khu vực bếp")
                                .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                });
            }
        });
        binding.btnHoanTac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien);
                donHang = temp;
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
                donHang = temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Alerter.create(ThongTinDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Đã xác nhận hoàn hàng từ shipper")
                                .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                });
            }
        });
        binding.btnXacNhanTraTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.Shipper_DaChuyenTien);
                temp.setNgayGiaoHang(new Date());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        donHang = temp;
                        LoadData();
                        LoadButton(donHang.getTrangThai());
                        for (DonHangInfo donHangInfo :
                                donHangInfos
                        ) {
                            SanPham sanPham = donHangInfo.getSanPham();
                            sanPham.setSoLuongBanDuoc(sanPham.getSoLuongBanDuoc() + 1);
                            firebase_manager.Ghi_SanPham(sanPham);
                        }
                        Alerter.create(ThongTinDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Đơn hàng đã hoành thành")
                                .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                });
            }
        });
        binding.btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new NordanAlertDialog.Builder(ThongTinDonHangActivity.this)
                        .setDialogType(DialogType.WARNING)
                        .setAnimation(Animation.SLIDE)
                        .isCancellable(true)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có chắc hủy đơn hàng ? ")
                        .setPositiveBtnText("Oke")
                        .onPositiveClicked(() -> {
                            DonHang temp = donHang;
                            temp.setTrangThai(TrangThaiDonHang.SHOP_HuyDonHang);
                            donHang = temp;
                            LoadData();
                            LoadButton(donHang.getTrangThai());
                            firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Alerter.create(ThongTinDonHangActivity.this)
                                            .setTitle("Thông báo")
                                            .setText("Đã hủy :  " + donHang.getIDDonHang())
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                }
                            });
                        })
                        .setNegativeBtnText("Hủy")
                        .onNegativeClicked(() -> {
                        })
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
                donHang = temp;
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
                donHang = temp;
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
                        if (dialog.getInputText() != null) {
                            DonHang temp = donHang;
                            temp.setTrangThai(TrangThaiDonHang.Bep_DaHuyDonHang);
                            temp.setGhiChuCuaHang(dialog.getInputText());
                            donHang = temp;
                            LoadData();
                            LoadButton(donHang.getTrangThai());
                            firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Alerter.create(ThongTinDonHangActivity.this)
                                            .setTitle("Thông báo")
                                            .setText("Đơn hàng đã được hủy vì lí do: " + dialog.getInputText())
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                }
                            });
                        } else {
                            Alerter.create(ThongTinDonHangActivity.this)
                                    .setTitle("Lỗi")
                                    .setText("Vui lòng không để trống! ")
                                    .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();

                        }
                    }
                });
                dialog.show();
            }
        });

        binding.btnGiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.spDSShipper.getSelectedItem() != null) {
                    TrangThaiShipper trangThaiShipper = shippers.get(binding.spDSShipper.getSelectedPosition()).getTrangThaiShipper();
                    if (trangThaiShipper != TrangThaiShipper.BiKhoa && trangThaiShipper != TrangThaiShipper.KhongHoatDong) {
                        DonHang temp = donHang;
                        temp.setTrangThai(TrangThaiDonHang.SHOP_DangGiaoShipper);
                        temp.setIDShipper(shippers.get(binding.spDSShipper.getSelectedPosition()).getiDShipper());
                        donHang = temp;
                        LoadData();
                        LoadButton(donHang.getTrangThai());

                        firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Alerter.create(ThongTinDonHangActivity.this)
                                        .setTitle("Thông báo")
                                        .setText("Đã gửi yêu cầu lấy hàng đến shipper")
                                        .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                        .show();
                            }
                        });
                        String noiDung = "Bạn đã nhận được 1 đơn hàng mới : "+donHang.getIDDonHang()+"\nVui lòng kiểm tra và giao hàng cho khách!";
                        String uuid = UUID.randomUUID().toString().replace("-", "");
                        ThongBao thongBao = new ThongBao(uuid,noiDung,"Thông báo","",donHang.getIDShipper(),"", TrangThaiThongBao.ChuaXem,new Date());
                        thongBao.setLoaiThongBao(LoaiThongBao.DonHangShipper);
                        thongBao.setDonHang(donHang);
                        firebase_manager.mDatabase.child("ThongBao").child(donHang.getIDShipper()).child(uuid).setValue(thongBao);
                    } else {
                        Alerter.create(ThongTinDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Shipper không khả dụng!")
                                .setBackgroundColorRes(R.color.error) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                } else {
                    Alerter.create(ThongTinDonHangActivity.this)
                            .setTitle("Thông báo")
                            .setText("Vui lòng chọn shipper")
                            .setBackgroundColorRes(R.color.error) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                }

            }
        });
        binding.btnDaGiaoHangChoShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.Shipper_DaLayHang);
                donHang = temp;
                LoadData();
                LoadButton(donHang.getTrangThai());
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Alerter.create(ThongTinDonHangActivity.this)
                                .setTitle("Thông báo")
                                .setText("Đã giao hàng cho shipper")
                                .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                });
            }

        });
        binding.lnBaoCaoShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastDialog dialog = FastDialog.w(ThongTinDonHangActivity.this)
                        .setTitleText("Thông báo")
                        .setText("Bạn muốn báo cáo Shipper này?")
                        .setHint("Vui lòng nhập lí do")
                        .setAnimation(Animations.GROW_IN)
                        .positiveText("Báo cáo")
                        .negativeText("Hủy")
                        .create();

                dialog.positiveClickListener(new PositiveClick() {
                    @Override
                    public void onClick(View view) {
                        if (dialog.getInputText() != null) {
                            String uuid = UUID.randomUUID().toString().replace("-", "");
                            BaoCaoShipper baoCaoShipper = new BaoCaoShipper(uuid, firebase_manager.auth.getUid(), donHang.getIDShipper(), dialog.getInputText(), "Thông báo", new Date(), TrangThaiBaoCao.ChuaXem);
                            Alerter.create(ThongTinDonHangActivity.this)
                                    .setTitle("Thông báo")
                                    .setText("Đã gửi báo cáo đến admin về shipper : " + baoCaoShipper.getIdBaoCao())
                                    .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                            firebase_manager.mDatabase.child("BaoCao_CuaHang_Shipper").child(uuid).setValue(baoCaoShipper);
                            dialog.dismiss();
                        } else {
                            Alerter.create(ThongTinDonHangActivity.this)
                                    .setTitle("Thông báo")
                                    .setText("Vui lòng không để trống !")
                                    .setBackgroundColorRes(R.color.error) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                    }
                });
                dialog.show();
            }
        });
        binding.radHeThong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadShipper();
            }
        });
        binding.radCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadShipper();
            }
        });
        binding.spDSShipper.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                if (shippers.get(position).getTrangThaiShipper() == TrangThaiShipper.BiKhoa || shippers.get(position).getTrangThaiShipper() == TrangThaiShipper.KhongHoatDong) {
                    Alerter.create(ThongTinDonHangActivity.this)
                            .setTitle("Thông báo")
                            .setText("Shipper không khả dụng!")
                            .setBackgroundColorRes(R.color.error) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void LoadButton(TrangThaiDonHang trangThai) {

        if (trangThai == TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien) {
            binding.btnXacNhanCoc.setVisibility(View.VISIBLE);
            binding.btnHuyDonHang.setVisibility(View.VISIBLE);
            binding.lnNew.setVisibility(View.VISIBLE);
            binding.imgTick.setVisibility(View.GONE);

        } else {
            binding.btnXacNhanCoc.setVisibility(View.GONE);
            binding.btnHuyDonHang.setVisibility(View.GONE);
            binding.lnNew.setVisibility(View.GONE);
            binding.imgTick.setVisibility(View.VISIBLE);
        }
        if (trangThai.equals(TrangThaiDonHang.SHOP_DangChuanBihang)) {
            binding.btnHoanTac1.setVisibility(View.VISIBLE);

        } else {
            binding.btnHoanTac1.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.ChoShopXacNhan_Tien) {
            binding.btnXacNhanTraTien.setVisibility(View.VISIBLE);
            binding.lnBaoCaoShipper.setVisibility(View.VISIBLE);
        } else {
            binding.btnXacNhanTraTien.setVisibility(View.GONE);
            binding.lnBaoCaoShipper.setVisibility(View.GONE);

        }
        if (trangThai == TrangThaiDonHang.ChoShopXacNhan_TraHang) {
            binding.btnXacNhanHoanHang.setVisibility(View.VISIBLE);
            binding.lnBaoCaoShipper.setVisibility(View.VISIBLE);
        } else {
            binding.btnXacNhanHoanHang.setVisibility(View.GONE);
            binding.lnBaoCaoShipper.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.Shipper_DaLayHang || trangThai == TrangThaiDonHang.ChoShopXacNhan_TraHang
                || trangThai == TrangThaiDonHang.ChoShopXacNhan_Tien || trangThai == TrangThaiDonHang.Shipper_DaTraHang
                || trangThai == TrangThaiDonHang.Shipper_GiaoThanhCong || trangThai == TrangThaiDonHang.Shipper_GiaoKhongThanhCong
                || trangThai == TrangThaiDonHang.Shipper_DaChuyenTien) {
            binding.lnBaoCaoShipper.setVisibility(View.VISIBLE);
            binding.lnTTShipper.setVisibility(View.VISIBLE);
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
        if (trangThai == TrangThaiDonHang.SHOP_DaChuanBiXong || trangThai == TrangThaiDonHang.Shipper_KhongNhanGiaoHang) {
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
        if (binding.radHeThong.isChecked()) {
            firebase_manager.mDatabase.child("Shipper").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    shippers.clear();
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()
                    ) {
                        Shipper shipper = dataSnapshot.getValue(Shipper.class);
                        shippers.add(shipper);
                    }
                    shipperSpinnerAdapter = new ShipperSpinnerAdapter(ThongTinDonHangActivity.this, R.layout.item_shipper, shippers);
                    binding.spDSShipper.setAdapter(shipperSpinnerAdapter);
                }
            });
        } else {
            firebase_manager.mDatabase.child("Shipper").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    shippers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()
                    ) {
                        Shipper shipper = snapshot.getValue(Shipper.class);
                        if (shipper.getIdCuaHang().equals(firebase_manager.auth.getUid())) {
                            shippers.add(shipper);
                        }
                    }
                    shipperSpinnerAdapter = new ShipperSpinnerAdapter(ThongTinDonHangActivity.this, R.layout.item_shipper, shippers);
                    binding.spDSShipper.setAdapter(shipperSpinnerAdapter);
                }
            });
        }

    }

    private void LoadData() {

        firebase_manager.mDatabase.child("DonHang").child(donHang.getIDDonHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonHang temp = snapshot.getValue(DonHang.class);
                donHang = temp;
                binding.txtSoDienThoai.setText(donHang.getSoDienThoai());
                binding.txtDiaChi.setText(donHang.getDiaChi());
                binding.tvIDDonHang.setText(donHang.getIDDonHang().substring(0,25));
                binding.txtTrangThaiDonHang.setText(firebase_manager.GetStringTrangThaiDonHang(donHang.getTrangThai()));
                String gia = String.valueOf(donHang.getTongTien());
                binding.txtTongTien.setText(gia);
                LoadButton(donHang.getTrangThai());
                if (donHang.getIDShipper().isEmpty()) {
                    binding.lnTTShipper.setVisibility(View.GONE);
                } else {
                    binding.lnTTShipper.setVisibility(View.VISIBLE);
                    firebase_manager.mDatabase.child("Shipper").child(donHang.getIDShipper()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Shipper temp = snapshot.getValue(Shipper.class);
                            if (temp != null) {
                                binding.txtTenShipper.setText(temp.getHoVaTen());
                                binding.txtDiaChiShipper.setText(temp.getSoDienThoai());
                                binding.txtSoDienThoaiShipper.setText(temp.getDiaChi());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        firebase_manager.mDatabase.child("KhachHang").child(donHang.getIDKhachHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KhachHang khachHang = snapshot.getValue(KhachHang.class);
                binding.txtTenKhach.setText(khachHang.getTenKhachHang());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DonHangInfoAdapter donHangAdapter = new DonHangInfoAdapter(this, R.layout.donhang_item_sanpham, donHangInfos);
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