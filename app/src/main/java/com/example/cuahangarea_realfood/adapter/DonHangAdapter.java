package com.example.cuahangarea_realfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.SetOnLongClick;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.DonHang;
import com.example.cuahangarea_realfood.model.DonHangInfo;
import com.example.cuahangarea_realfood.model.KhachHang;
import com.example.cuahangarea_realfood.model.SanPham;
import com.example.cuahangarea_realfood.screen.ThongTinDonHangActivity;
import com.example.cuahangarea_realfood.screen.ThongTinSanPhamActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<DonHang> arrayList;
    ArrayList<DonHang> source;

    public SetOnLongClick setOnLongClick;
    Firebase_Manager firebase_manager = new Firebase_Manager();

    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public DonHangAdapter(Activity context, int resource, ArrayList<DonHang> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
        this.source = arrayList;
    }


    @NonNull
    @Override
    public DonHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        LinearLayout cardView = (LinearLayout) layoutInflater.inflate(viewType, parent, false);
        return new DonHangAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
        DonHang donHang = arrayList.get(position);
        holder.txtID.setText(donHang.getIDDonHang().substring(0, 9));
        holder.txtTrangThaiDonHang.setText(donHang.getTrangThai().toString());
        holder.txtTongTien.setText(donHang.getTongTien() + "");
        holder.txtDiaChi.setText(donHang.getDiaChi() + "");
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        String strDate= formatter.format(donHang.getNgayTao());
        holder.txtTime.setText(strDate);
        LoadButton(holder, donHang.getTrangThai());
        firebase_manager.mDatabase.child("KhachHang").child(donHang.getIDKhachHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KhachHang khachHang = snapshot.getValue(KhachHang.class);
                holder.txtTenKhach.setText(khachHang.getTenKhachHang());
                holder.txtSoDienThoai.setText(khachHang.getSoDienThoai());
                holder.txtDiaChi.setText(khachHang.getDiaChi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList<DonHangInfo> donHangInfos = new ArrayList<>();
//        DonHangInfoAdapter donHangAdapter = new DonHangInfoAdapter(context,R.layout.donhang_item_sanpham,donHangInfos);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        holder.rcvItemGiohang.setAdapter(donHangAdapter);
//        holder.rcvItemGiohang.setLayoutManager(linearLayoutManager);

        firebase_manager.mDatabase.child("DonHangInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.txtSanPham.setText("");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHangInfo donHangInfo = dataSnapshot.getValue(DonHangInfo.class);
                    if (donHang.getTrangThai() != TrangThaiDonHang.Shipper_GiaoThanhCong) {
                        if (donHangInfo.getIDDonHang().equals(donHang.getIDDonHang())) {
                            holder.txtSanPham.setText(holder.txtSanPham.getText() + donHangInfo.getSanPham().getTenSanPham() + "(" + donHangInfo.getSoLuong() + ")" + ", ");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.btnXacNhanCoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_DaGiaoChoBep);
                arrayList.set(position, temp);
                notifyDataSetChanged();
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notifyDataSetChanged();
                        LoadButton(holder, temp.getTrangThai());
                    }
                });
            }
        });
        holder.btnHoantac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                temp.setTrangThai(TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien);
                arrayList.set(position, temp);
                notifyDataSetChanged();
                firebase_manager.Ghi_DonHang(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notifyDataSetChanged();
                        LoadButton(holder, temp.getTrangThai());
                    }
                });
            }
        });
        holder.txtXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang temp = donHang;
                Intent intent = new Intent(context, ThongTinDonHangActivity.class);
                Gson gson = new Gson();
                String data = gson.toJson(donHang);
                intent.putExtra("donhang", data);
                context.startActivity(intent);
            }
        });
    }

    private void LoadButton(MyViewHolder holder, TrangThaiDonHang trangThai) {
        if (trangThai == TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien) {
            holder.btnXacNhanCoc.setVisibility(View.VISIBLE);

        } else {
            holder.btnXacNhanCoc.setVisibility(View.GONE);
        }
        if (trangThai == TrangThaiDonHang.SHOP_DangChuanBihang) {
            holder.btnHoantac.setVisibility(View.VISIBLE);

        } else {
            holder.btnHoantac.setVisibility(View.GONE);
        }
    }


    //Hàm để get layout type
    @Override
    public int getItemViewType(int position) {
        //1 list có 2 view
//        if(position%2==0)
//        {
//            ID layout A
//        }
//        else
//        {
//            ID layout B
//        }
        return resource;
    }

    //trả về số phần tử
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    arrayList = source;
                } else {
                    ArrayList<DonHang> list = new ArrayList<>();
                    for (DonHang donHang: source) {
                        if (donHang.getTrangThai().equals(strSearch)) {
                            list.add(donHang);
                        }
                    }
                    arrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<DonHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    //Define RecylerVeiw Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtXemChiTiet,txtID, txtTrangThaiDonHang, txtTenKhach, txtDiaChi, txtSoDienThoai, txtTongTien, txtSanPham,txtTime;
        ImageView imageView;
        RecyclerView rcvItemGiohang;
        ProgressBar progressBar;
        Button btnXacNhanCoc,btnHoantac;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.tv_IDDonHang);
            txtSoDienThoai = itemView.findViewById(R.id.txtSoDienThoai);
            txtTrangThaiDonHang = itemView.findViewById(R.id.txtTrangThaiDonHang);
            txtTenKhach = itemView.findViewById(R.id.txtTenKhach);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtSanPham = itemView.findViewById(R.id.txtSanPham);
            btnXacNhanCoc = itemView.findViewById(R.id.btnXacNhanCoc);
            progressBar = itemView.findViewById(R.id.progessbar);
            btnHoantac = itemView.findViewById(R.id.btnHoanTac1);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtXemChiTiet = itemView.findViewById(R.id.txtXemChiTiet);
        }
    }
}