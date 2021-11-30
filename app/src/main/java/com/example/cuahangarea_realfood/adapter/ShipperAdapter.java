package com.example.cuahangarea_realfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import com.developer.kalert.KAlertDialog;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.screen.ThongTinShipperActivity;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiShipper;
import com.example.cuahangarea_realfood.model.Shipper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class ShipperAdapter extends ArrayAdapter implements Filterable {
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Context context;
    int resource;
    ArrayList<Shipper> data;
    ArrayList<Shipper> data1;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ShipperAdapter(@NonNull Context context, int resource, ArrayList<Shipper> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.data1 = data;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvHoten = convertView.findViewById(R.id.tv_hovaten);
        TextView tvTrangthai = convertView.findViewById(R.id.tv_trangthai);
        ImageButton btnmore = convertView.findViewById(R.id.btnMore);

        TextView tvSdt = convertView.findViewById(R.id.tv_sdt);
        TextView tvMaxe = convertView.findViewById(R.id.tv_maxe);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
        ImageView ivImage = convertView.findViewById(R.id.image_profile);
        Shipper shipper = data.get(position);
        tvHoten.setText(shipper.getHoVaTen());
        firebase_manager.SetColor( data.get(position).getTrangThaiShipper(),tvTrangthai);
        tvTrangthai.setText(firebase_manager.GetStringTrangThaiShipper(shipper.getTrangThaiShipper()));
        tvDiaChi.setText(shipper.getDiaChi());
        tvSdt.setText(shipper.getSoDienThoai());
        tvMaxe.setText(shipper.getMaSoXe());
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context)
                            .load(uri.toString())
                            .into(ivImage);
                }catch (Exception e)
                {

                }

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinShipperActivity.class);
                Gson gson = new Gson();
                String data = gson.toJson(shipper);
                intent.putExtra("Shipper", data);
                getContext().startActivity(intent);
            }
        });
        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, btnmore);
                popup.inflate(R.menu.popupmenu_shipper);

                Menu menu = popup.getMenu();
                // com.android.internal.view.menu.MenuBuilder
                Log.i("LOG_TAG", "Menu class: " + menu.getClass().getName());

                // Register Menu Item Click event.
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.mn_delete:
                            {
                                new KAlertDialog(context,KAlertDialog.WARNING_TYPE).setContentText("Bạn có chắc xóa shipper này ?")
                                        .setConfirmText("Có").setCancelText("Không").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                    @Override
                                    public void onClick(KAlertDialog kAlertDialog) {
                                        firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).removeValue();
                                        kAlertDialog.dismiss();
                                    }
                                }).show();
                                break;
                            }
                            case R.id.mn_lockOrUnlock:
                            {
                                if (shipper.getTrangThaiShipper()== TrangThaiShipper.BiKhoa)
                                {
                                    Alerter.create((Activity) context)
                                            .setTitle("Thông báo")
                                            .setText("Trạng thái shipper đã được cập nhật")
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                    firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).child("trangThaiShipper").setValue(TrangThaiShipper.KhongHoatDong);
                                }
                                else {
                                    Alerter.create((Activity) context)
                                            .setTitle("Thông báo")
                                            .setText("Trạng thái shipper đã được cập nhật")
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                    firebase_manager.mDatabase.child("Shipper").child(shipper.getiDShipper()).child("trangThaiShipper").setValue(TrangThaiShipper.BiKhoa);
                                }
                                break;
                            }
                        }
                        return true;
                    }
                });

                // Show the PopupMenu.
                popup.show();
            }
        });
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyWord = constraint.toString();
                if(keyWord.isEmpty()){
                    data = data1;
                }else {
                    List<Shipper> list = new ArrayList<>();
                    for (Shipper shipper : data1){
                        if(shipper.getHoVaTen().toLowerCase().contains(keyWord.toLowerCase())||shipper.getTrangThaiShipper().toString().equals(keyWord)){
                            list.add(shipper);
                        }
                    }
                    data = (ArrayList<Shipper>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<Shipper>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
