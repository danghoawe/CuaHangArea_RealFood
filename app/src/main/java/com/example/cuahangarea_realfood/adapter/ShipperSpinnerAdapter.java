package com.example.cuahangarea_realfood.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.model.NganHang;
import com.example.cuahangarea_realfood.model.Shipper;
import com.example.cuahangarea_realfood.screen.ThongTinDonHangActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView;

public class ShipperSpinnerAdapter extends ArrayAdapter implements ISpinnerSelectedView {
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<Shipper>shippers;
    ArrayList<Shipper>source;

    Context context;
    int resource;
    public ShipperSpinnerAdapter(@NonNull Context context, int resource, @NonNull  ArrayList<Shipper>shippers) {
        super(context, resource, shippers);
        this.context =context;
        this.resource = resource;
        this.shippers = shippers;
        this.source = shippers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initVeiw(position, convertView, parent);

    }
    @Nullable
    @Override
    public Shipper getItem(int position) {
        return shippers.get(position);
    }

    @Override
    public int getCount() {
        return shippers.size();
    }


    @Override
    public View getDropDownView(int position, @Nullable  View convertView, @NonNull  ViewGroup parent) {
        return initVeiw(position, convertView, parent);
    }
    private View initVeiw(int position,   View convertView,   ViewGroup parent)
    {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_shipper,null);

        Shipper shipper = shippers.get(position);
        TextView txtTen = convertView.findViewById(R.id.txtTenShipper);
        TextView txtTrangThai = convertView.findViewById(R.id.txtTrangThaiHoatDong);
        ImageView imgAvata = convertView.findViewById(R.id.imgAvatar);
        TextView txtKhuVucHoatDong = convertView.findViewById(R.id.txtKhuVucHoatDong);
        TextView txtSoDienThoai = convertView.findViewById(R.id.txtSoDienThoai);

        txtTen .setText(shipper.getHoVaTen());
        txtKhuVucHoatDong .setText(shipper.getDiaChi());
        firebase_manager.SetColor( shipper.getTrangThaiShipper(),txtTrangThai);
        txtTrangThai.setText(firebase_manager.GetStringTrangThaiShipper(shipper.getTrangThaiShipper()));
        txtSoDienThoai.setText(shipper.getSoDienThoai());


        firebase_manager.storageRef.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull  Task<Uri> task) {

                if (task.isSuccessful())
                {
                    try {
                        Glide.with(context)
                                .load(task.getResult())
                                .into(imgAvata);
                    }catch (Exception e){

                    }

                }

            }
        });
        return convertView;
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    shippers = source;
                } else {
                    ArrayList<Shipper> list = new ArrayList<>();
                    for (Shipper shipper : source) {
                        if (shipper.getHoVaTen().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(shipper);
                        }
                    }
                    shippers = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = shippers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shippers = (ArrayList<Shipper>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public View getNoSelectionView() {
        return null;
    }

    @Override
    public View getSelectedView(int position) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_shipper,null);

        Shipper shipper = shippers.get(position);


        TextView txtTen = convertView.findViewById(R.id.txtTenShipper);
        TextView txtTrangThai = convertView.findViewById(R.id.txtTrangThaiHoatDong);
        ImageView imgAvata = convertView.findViewById(R.id.imgAvatar);
        TextView txtKhuVucHoatDong = convertView.findViewById(R.id.txtKhuVucHoatDong);
        TextView txtSoDienThoai = convertView.findViewById(R.id.txtSoDienThoai);

        txtTen .setText(shipper.getHoVaTen());
        txtKhuVucHoatDong .setText(shipper.getDiaChi());
        firebase_manager.SetColor( shipper.getTrangThaiShipper(),txtTrangThai);
        txtTrangThai.setText(firebase_manager.GetStringTrangThaiShipper(shipper.getTrangThaiShipper()));
        txtSoDienThoai.setText(shipper.getSoDienThoai());
        firebase_manager.storageRef.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull  Task<Uri> task) {
                try {
                    Glide.with(context)
                            .load(task.getResult())
                            .into(imgAvata);
                }catch (Exception e)
                {
                    Log.d("Image: ","Not found");
                }

            }
        });
        return convertView;

    }

}
