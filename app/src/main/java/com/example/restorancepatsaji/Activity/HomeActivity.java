package com.example.restorancepatsaji.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

//import com.example.restorancepatsaji.Adapter.BeritaAdapter;
import com.example.restorancepatsaji.Config.Config;
import com.example.restorancepatsaji.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    TextView username;
    Toolbar toolbar;
    Button logout_btn, addBerita;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout_btn = findViewById(R.id.signout_btn);
        addBerita = findViewById(R.id.add_berita);
        username = findViewById(R.id.username_appppp);
        String usernameee = getIntent().getStringExtra("username");
        username.setText(usernameee);
        recyclerView = findViewById(R.id.rv_heros);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<List<BeritaGet>> call = apiInterface.getAll();
//        call.enqueue(new Callback<List<BeritaGet>>() {
//            @Override
//            public void onResponse(Call<List<BeritaGet>> call, Response<List<BeritaGet>> response) {
//                List<BeritaGet> beritaGets = response.body();
//                BeritaAdapter beritaAdapter = new BeritaAdapter(HomeActivity.this, beritaGets);
//                recyclerView.setAdapter(beritaAdapter);
//            }
//            @Override
//            public void onFailure(Call<List<BeritaGet>> call, Throwable t) {
//                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });


        addBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBeritaaa();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }


//    private void jsonRequest() {
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONArray jsonArray = response.getJSONArray("beritaa");
//                                JSONArray jsonArray1 = response.getJSONArray("image");
//                                JSONArray jsonArray2 = response.getJSONArray("category");
//                                JSONArray jsonArray3 = response.getJSONArray("appUser");
//
//
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
//                                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
//                                JSONObject jsonObject3 = jsonArray3.getJSONObject(i);
//                                BeritaGet beritaGet = new BeritaGet();
//                                beritaGet.setJudul(jsonObject.getString("judul"));
//                                beritaGet.setWktCreateBerita(jsonObject.getString("wktCreateBerita"));
//                                beritaGet.setIsiBerita(jsonObject.getString("isiBerita"));
//                                beritaGet.setTrending(jsonObject.getString("trending"));
//                                beritaGet.setTrending(String.valueOf(jsonObject.getInt("likeBerita")));
//                                beritaGet.getImage().setId(jsonObject1.getString("id"));
//                                beritaGet.getImage().setNamePho(jsonObject1.getString("namePho"));
//                                beritaGet.getImage().setType(jsonObject1.getString("type"));
//                                beritaGet.getImage().setPict(jsonObject1.getString("pict").getBytes());
//                                beritaGet.getCategory().setId(jsonObject2.getString("id"));
//                                beritaGet.getCategory().setName(jsonObject2.getString("name"));
//                                beritaGet.getAppUser().setId(jsonObject3.getString("id"));
//                                beritaGet.getAppUser().setUsername(jsonObject3.getString("username"));
//                                beritaGet.getAppUser().setEmail(jsonObject3.getString("email"));
//                                beritaGet.getAppUser().setRole(jsonObject3.getString("role"));
//                                beritaGets.add(beritaGet);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        setupRecycleView(beritaGets);
//
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue = Volley.newRequestQueue(HomeActivity.this);
//        requestQueue.add(jsonObjectRequest);
//    }

//    private void setupRecycleView(List<BeritaGet> beritaGets) {
//        CustomAdapter adapter = new CustomAdapter(this, beritaGets);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//    }

    private void signOut(){
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void addBeritaaa(){
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(HomeActivity.this, AddBeritaPage.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}