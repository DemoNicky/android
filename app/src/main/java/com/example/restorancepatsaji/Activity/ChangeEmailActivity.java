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
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.restorancepatsaji.R;

import java.util.HashMap;
import java.util.Map;

public class ChangeEmailActivity extends AppCompatActivity {

    TextView newemail;
    Button btn_chnge;
    String username, oldemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        newemail = findViewById(R.id.changeemail);
        oldemail = getIntent().getStringExtra("email");
        newemail.setText(oldemail);
        btn_chnge = findViewById(R.id.changeemail_btn);
        Toolbar toolbar = findViewById(R.id.toolbaruemail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = getIntent().getStringExtra("username");
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
                Intent intent = new Intent(ChangeEmailActivity.this, ProfileActivity.class);
                intent.putExtra("email", oldemail);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void confChng() {
        String url = "http://10.0.2.2:8080/api/v1/changeemail";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ChangeEmailActivity.this, "Berhasil Mengganti Email", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChangeEmailActivity.this, MemberActivityHome.class);
                intent.putExtra("email", newemail.getText().toString());
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeEmailActivity.this, "Gagal Mengganti Email", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("oldemail", oldemail);
                param.put("newemail", newemail.getText().toString());
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

            }
        });
        requestQueue.add(stringRequest);
    }
}