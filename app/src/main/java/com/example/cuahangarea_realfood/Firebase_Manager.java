package com.example.cuahangarea_realfood;

import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.cuahangarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.cuahangarea_realfood.adapter.DanhMucAdapter;
import com.example.cuahangarea_realfood.adapter.DonHangAdapter;
import com.example.cuahangarea_realfood.adapter.DonHang_BepAdapter;
import com.example.cuahangarea_realfood.adapter.MaGiamGiaAdapter;
import com.example.cuahangarea_realfood.adapter.SanPhamAdapter;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.model.TaiKhoanNganHang;
import com.example.cuahangarea_realfood.model.ThongBao;
import com.example.cuahangarea_realfood.model.Voucher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Firebase_Manager {
    public  DatabaseReference mDatabase ;
    public StorageReference storageRef ;
    public FirebaseAuth auth;
    public Firebase_Manager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        auth= FirebaseAuth.getInstance();
    }

    public Task<Void> Ghi_ThongBao(ThongBao thongBao)
    {
        return  mDatabase.child("ThongBao").child(auth.getUid()).child(thongBao.getIDThongBao()).setValue(thongBao);
    }
    public Task<Void> Ghi_DonHang(DonHang donHang)
    {
        return  mDatabase.child("DonHang").child(donHang.getIDDonHang()).setValue(donHang);
    }
    public Task<Void> Ghi_NganHang(TaiKhoanNganHang taiKhoanNganHang)
    {
        return  mDatabase.child("TaiKhoanNganHang").child(taiKhoanNganHang.getId()).setValue(taiKhoanNganHang);
    }
    public Task<Void> Ghi_CuaHang(CuaHang cuaHang)
    {
      return  mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
    }
    public void Ghi_DanhMuc(DanhMuc danhMuc)
    {
        mDatabase.child("DanhMuc").child(auth.getUid()).child(danhMuc.getIDDanhMuc()).setValue(danhMuc);
    }
    public Task<Void> Ghi_Voucher(Voucher voucher)
    {
      return  mDatabase.child("Voucher").child(voucher.getIdMaGiamGia()).setValue(voucher);
    }

    public void GetSanPham(ArrayList arrayList, SanPhamAdapter sanPhamAdapter) {
        mDatabase.child("SanPham").orderByChild("idcuaHang").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    arrayList.add(sanPham);
                    if (sanPhamAdapter!=null)
                    {
                        sanPhamAdapter.notifyDataSetChanged();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetDonHang(ArrayList arrayList, DonHangAdapter donHangAdapter) {
        mDatabase.child("DonHang").orderByChild("ngayTao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getIDCuaHang().equals(auth.getUid()))
                    {
                        arrayList.add(donHang);
                        if (donHangAdapter!=null)
                        {
                            donHangAdapter.notifyDataSetChanged();
                        }
                    }
                }
                Collections.sort(arrayList, new Comparator<DonHang>() {
                    @Override
                    public int compare(DonHang o1, DonHang o2) {
                        return o2.getNgayTao().compareTo(o1.getNgayTao());
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetDonHang_Bep(ArrayList arrayList, DonHang_BepAdapter donHangAdapter) {
        mDatabase.child("DonHang").orderByChild("ngayTao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getIDCuaHang().equals(auth.getUid()))
                    {
                        if (donHang.getTrangThai()== TrangThaiDonHang.SHOP_DangChuanBihang||
                                donHang.getTrangThai()== TrangThaiDonHang.SHOP_DaChuanBiXong||
                                donHang.getTrangThai()== TrangThaiDonHang.SHOP_DangGiaoShipper||
                                donHang.getTrangThai()==TrangThaiDonHang.SHOP_DaGiaoChoBep)
                        {
                            arrayList.add(donHang);
                        }
                        if (donHangAdapter!=null)
                        {
                            donHangAdapter.notifyDataSetChanged();
                        }
                    }
                }
                Collections.sort(arrayList, new Comparator<DonHang>() {
                    @Override
                    public int compare(DonHang o1, DonHang o2) {
                        return o2.getNgayTao().compareTo(o1.getNgayTao());
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetVoucher(ArrayList arrayList, MaGiamGiaAdapter maGiamGiaAdapter) {
        mDatabase.child("Voucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Voucher voucher = postSnapshot.getValue(Voucher.class);
                    if (voucher.getSanPham().getIDCuaHang().equals(auth.getUid()))
                    {
                        arrayList.add(voucher);
                    }
                }
                if (maGiamGiaAdapter!=null)
                {
                    maGiamGiaAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetSanPham_V2(ArrayList arrayList, ArrayAdapter sanPhamAdapter) {
        mDatabase.child("SanPham").orderByChild("idcuaHang").equalTo(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    arrayList.add(sanPham.getTenSanPham());
                }
                sanPhamAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void GetDanhSachDanhMuc(ArrayList<DanhMuc>danhMucs, DanhMucAdapter danhMucAdapter) {
        mDatabase.child("DanhMuc").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucs.add(danhMuc);
                    if (danhMucAdapter!=null)
                    {
                        danhMucAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public Task<Void> Ghi_SanPham(SanPham sanPham)
    {
       return mDatabase.child("SanPham").child((sanPham.getIDSanPham())).setValue(sanPham);
    }
    public UploadTask UpImageDanhMuc(Uri truoc, String danhMuc)
    {
       return storageRef.child("DanhMuc").child(danhMuc).child("image").putFile(truoc);
    }
    public void UpImageSanPham(ArrayList<Uri> image, String idSanPham,ArrayList<String>nameImage)
    {
        for (int i = 0; i<image.size();i++
        ) {
            try {
                storageRef.child("SanPham").child(auth.getUid()).child(idSanPham).child(nameImage.get(i)).putFile(image.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.d("Upload Image: ","Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Log.d("Upload Image: ","Faild");

                    }
                });
            }catch (Exception e)
            {
                Log.d("Firebase: UpLoad",e.getMessage());
            }
        }
    }
    public ArrayList<DanhMuc> GetDanhMuc()
    {
        ArrayList<DanhMuc> danhMucs = new ArrayList<>();
        mDatabase.child("DanhMuc").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucs.add(danhMuc);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return  danhMucs;
    }

    public void Up2MatCMND(Uri truoc, Uri sau, String IDCuaHang)
    {
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatTruoc").putFile(truoc);
        storageRef.child("CuaHang").child(IDCuaHang).child("CMND_MatSau").putFile(sau);
    }
    public UploadTask UpAvatar(Uri Avatar)
    {
        return   storageRef.child("CuaHang").child(auth.getUid()).child("Avatar").putFile(Avatar);

    }
    public UploadTask UpWallPaper(Uri WallPaper)
    {
        return   storageRef.child("CuaHang").child(auth.getUid()).child("WallPaper").putFile(WallPaper);
    }

    public CuaHang getCuaHang(){
        final CuaHang[] cuaHang = new CuaHang[1];
        mDatabase.child("CuaHang").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               CuaHang temp = dataSnapshot.getValue(CuaHang.class);
               cuaHang[0] = temp;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return cuaHang[0];
    }

}
