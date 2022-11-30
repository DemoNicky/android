package com.example.restorancepatsaji.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restorancepatsaji.Interface.BeritaInterface;
import com.example.restorancepatsaji.Model.Berita;
import com.example.restorancepatsaji.R;
import com.example.restorancepatsaji.Util.RealPathUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//implements AdapterView.OnItemSelectedListener
public class AddBeritaPage extends AppCompatActivity {

    private Spinner spinner;

    Button btnChose, btnUpload;

    EditText judul, isiBerita;

    String email;

    String eml = "";

    ImageView imageUpload;

    SwitchCompat switchCompat;

    String swtch = "";

    private Uri filePath;

    String[] category = {"Select Category", "Bola", "Politik"};

    String cate = "";

    String path;

    BeritaInterface beritaInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_berita_page);

        spinner = findViewById(R.id.spinner_cate);
        judul = findViewById(R.id.judul_berita);
        isiBerita = findViewById(R.id.isiberita);
        switchCompat = findViewById(R.id.btn_trend);
        btnChose = findViewById(R.id.upload_btn);
        btnUpload = findViewById(R.id.post_btn);
        imageUpload = findViewById(R.id.image_upld);
        email = getIntent().getStringExtra("email");
        eml = email;


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate = category[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    swtch = "benar";
                }
            }
        });

        clickListener();
    }

    private void clickListener() {
        btnChose.setOnClickListener(v ->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            }else {
                ActivityCompat.requestPermissions(AddBeritaPage.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            }
        });

        btnUpload.setOnClickListener(v -> {
            if (!emptyField() || !emptyFieldIsi()){
                return;
            }else {

            }
            createRes(email, judul.getText().toString(), isiBerita.getText().toString(), swtch, cate);
        });
    }

    private void createRes(String email, String judul, String isi, String trending, String category) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody brt_email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody brt_judul = RequestBody.create(MediaType.parse("multipart/form-data"),judul);
        RequestBody brt_isi = RequestBody.create(MediaType.parse("multipart/form-data"),isi);
        RequestBody brt_trend = RequestBody.create(MediaType.parse("multipart/form-data"),trending);
        RequestBody brt_category = RequestBody.create(MediaType.parse("multipart/form-data"),category);

        BeritaInterface beritaInterface = retrofit.create(BeritaInterface.class);
        Call<Berita> call = beritaInterface.addBerita(brt_email, brt_judul, brt_isi, brt_trend, brt_category, body);
        call.enqueue(new Callback<Berita>() {
            @Override
            public void onResponse(Call<Berita> call, Response<Berita> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Berita Ditambahkan", Toast.LENGTH_LONG).show();
                }else {
                    System.out.println(response.toString());
                    Toast.makeText(getApplicationContext(), "Berita Gagal Ditambahkan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Berita> call, Throwable t) {
                AddBeritaPage.this.judul.setText(null);
                AddBeritaPage.this.isiBerita.setText(null);
                AddBeritaPage.this.imageUpload.setImageBitmap(null);
                AddBeritaPage.this.switchCompat.setOnCheckedChangeListener(null);
                AddBeritaPage.this.spinner.setOnItemSelectedListener(null);
                Toast.makeText(getApplicationContext(), "Berita Ditambahkan", Toast.LENGTH_LONG).show();
                System.out.println(t.fillInStackTrace());
                Intent intent = new Intent(AddBeritaPage.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = AddBeritaPage.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageUpload.setImageBitmap(bitmap);

        }
    }

    public boolean emptyField(){
        String fld = judul.getText().toString();

        if (fld.isEmpty()){
            judul.setError("Judul Berita masih Kosong");
            return false;
        }else {
            judul.setError(null);
            return true;
        }
    }

    public boolean emptyFieldIsi(){
        String isi = isiBerita.getText().toString();

        if (isi.isEmpty()){
            isiBerita.setError("Isi Berita masih Kosong");
            return false;
        }else {
            isiBerita.setError(null);
            return true;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (requestCode == CODE_GALLERY_REQUEST) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
//            }else {
//                Toast.makeText(getApplicationContext(), "Aplikasi tidak di izinkan mengambil gambar", Toast.LENGTH_LONG).show();
//
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        cate = category[i];
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}