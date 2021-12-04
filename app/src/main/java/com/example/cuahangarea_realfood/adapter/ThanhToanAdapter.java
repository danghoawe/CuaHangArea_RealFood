package com.example.cuahangarea_realfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.cuahangarea_realfood.Firebase_Manager;

import com.example.cuahangarea_realfood.R;

import com.example.cuahangarea_realfood.TrangThai.TrangThaiThanhToan;
import com.example.cuahangarea_realfood.model.CuaHang;
import com.example.cuahangarea_realfood.model.ThanhToan;
import com.example.cuahangarea_realfood.screen.GuiThongBaoActivity;
import com.example.cuahangarea_realfood.screen.ThongTinThanhToanActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.MyViewHolder>implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<ThanhToan> arrayList;
    private ArrayList<ThanhToan> source;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    StorageReference storageRef  = FirebaseStorage.getInstance().getReference();
    public ThanhToanAdapter(Activity context, int resource, ArrayList<ThanhToan> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
        this.source = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        SwipeRevealLayout cardView = (SwipeRevealLayout) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (arrayList.size()>0)
        {
            ThanhToan thanhToan = arrayList.get(position);
            holder.idThanhToan.setText("ID: "+thanhToan.getIdBill().substring(0,20));
            holder.tvSoTien.setText(thanhToan.getSoTien() + " VND");
            holder.tvTrangThai.setText(firebase_manager.GetStringTrangThaiHoaDon(thanhToan.getTrangThaiThanhToan()));
           firebase_manager.SetColorTrangThaiHoaDonr(thanhToan.getTrangThaiThanhToan(),holder.tvTrangThai);
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            String strDate= formatter.format(thanhToan.getNgayThanhToan());
            holder.tvThoiGian.setText(strDate);
            if (thanhToan.getTrangThaiThanhToan()==TrangThaiThanhToan.DaXacNhan)
            {
                holder.btnXacNhan.setVisibility(View.GONE);
            }
            else {
                holder.btnXacNhan.setVisibility(View.VISIBLE);
            }
            firebase_manager.mDatabase.child("CuaHang").child(thanhToan.getIdCuaHang()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CuaHang cuaHang = snapshot.getValue(CuaHang.class);

                    holder.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });


            firebase_manager.storageRef.child("ThanhToan").child(thanhToan.getIdBill()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    try {
                        Glide.with(context)
                                .load(uri.toString())
                                .into(holder.ivThanhToan);
                    }
                    catch (Exception e)
                    {

                    }

                }
            });
            holder.ivThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThongTinThanhToanActivity.class);
                    Gson gson = new Gson();
                    String data = gson.toJson(thanhToan);
                    intent.putExtra("ThanhToan", data);
                    context.startActivity(intent);
                }
            });
            holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebase_manager.mDatabase.child("ThanhToan").child(thanhToan.getIdBill()).child("trangThaiThanhToan").setValue(TrangThaiThanhToan.DaXacNhan);
                }
            });
            holder.btnHoTro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GuiThongBaoActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    //Hàm để get layout type
    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    //trả về số phần tử
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //Define RecylerVeiw Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenCuaHang,idThanhToan,tvSoTien,tvThoiGian,tvTrangThai;
        ImageView ivThanhToan;
        Button btnXacNhan,btnHoTro;

        public MyViewHolder(View itemView) {
            super(itemView);
            idThanhToan = itemView.findViewById(R.id.idThanhToan);
            tvTenCuaHang = itemView.findViewById(R.id.tvTenCuaHang);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            ivThanhToan = itemView.findViewById(R.id.ivThanhToan);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
            btnHoTro = itemView.findViewById(R.id.btnBaoCao);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyWord = constraint.toString();
                if(keyWord.isEmpty()){
                    arrayList = source;
                }else {
                    List<ThanhToan> list = new ArrayList<>();
                    for (ThanhToan thanhToan : arrayList){
                        if(thanhToan.getIdCuaHang().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(thanhToan);
                        }
                    }
                    arrayList = (ArrayList<ThanhToan>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<ThanhToan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
