package com.example.cuahangarea_realfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.Screen.Home;
import com.example.cuahangarea_realfood.Screen.ThongTinSanPhamActivity;
import com.example.cuahangarea_realfood.SetOnLongClick;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;


public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<SanPham> arrayList;
    public SetOnLongClick setOnLongClick;

    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    StorageReference storageRef  = FirebaseStorage.getInstance().getReference();
    FirebaseAuth  auth= FirebaseAuth.getInstance();
    public SanPhamAdapter(Activity context, int resource, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        LinearLayout cardView = (LinearLayout) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham sanPham = arrayList.get(position);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);

        holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        int number = Integer.parseInt(sanPham.getGia());
        String price =format.format(number);
        holder.txtGia.setText(price);
        storageRef.child("SanPham").child(auth.getUid()).child(sanPham.getIDSanPham()).child(sanPham.getImage().get(0)).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(context)
                        .load(task.getResult().toString())
                        .into(holder.imageView);
                holder.progressBar.setVisibility(View.GONE);
                Log.d("link",task.getResult().toString());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (setOnLongClick!=null)
                {
                    setOnLongClick.onLongClick(position);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ThongTinSanPhamActivity.class);

                intent.putExtra("sampleObject", sanPham);
                context.startActivity(intent);

                startActivity(i);
            }
        });

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

    //Define RecylerVeiw Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham;
        TextView txtGia;
        ImageView imageView;
        ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAnh);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGia = itemView.findViewById(R.id.txtGia);
            progressBar = itemView.findViewById(R.id.progessbar);
        }
    }
}
