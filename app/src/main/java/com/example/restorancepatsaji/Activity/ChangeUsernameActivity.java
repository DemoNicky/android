package com.example.restorancepatsaji.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.restorancepatsaji.Config.ApiClient;
import com.example.restorancepatsaji.Model.ChangeUsernameRequest;
import com.example.restorancepatsaji.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUsernameActivity extends AppCompatActivity {
    TextView username;
    Button btn_chnge;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        username = findViewById(R.id.changeusername);
        btn_chnge = findViewById(R.id.changeuser_btn);
        Toolbar toolbar = findViewById(R.id.toolbaruser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String usernameee = getIntent().getStringExtra("username");
        username.setText(usernameee);
        email = getIntent().getStringExtra("email");
        btn_chnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confChng(email, username.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(ChangeUsernameActivity.this, ProfileActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void confChng(String email, String username) {

        Call<ResponseBody> responseBodyCall = ApiClient.getService().changeUsername(email, username);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangeUsernameActivity.this, "Perubahan Sukses", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangeUsernameActivity.this, MemberActivityHome.class);
                    intent.putExtra("email", email);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ChangeUsernameActivity.this, "Kesalahan Saat Merubah Username", Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangeUsernameActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });
    }

//    private void confChng() {
//        String url = "http://192.168.100.2:8080/api/v1/changeusername";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(ChangeUsernameActivity.this, "Berhasil Mengganti Username", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ChangeUsernameActivity.this, MemberActivityHome.class);
//                String user = username.getText().toString();
//                intent.putExtra("email", email);
//                intent.putExtra("username", user);
//                startActivity(intent);
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ChangeUsernameActivity.this, "Gagal Mengganti Username", Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("username", username.getText().toString());
//                param.put("email", email);
//                return param;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
}