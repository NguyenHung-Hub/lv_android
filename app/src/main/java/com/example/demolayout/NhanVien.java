package com.example.demolayout;

import android.graphics.Bitmap;

import java.io.Serializable;

public class NhanVien implements Serializable {

    private static final long serialVersionUID = -6982331873461028582L;
    private int maSo;
    private String hoTen;
    private String gioiTinh;
    private String donVi;
    //    private String pathAvatar;
    private String avatar;


    public NhanVien(int maSo, String hoTen, String gioiTinh, String donVi, String avatar) {
        this.maSo = maSo;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.donVi = donVi;
        this.avatar = avatar;

    }

    public int getMaSo() {
        return maSo;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setMaSo(int maSo) {
        this.maSo = maSo;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maSo=" + maSo +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", donVi='" + donVi + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
