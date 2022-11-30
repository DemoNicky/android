package com.example.restorancepatsaji.Interface;

import com.example.restorancepatsaji.Model.LoginRequest;
import com.example.restorancepatsaji.Model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("v1/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("v1/register")
    Call<ResponseBody> registerUser(@Query("username") String username,
                                       @Query("email") String email,
                                       @Query("password") String password);

    @PUT("v1/changeusername")
    Call<ResponseBody> changeUsername(@Query("email")String email,
                                      @Query("username")String username);

    @PUT("v1/changeemail")
    Call<ResponseBody> changeEmail(@Query("oldemail")String email,
                                      @Query("newemail")String username);
}
