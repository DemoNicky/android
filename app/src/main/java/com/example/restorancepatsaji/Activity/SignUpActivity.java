package com.example.restorancepatsaji.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.restorancepatsaji.Config.ApiClient;
import com.example.restorancepatsaji.Helpers.StringHelper;
import com.example.restorancepatsaji.Model.RegisterRequest;
import com.example.restorancepatsaji.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText username, email, password, confirm;
    Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.user_regis);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);

        regis = findViewById(R.id.register_btn);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateusername() || !validateEmail() || !validatePasswordAndConfirm()){
                    return;
                }
                registerUser(username.getText().toString(), email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerUser(String usernmae, String email, String password){

        Call<ResponseBody> responseBodyCall = ApiClient.getService().registerUser(usernmae, email, password);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Registrasi Sukses", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SignUpActivity.this, "Kesalahan Saat Registrasi", Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
            }
        });
    }


    //ini yang volley
//    public void processFormField(){
//        if (!validateusername() || !validateEmail() || !validatePasswordAndConfirm()){
//            return;
//        }
//
//        String url = "http://192.168.100.2:8080/api/v1/register";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                username.setText(null);
//                email.setText(null);
//                password.setText(null);
//                confirm.setText(null);
//                Toast.makeText(SignUpActivity.this, "Registrasi Sukses", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SignUpActivity.this, "Fail Regis", Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//
//                param.put("username", username.getText().toString());
//                param.put("email", email.getText().toString());
//                param.put("password", password.getText().toString());
//                return param;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
////        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        requestQueue.add(stringRequest);
//    }

    public void goToHome(View view){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validateusername(){
        String usr = username.getText().toString();

        if (usr.isEmpty()){
            username.setError("Username masih Kosong");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }


    public boolean validateEmail(){
        String emaill = email.getText().toString();

        if (emaill.isEmpty()){
            email.setError("Email masih Kosong");
            return false;
        }else if (!StringHelper.regexEmailVeriv(emaill)){
            email.setError("Mohon masukan email yang Valid");
            return false;
        }else {
            email.setError(null);
            return true;
        }
    }

    public boolean validatePasswordAndConfirm(){
        String pass = password.getText().toString();
        String conff = confirm.getText().toString();

        if (pass.isEmpty() || conff.isEmpty()){
            password.setError("Password masih Kosong");
            confirm.setError("Konfirmasi password masih Kosong");
            return false;
        }else if (!conff.equals(pass)){
            confirm.setError("Konfirmasi Tidak Valid");
            return false;
        }else {
            password.setError(null);
            confirm.setError(null);
            return true;
        }
    }
}