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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.restorancepatsaji.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newpassword, confpassword, oldpassword;
    String email, username;
    Button btn_chnge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldpassword = findViewById(R.id.oldPassword);
        newpassword = findViewById(R.id.newPassword);
        confpassword = findViewById(R.id.confpassword);
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        btn_chnge = findViewById(R.id.changepass_btn);
        Toolbar toolbar = findViewById(R.id.toolbarupassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_chnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confChng();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void confChng() {
        if (!validatePasswordAndConfirm()){
            return;
        }

        String url = "http://10.0.2.2:8080/api/v1/changepass";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                JSONObject obj = new JSONObject(response);
                Toast.makeText(ChangePasswordActivity.this, "Berhasil Mengganti Password", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChangePasswordActivity.this, MemberActivityHome.class);
                intent.putExtra("email", email);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePasswordActivity.this, "Gagal Mengganti Password", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("oldpassword", oldpassword.getText().toString());
                param.put("newpassword", newpassword.getText().toString());
                param.put("email", email);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(ChangePasswordActivity.this, "Password Salah", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public boolean validatePasswordAndConfirm(){
        String pass = newpassword.getText().toString();
        String conff = confpassword.getText().toString();

        if (pass.isEmpty() || conff.isEmpty()){
            newpassword.setError("Password masih Kosong");
            confpassword.setError("Konfirmasi password masih Kosong");
            return false;
        }else if (!conff.equals(pass)){
            confpassword.setError("Konfirmasi Tidak Valid");
            return false;
        }else {
            newpassword.setError(null);
            confpassword.setError(null);
            return true;
        }
    }
}