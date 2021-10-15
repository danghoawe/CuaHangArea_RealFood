package com.example.qlsv;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangarea_realfood.model.DanhMuc;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter <DanhMucAdapter.MyViewHolder>{
    private Activity context;
    private int resource;
    private ArrayList<DanhMuc> arrayList ;

    public DanhMucAdapter(Activity context, int resource, ArrayList<DanhMuc> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        CardView cardView = (CardView) layoutInflater.inflate(viewType,parent,false);
        return new MyViewHolder(cardView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


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
    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView txtMaSV ;
        TextView txtTenSV ;
        TextView txtThoiGianHoc;
        TextView txtXepLoai;
        TextView txtDiem ;
        TextView txtHocPhan ;
        ImageView imageView ;
        View.OnClickListener onClickListener;

        public MyViewHolder( View itemView) {
            super(itemView);
        }
    }
}
