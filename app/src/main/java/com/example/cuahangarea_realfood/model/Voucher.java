package com.example.cuahangarea_realfood.model;

import java.util.Date;

public class Voucher {
    String idSanPham,idMaGiamGia,maGiamGia;
    Double giaGiam,phanTramGiam;
    Date ngayTao,hanSuDung;

    public Voucher( String idMaGiamGia,String idSanPham, String maGiamGia, Double giaGiam, Double phanTramGiam, Date ngayTao, Date hanSuDung) {
        this.idSanPham = idSanPham;
        this.idMaGiamGia = idMaGiamGia;
        this.maGiamGia = maGiamGia;
        this.giaGiam = giaGiam;
        this.phanTramGiam = phanTramGiam;
        this.ngayTao = ngayTao;
        this.hanSuDung = hanSuDung;
    }

    public Voucher() {
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdMaGiamGia() {
        return idMaGiamGia;
    }

    public void setIdMaGiamGia(String idMaGiamGia) {
        this.idMaGiamGia = idMaGiamGia;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
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
