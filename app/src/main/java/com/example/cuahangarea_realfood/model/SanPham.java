package com.example.cuahangarea_realfood.model;

import java.util.ArrayList;

public class SanPham {
    String IDSanPham, TenSanPham, IDLoai, IDDanhMuc, Gia, ChiTietSanPham, IDCuaHang;
    Float Rating;

    public ArrayList<String> getImage() {
        return images;
    }

    public void setImage( ArrayList<String> images) {
        this.images = images;
    }

    ArrayList<String> images;

    public SanPham(String IDSanPham, String tenSanPham, String IDLoai, String IDDanhMuc, String gia, String chiTietSanPham, String IDCuaHang, Float rating,  ArrayList<String> images) {
        this.IDSanPham = IDSanPham;
        TenSanPham = tenSanPham;
        this.IDLoai = IDLoai;
        this.IDDanhMuc = IDDanhMuc;
        Gia = gia;
        ChiTietSanPham = chiTietSanPham;
        this.IDCuaHang = IDCuaHang;
        Rating = rating;
        this.images = images;
    }

    public SanPham( ) {
    }

    public String getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(String IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getIDLoai() {
        return IDLoai;
    }

    public void setIDLoai(String IDLoai) {
        this.IDLoai = IDLoai;
    }

    public String getIDDanhMuc() {
        return IDDanhMuc;
    }

    public void setIDDanhMuc(String IDDanhMuc) {
        this.IDDanhMuc = IDDanhMuc;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getChiTietSanPham() {
        return ChiTietSanPham;
    }

    public void setChiTietSanPham(String chiTietSanPham) {
        ChiTietSanPham = chiTietSanPham;
    }

    public String getIDCuaHang() {
        return IDCuaHang;
    }

    public void setIDCuaHang(String IDCuaHang) {
        this.IDCuaHang = IDCuaHang;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }
}
