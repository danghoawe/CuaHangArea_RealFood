package com.example.cuahangarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.Fragment.DanhMuc_DialogFragment;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.Validate;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.SanPham;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nordan.dialog.Animation;
import com.nordan.dialog.DialogType;
import com.nordan.dialog.NordanAlertDialog;
import com.nordan.dialog.NordanLoadingDialog;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThongTinSanPhamActivity extends AppCompatActivity {
    List<CarouselItem> list = new ArrayList<>();
    List<DanhMuc> listDanhMuc = new ArrayList<>();
    List<String> namesDanhMuc = new ArrayList<>();
    ArrayAdapter adapter ;

    ImageCarousel carousel;
    Button btnThemAnh, btnThemSanPham;
    private String image;
    EditText edtTenSanPham, edtSize, edtDonGia, edtThongTinChiTiet;
    Spinner spDanhMuc, spLoaiSanPham;
    Validate validate = new Validate();
    ImageView imageView,imageDelete;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<String> images = new ArrayList<>();
    ArrayList<Uri> uriImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        setControl();
        LoadData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetEvent();
        getDanhMuc();
    }

    private void setControl() {
        carousel = findViewById(R.id.carousel);
        btnThemAnh = findViewById(R.id.btnThemAnh);
        edtDonGia = findViewById(R.id.edtDonGia);
        edtSize = findViewById(R.id.edtSize);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtThongTinChiTiet = findViewById(R.id.edtThongTinChiTiet);
        spDanhMuc = findViewById(R.id.spDanhMucSanPham);
        spLoaiSanPham = findViewById(R.id.spLoaiSanPham);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
        imageView = findViewById(R.id.imagebackground);
        imageDelete = findViewById(R.id.imageDelete);
    }

    private void LoadData() {
        if (list.size() == 0) {
            imageView.setVisibility(View.VISIBLE);
            imageDelete.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.GONE);
            imageDelete.setVisibility(View.VISIBLE);
        }
    }
    private void SetEvent() {
        btnThemAnh.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
                                                  @Override
                                                  public void onPickResult(PickResult r) {
                                                      image = r.getUri().toString();
                                                      list.add(new CarouselItem(image));
                                                      carousel.setData(list);
                                                      uriImages.add(r.getUri());
                                                      images.add("Image" + images.size());
                                                      LoadData();
                                                  }
                                              })
                                                      .show(ThongTinSanPhamActivity.this);
                                          }
                                      }
        );
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validated_Form()) {
                    KAlertDialog kAlertDialog = new KAlertDialog(ThongTinSanPhamActivity.this, KAlertDialog.PROGRESS_TYPE);
                    kAlertDialog.show();


                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    SanPham sanPham = new SanPham(uuid, edtTenSanPham.getText().toString(), "", "", edtDonGia.getText().toString(), edtThongTinChiTiet.getText().toString(), firebase_manager.auth.getUid(), (float) 0.0, images);
                    firebase_manager.UpImageSanPham(uriImages, uuid, images);
                    firebase_manager.Ghi_SanPham(sanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                            kAlertDialog.setContentText("Đã thêm sản phẩm thành công");
//                            Dialog a = new NordanAlertDialog.Builder(DangKyActivity.this)
//                                    .setAnimation(Animation.SIDE)
//                                    .isCancellable(false)
//                                    .setTitle("Thông báo")
//                                    .setMessage("Thêm danh mục thành công")
//                                    .setPositiveBtnText("Ok")
//                                    .setDialogType(DialogType.SUCCESS)
//                                    .onPositiveClicked(() -> {/* Do something here */})
//                                    .build();
//                            a.show();
                        }
                    });
                } else {
                    Toast.makeText(ThongTinSanPhamActivity.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               list.remove( carousel.getCurrentPosition());
               LoadData();
               carousel.setData(list);
            }
        });
    }

    private boolean Validated_Form() {
        boolean result = false;
        if (!validate.isBlank(edtTenSanPham) && !validate.isBlank(edtThongTinChiTiet)
                && !validate.isBlank(edtSize) && !validate.isBlank(edtDonGia)
                && validate.isNumber(edtDonGia)
        ) {

            result = true;
        }
        return result;
    }
    private void getDanhMuc(){
        firebase_manager.mDatabase.child("DanhMuc").child(firebase_manager.auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDanhMuc.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    listDanhMuc.add(danhMuc);
                    namesDanhMuc.add(danhMuc.getTenDanhMuc());
                }
                adapter = new ArrayAdapter(ThongTinSanPhamActivity.this,
                        android.R.layout.simple_expandable_list_item_1 ,
                        namesDanhMuc);
                spDanhMuc.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Log.d("A",namesDanhMuc.size()+"");
    }

}