package com.example.cuahangarea_realfood.adapter;

import android.app.Activity;
import android.graphics.Color;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangarea_realfood.Firebase_Manager;
import com.example.cuahangarea_realfood.R;
import com.example.cuahangarea_realfood.SetOnLongClick;
import com.example.cuahangarea_realfood.TrangThai.TrangThaiThongBao;
import com.example.cuahangarea_realfood.model.DanhMuc;
import com.example.cuahangarea_realfood.model.ThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<ThongBao> arrayList;
    public SetOnLongClick setOnLongClick;

    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    Firebase_Manager firebase_manager = new Firebase_Manager();
    public ThongBaoAdapter(Activity context, int resource, ArrayList<ThongBao> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        CardView cardView = (CardView) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ThongBao thongBao = arrayList.get(position);

        holder.txtTieuDe.setText(thongBao.getTieuDe());
        holder.txtNoiDung.setText(thongBao.getNoiDung());
        Log.d("Noti",thongBao.getNoiDung());
        if (thongBao.getTrangThaiThongBao()== TrangThaiThongBao.DaXem)
        {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        }
       firebase_manager.storageRef.child(thongBao.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("link",uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .into(holder.imageView);

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
        TextView txtTieuDe;
        ImageView imageView;
        TextView txtNoiDung;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_noti);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDung);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            linearLayout = itemView.findViewById(R.id.lnLayout);
        }
    }
}
