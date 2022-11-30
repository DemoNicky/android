package com.example.restorancepatsaji.Interface;
import com.example.restorancepatsaji.Model.Berita;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BeritaInterface {

    @Multipart
    @POST("v1/berita/insert")
    Call<Berita> addBerita(@Part("email")RequestBody email,
                           @Part("judul")RequestBody judul,
                           @Part("isiberita") RequestBody isiberita,
                           @Part("trending") RequestBody trending,
                           @Part("category") RequestBody category,
                           @Part MultipartBody.Part image);

}
