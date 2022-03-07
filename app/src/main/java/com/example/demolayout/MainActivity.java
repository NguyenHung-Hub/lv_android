package com.example.demolayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.security.ConfirmationNotAvailableException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NhanVien> nv_list = new ArrayList<>();

    private String[] dv_list;
    private String donVi;

    private NhanVienListView nhanVienListView;
    private NhanVien nhanVienSelected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        EditText et_maSo = findViewById(R.id.et_maSo);
        EditText et_hoTen = findViewById(R.id.et_hoTen);

        ListView lv_nhanVien = findViewById(R.id.lv_nhanVien);
        RadioGroup rg_gioiTinh = findViewById(R.id.rg_gioiTinh);
        RadioButton rb_nam = findViewById(R.id.rb_nam);
        RadioButton rb_nu = findViewById(R.id.rb_nu);

        Spinner sp_donVi = findViewById(R.id.sp_donVi);

        nv_list.add(new NhanVien(110, "Nguyen Hung", "Nam", "Phòng tiếp thị"));
        nv_list.add(new NhanVien(111, "Tran Hung", "Nam", "Phòng kĩ thuật"));
        nv_list.add(new NhanVien(112, "Le Thanh", "Nữ", "Phòng kế toán"));
        nv_list.add(new NhanVien(113, "Pham Thi Thuy", "Nữ", "Phòng kế toán"));
        nv_list.add(new NhanVien(114, "Le Trinh", "Nữ", "Phòng kế toán"));

        nhanVienListView = new NhanVienListView(nv_list);
        lv_nhanVien.setAdapter(nhanVienListView);



        dv_list = getResources().getStringArray(R.array.don_vi_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dv_list);

        sp_donVi.setAdapter(arrayAdapter);


        sp_donVi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                donVi = dv_list[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnThem = findViewById(R.id.btnThem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSo = Integer.parseInt(et_maSo.getText().toString());
                String hoTen = et_hoTen.getText().toString();
                String gioiTinh = ((RadioButton) findViewById(rg_gioiTinh.getCheckedRadioButtonId())).getText().toString();

                NhanVien nv = new NhanVien(maSo, hoTen, gioiTinh, donVi);

                nv_list.add(nv);

                //đưa ds nv và listView
//                ArrayList<String> listItems = new ArrayList<>();
//                for (NhanVien nv1 :
//                        nv_list) {
//                    listItems.add(nv1.toString());
//                }
//
//                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, listItems);
//                lv_nhanVien.setAdapter(arrayAdapter1);
//                System.out.println(nv);
                lv_nhanVien.setAdapter(nhanVienListView);
            }
        });

        Button btnSua = findViewById(R.id.btnSua);
        btnSua.setEnabled(false);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSo = Integer.parseInt(et_maSo.getText().toString());
                String hoTen = et_hoTen.getText().toString();
                String gioiTinh = ((RadioButton) findViewById(rg_gioiTinh.getCheckedRadioButtonId())).getText().toString();


                if (nhanVienSelected != null){
                    for (int i = 0; i < nv_list.size(); i++) {
                        if (nv_list.get(i).equals(nhanVienSelected)){
                            nv_list.get(i).setMaSo(maSo);
                            nv_list.get(i).setHoTen(hoTen);
                            nv_list.get(i).setGioiTinh(gioiTinh);
                            nv_list.get(i).setDonVi(donVi);

                            lv_nhanVien.setAdapter(nhanVienListView);
                            nhanVienSelected = null;
                            btnSua.setEnabled(false);
                        }
                    }
                }

            }
        });

        Button btnXoa = findViewById(R.id.btnXoa);
        btnXoa.setEnabled(false);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhanVienSelected != null){
                    for (int i = 0; i < nv_list.size(); i++) {
                        if (nv_list.get(i).equals(nhanVienSelected)){
                            nv_list.remove(i);

                            lv_nhanVien.setAdapter(nhanVienListView);
                            nhanVienSelected = null;
                            btnXoa.setEnabled(false);
                        }
                    }
                }
            }
        });

        Button btnLamMoi = findViewById(R.id.btnLamMoi);
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_maSo.setText("");
                et_hoTen.setText("");

                rb_nam.setChecked(true);

                sp_donVi.setSelection(0);
                lv_nhanVien.clearChoices();
                lv_nhanVien.setAdapter(nhanVienListView);
                nhanVienSelected = null;

            }
        });

        Button btnThoat = findViewById(R.id.btnThoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        lv_nhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);


                NhanVien nv = nv_list.get(i);
                nhanVienSelected = nv;

                et_maSo.setText(nv.getMaSo() + "");
                et_hoTen.setText(nv.getHoTen());

                if (nv.getGioiTinh().equals("Nam")) {
                    rb_nam.setChecked(true);
                } else {
                    rb_nu.setChecked(true);
                }


                for (int j = 0; j < dv_list.length; j++) {
                    if (dv_list[j].equals(nv.getDonVi())) {
                        sp_donVi.setSelection(j);
                    }
                }
            }
        });
    }
}