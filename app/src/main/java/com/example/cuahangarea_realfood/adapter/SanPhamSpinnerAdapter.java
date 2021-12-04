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
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.model.Shipper;
import com.example.cuahangarea_realfood.model.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView;

public class SanPhamSpinnerAdapter extends ArrayAdapter implements ISpinnerSelectedView {
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<SanPham> sanPhams;
    ArrayList<SanPham> source;

    Context context;
    int resource;

    public SanPhamSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SanPham> sanPhams) {
        super(context, resource, sanPhams);
        this.context = context;
        this.resource = resource;
        this.sanPhams = sanPhams;
        this.source = sanPhams;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initVeiw(position, convertView, parent);

    }

    @Nullable
    @Override
    public SanPham getItem(int position) {
        return sanPhams.get(position);
    }

    @Override
    public int getCount() {
        return sanPhams.size();
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initVeiw(position, convertView, parent);
    }

    private View initVeiw(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_sanphamr, null);

        SanPham sanPham = sanPhams.get(position);
        TextView txtTen = convertView.findViewById(R.id.txtTenSanPham);
        ImageView imgAvata = convertView.findViewById(R.id.imgAvatar);
        ImageView imgcheck = convertView.findViewById(R.id.imgcheck);

        txtTen.setText(sanPham.getTenSanPham());
        firebase_manager.storageRef.child("SanPham").child(firebase_manager.auth.getUid()).child(sanPham.getIDSanPham()).child(sanPham.getImages().get(0)).
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context)
                            .load(uri.toString())
                            .into(imgAvata);
                } catch (Exception e) {
                    Log.d("link", uri.toString());
                }
            }
        });

        firebase_manager.mDatabase.child("Voucher").orderByChild("idSanPham").equalTo(sanPham.getIDSanPham()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    imgcheck.setVisibility(View.VISIBLE);
                } else {
                    imgcheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("Voucher").orderByChild("idCuaHang").equalTo(sanPham.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean res =false;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Voucher voucher = dataSnapshot.getValue(Voucher.class);
                    if (voucher.getSanPham().getIDSanPham().equals(sanPham.getIDSanPham()))
                    {
                        res = true;
                        break;
                    }
                }
                if (res == true) {

                    imgcheck.setVisibility(View.VISIBLE);
                } else {
                    imgcheck.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    sanPhams = source;
                } else {
                    ArrayList<SanPham> list = new ArrayList<>();
                    for (SanPham sanPham : source) {
                        if (sanPham.getTenSanPham().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(sanPham);
                        }
                    }
                    sanPhams = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sanPhams;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sanPhams = (ArrayList<SanPham>) results.values;
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
        View  convertView = LayoutInflater.from(context).inflate(R.layout.item_sanphamr, null);

        SanPham sanPham = sanPhams.get(position);
        TextView txtTen = convertView.findViewById(R.id.txtTenSanPham);
        ImageView imgAvata = convertView.findViewById(R.id.imgAvatar);
        ImageView imgcheck = convertView.findViewById(R.id.imgcheck);

        txtTen.setText(sanPham.getTenSanPham());
        firebase_manager.storageRef.child("SanPham").child(firebase_manager.auth.getUid()).child(sanPham.getIDSanPham()).child(sanPham.getImages().get(0)).
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context)
                            .load(uri.toString())
                            .into(imgAvata);
                } catch (Exception e) {
                    Log.d("link", uri.toString());
                }
            }
        });

        firebase_manager.mDatabase.child("Voucher").orderByChild("idcuaHang").equalTo(sanPham.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean res =false;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Voucher voucher = dataSnapshot.getValue(Voucher.class);
                    if (voucher.getSanPham().getIDSanPham().equals(sanPham.getIDSanPham()))
                    {
                        res = true;
                        break;
                    }
                }
                if (res == false) {
                    Toast.makeText(context, res+"", Toast.LENGTH_SHORT).show();
                    imgcheck.setVisibility(View.VISIBLE);
                } else {
                    imgcheck.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return convertView;

    }

}
