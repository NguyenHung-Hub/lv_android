package com.example.demolayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.security.ConfirmationNotAvailableException;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private static final int SELECT_IMG_CODE = 200;

    private ImageView iv_preview;
    private FileOutputStream outputStreamImg;


    private ArrayList<NhanVien> nv_list = new ArrayList<>();

    private String[] dv_list;
    private String donVi;

    private NhanVienListView nhanVienListView;
    private ListView lv_nhanVien;
    private NhanVien nhanVienSelected;
    private Bitmap bm_avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_preview = findViewById(R.id.iv_preview);


        EditText et_maSo = findViewById(R.id.et_maSo);
        EditText et_hoTen = findViewById(R.id.et_hoTen);

        lv_nhanVien = findViewById(R.id.lv_nhanVien);
        RadioGroup rg_gioiTinh = findViewById(R.id.rg_gioiTinh);
        RadioButton rb_nam = findViewById(R.id.rb_nam);
        RadioButton rb_nu = findViewById(R.id.rb_nu);

        Spinner sp_donVi = findViewById(R.id.sp_donVi);

//        nv_list.add(new NhanVien(110, "Nguyen Hung", "Nam", "Phòng tiếp thị", "/storage/emulated/0/Android/data/com.example.demolayout/files/MyDir/img/1647771044300.jpg"));
//        nv_list.add(new NhanVien(111, "Tran Hung", "Nam", "Phòng kĩ thuật", "/storage/emulated/0/Android/data/com.example.demolayout/files/MyDir/img/1647771044300.jpg"));
//        nv_list.add(new NhanVien(112, "Le Thanh", "Nữ", "Phòng kế toán", "/storage/emulated/0/Android/data/com.example.demolayout/files/MyDir/img/1647771044300.jpg"));
//        nv_list.add(new NhanVien(113, "Pham Thi Thuy", "Nữ", "Phòng kế toán", "/storage/emulated/0/Android/data/com.example.demolayout/files/MyDir/img/1647771044300.jpg"));
//        nv_list.add(new NhanVien(114, "Le Trinh", "Nữ", "Phòng kế toán", "/storage/emulated/0/Android/data/com.example.demolayout/files/MyDir/img/1647771044300.jpg"));

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

        Button btnImg = findViewById(R.id.btn_chooseImg);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMG_CODE);


            }


        });

        Button btnThem = findViewById(R.id.btnThem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSo = Integer.parseInt(et_maSo.getText().toString());
                String hoTen = et_hoTen.getText().toString();
                String gioiTinh = ((RadioButton) findViewById(rg_gioiTinh.getCheckedRadioButtonId())).getText().toString();

                BitmapDrawable drawable = (BitmapDrawable) iv_preview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                String avatar = BitMapToString(bitmap);
                NhanVien nv = new NhanVien(maSo, hoTen, gioiTinh, donVi, avatar);


                nv_list.add(nv);

                lv_nhanVien.setAdapter(nhanVienListView);
            }
        });


        Button btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFileExternalStorage();
            }
        });

        Button btnWrite = findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("show:   " + getFilesDir());
                writeFileExternalStorage();

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


                if (nhanVienSelected != null) {
                    for (int i = 0; i < nv_list.size(); i++) {
                        if (nv_list.get(i).equals(nhanVienSelected)) {
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
                if (nhanVienSelected != null) {
                    for (int i = 0; i < nv_list.size(); i++) {
                        if (nv_list.get(i).equals(nhanVienSelected)) {
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

                iv_preview.setImageURI(null);

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


    public void writeFileExternalStorage() {


        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {

            return;
        }
        File file = new File(getExternalFilesDir("MyDir"), "demo2.txt");

        FileOutputStream outputStream = null;

        try {


            outputStream = new FileOutputStream(file, false);

            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

            objectOutput.writeObject(nv_list);
            System.out.println("lưu " + nv_list.size() + " nhân viên");
            objectOutput.flush();
            objectOutput.close();

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveImg();
            } else {
                askPermission();
            }

            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFileExternalStorage() {
        try {
            FileInputStream inputStream = new FileInputStream(getExternalFilesDir("MyDir/demo2.txt"));
            ObjectInputStream objectIS = new ObjectInputStream(inputStream);

            nv_list = null;
            nv_list = (ArrayList<NhanVien>) objectIS.readObject();
            System.out.println("load " + nv_list.size() + " nhân viên");
            nhanVienListView = new NhanVienListView(nv_list);
            lv_nhanVien.setAdapter(nhanVienListView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMG_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = Objects.requireNonNull(data).getData();
                iv_preview.setImageURI(uri);
                File pathTemp = new File(uri.getPath());

                System.out.println("pathTemp: " + pathTemp.getPath());
            } else {
                Toast.makeText(MainActivity.this, "Lỗi ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImg();

            } else {
                Toast.makeText(MainActivity.this, "Chưa cấp quyền!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    Nếu lưu img vào file thì k cần lưu riêng ra folder
    private void saveImg() {
        File dir = new File(getExternalFilesDir("MyDir"), "img");
        if (!dir.exists()) {
            dir.mkdir();
        }

        BitmapDrawable drawable = (BitmapDrawable) iv_preview.getDrawable();

        Bitmap bitmap = drawable.getBitmap();
        File img = new File(dir, System.currentTimeMillis() + ".jpg");

        try {
            outputStreamImg = new FileOutputStream(img);
            System.out.println("path:" + img.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamImg);

        Toast.makeText(MainActivity.this, "Luu anh thanh cong", Toast.LENGTH_SHORT).show();
        try {
            outputStreamImg.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStreamImg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}