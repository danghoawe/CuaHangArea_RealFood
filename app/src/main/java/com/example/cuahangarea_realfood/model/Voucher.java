package com.example.cuahangarea_realfood.model;

import java.util.Date;

public class Voucher {
    String maGiamGia,idSanPham;
    Double giaGiam,phanTramGiam;
    Date ngayTao,hanSuDung;

    public Voucher() {
    }

    public Voucher(String maGiamGia, String idSanPham, Double giaGiam, Double phanTramGiam, Date hanSuDung) {
        this.maGiamGia = maGiamGia;
        this.idSanPham = idSanPham;
        this.giaGiam = giaGiam;
        this.phanTramGiam = phanTramGiam;
        this.ngayTao = new Date();
        this.hanSuDung = hanSuDung;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public Double getGiaGiam() {
        return giaGiam;
    }

    public void setGiaGiam(Double giaGiam) {
        this.giaGiam = giaGiam;
    }

    public Double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(Double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
}
