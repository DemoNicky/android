package com.example.restorancepatsaji.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restorancepatsaji.Config.ApiClient;
import com.example.restorancepatsaji.Helpers.StringHelper;
import com.example.restorancepatsaji.Model.LoginRequest;
import com.example.restorancepatsaji.Model.LoginResponse;
import com.example.restorancepatsaji.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button signin_btn;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        TextView createAcc = (TextView) findViewById(R.id.createacc);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signin_btn = findViewById(R.id.signin_btn);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(username.getText().toString());
                loginRequest.setPassword(password.getText().toString());
                loginUser(loginRequest);
            }
        });
    }

    public void loginUser(LoginRequest loginRequest){
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                        LoginResponse loginResponse = response.body();

                        Intent intent2 = new Intent(MainActivity.this, MemberActivityHome.class);
                        intent2.putExtra("email", loginResponse.getEmail());
                        intent2.putExtra("username", loginResponse.getUsername());
                        startActivity(intent2);
                        finish();
                }else {
                    Toast.makeText(MainActivity.this, "Username or Password is Wrong", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Kesalahan dalam login", Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }


    //ini yang volley udah jadi
//    public void authUser() {
//        if (!validateEmail() || !validatePassword()) {
//            return;
//        }
//
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
////
////            Context context = null;
////            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
////
////            for (Network net : connectivityManager.getAllNetworks()) {
////
////                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(net);
////
////                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
////                    connectivityManager.bindProcessToNetwork(net);
////                    break;
////                }
////            }
////        }
//
//        String eemail = username.getText().toString().trim();
//        String pass = password.getText().toString().trim();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        String url = "http://192.168.100.2:8080/api/v1/login";
//
////        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
////        progressDialog.setMessage("Please Wait");
////        progressDialog.show();
//
//        HashMap<String, String> param = new HashMap<>();
//        param.put("email", username.getText().toString().trim());
//        param.put("password", password.getText().toString().trim());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(param)
//                , new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String role = (String) response.get("role");
//
//                    if (role.trim().equals("ADMIN")){
//                        String username = (String) response.get("username");
//                        String emailll = (String) response.get("email");
//                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                        intent.putExtra("username", username);
//                        intent.putExtra("email", emailll);
//                        startActivity(intent);
//                        finish();
//                    }else if (role.trim().equals("MEMBER")){
//                        String username = (String) response.get("username");
//                        String emailll = (String) response.get("email");
//                        Intent intent2 = new Intent(MainActivity.this, MemberActivityHome.class);
//                        intent2.putExtra("username", username);
//                        intent2.putExtra("email", emailll);
//                        startActivity(intent2);
//                        finish();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, "invalid username or password", Toast.LENGTH_LONG).show();
//                    return;
//            }
//        });
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
//        requestQueue.add(jsonObjectRequest);
//    }

    public static boolean isConnected(Context context){
        NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo == null) {
            return false;
        }
        else{
            //Check if only wifi is selected or wifi==1  Constant Value: 1 (0x00000001)
            if(((localNetworkInfo.getType() == 1)) || (localNetworkInfo.isConnected()) || (localNetworkInfo.isAvailable()))
                return true;
        }
        return false;
    }

    public boolean validateEmail(){
        String emaill = username.getText().toString();

        if (emaill.isEmpty()){
            username.setError("Email masih Kosong");
            return false;
        }else if (!StringHelper.regexEmailVeriv(emaill)){
            username.setError("Mohon masukan email yang Valid");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String pass = password.getText().toString();

        if (pass.isEmpty()){
            password.setError("Password masih Kosong");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    public void signUp(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}