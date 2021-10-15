package com.example.cuahangarea_realfood;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cuahangarea_realfood.model.DanhMuc;
import com.google.firebase.auth.FirebaseAuth;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.UUID;


public class DanhMuc_DialogFragment extends DialogFragment {
    Firebase_Manager firebase_manager = new Firebase_Manager();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Validate validate = new Validate();
    public  OnInputSelected onInputSelected;
    static final String TAG = "DanhMuc_DialogFragment";
    static final int code = 1;
    Uri image;
    Button btnThemDanhMuc;
    EditText edtTenDanhMuc;
    ImageView imgAnh;

    public  interface OnInputSelected{
        void setInputUpdate(DanhMuc danhMuc);
        void setInput(DanhMuc danhMuc);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danh_muc__dialog, container, false);

        btnThemDanhMuc = view.findViewById(R.id.btnThem);
        imgAnh = view.findViewById(R.id.imgAnh);
        edtTenDanhMuc = view.findViewById(R.id.edtTenDanhMuc);

        btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate.isBlank(edtTenDanhMuc))
                {
                    String uuid = UUID.randomUUID().toString().replace("-","");
                    DanhMuc danhMuc= new DanhMuc(uuid,auth.getUid(),edtTenDanhMuc.getText().toString());
                    firebase_manager.Ghi_DanhMuc(danhMuc);
                    firebase_manager.UpImageDanhMuc(image,uuid);
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                imgAnh.setImageBitmap(r.getBitmap());
                                image = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity());
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onInputSelected = (OnInputSelected)getTargetFragment();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DanhMuc_DialogFragment.code)
        {

        }

    }
}