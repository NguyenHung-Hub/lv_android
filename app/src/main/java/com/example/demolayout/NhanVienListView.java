package com.example.demolayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class NhanVienListView extends BaseAdapter {
    final ArrayList<NhanVien> nhanViens;


    public NhanVienListView(ArrayList<NhanVien> nhanViens) {
        this.nhanViens = nhanViens;
    }


    @Override
    public int getCount() {
        return nhanViens.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanViens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhanViens.get(i).getMaSo();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View nhanVienView;
        if (view == null) {
            nhanVienView = View.inflate(viewGroup.getContext(), R.layout.nhanvien_view, null);
        } else {
            nhanVienView = view;
        }

        //Hien thi du lieu nhan vien
        NhanVien nv = (NhanVien) getItem(i);
        ((TextView) nhanVienView.findViewById(R.id.maSo_nv)).setText("Mã số: " + nv.getMaSo());
        ((TextView) nhanVienView.findViewById(R.id.ten_nv)).setText("Họ tên: " + nv.getHoTen());
        ((TextView) nhanVienView.findViewById(R.id.gioiTinh_nv)).setText("Giới tính: " + nv.getGioiTinh());
        ((TextView) nhanVienView.findViewById(R.id.donVi_nv)).setText("Đơn vị: " + nv.getDonVi());

        Bitmap avatar = StringToBitMap(nv.getAvatar());
        ((ImageView) nhanVienView.findViewById(R.id.nhanVien_img_id)).setImageBitmap(avatar);


        return nhanVienView;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
