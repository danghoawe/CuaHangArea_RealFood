package com.example.cuahangarea_realfood.model;

public class LoaiSanPham {
  String  IDLoai;
    String TenLoai;

    public LoaiSanPham() {
    }

    public String getIDLoai() {
        return IDLoai;
    }

    public void setIDLoai(String IDLoai) {
        this.IDLoai = IDLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public LoaiSanPham(String IDLoai, String tenLoai) {
        this.IDLoai = IDLoai;
        TenLoai = tenLoai;
    }


}
