package com.example.restorancepatsaji.Activity;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restorancepatsaji.Config.RequestManager;
import com.example.restorancepatsaji.Listener.OnFetchDataListenenr;
import com.example.restorancepatsaji.Listener.SelectListener;
import com.example.restorancepatsaji.Model.LoginResponse;
import com.example.restorancepatsaji.Model.NewsApiResponse;
import com.example.restorancepatsaji.Model.NewsHeadline;
import com.example.restorancepatsaji.R;
import com.example.restorancepatsaji.Rest.CustomAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MemberActivityHome extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7, x = null;
    Boolean sudah = false;
    SearchView searchView;
    ImageView profile;
    TextView username;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        searchView = findViewById(R.id.search_view);
        profile = findViewById(R.id.profile_ic);
        username = findViewById(R.id.usernama);
        String usernameeee = getIntent().getStringExtra("username");
        username.setText(usernameeee);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Memuat Berita");
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        if (sudah.equals(false)){
            b1.setTextColor(Color.WHITE);
            b1.setTextSize(20);
        }
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadline(listenenr, "general", null);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberActivityHome.this, ProfileActivity.class);
                String usernamee = getIntent().getStringExtra("username");
                String emailll = getIntent().getStringExtra("email");
                intent.putExtra("username", usernamee);
                intent.putExtra("email", emailll);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetcing news Articles of " + query);
                dialog.show();
                RequestManager requestManager = new RequestManager(MemberActivityHome.this);
                String cat = "";
                if (x == null){
                    cat = "general";
                }else if(x.getText().toString().equals("home")){
                    cat = "general";
                }else {
                    cat = x.getText().toString();
                }
                requestManager.getNewsHeadline(listenenr, cat, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListenenr<NewsApiResponse> listenenr = new OnFetchDataListenenr<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadline> list, String message) {
            if (list.isEmpty()){
                dialog.dismiss();
                Toast.makeText(MemberActivityHome.this, "Tidak Menemukan Berita yang cocok", Toast.LENGTH_SHORT).show();
            }else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MemberActivityHome.this, "Error", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadline> list) {
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void OnNewsClicked(NewsHeadline newsHeadline) {
        startActivity(new Intent(MemberActivityHome.this, DetailsActivity.class)
                .putExtra("data", newsHeadline));
    }

    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        sudah = true;
        if (sudah.equals(true)) {
            b1.setTextColor(getResources().getColor(R.color.whitedark));
            b1.setTextSize(15);
        }
            if (!(x == null)){
                x.setTextColor(getResources().getColor(R.color.whitedark));
//            x.setPaintFlags(0);
            x.setTextSize(15);
        }

        String category = button.getText().toString();
        button.setTextColor(Color.WHITE);
        button.setTextSize(20);
//        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        x = button;

        dialog.setTitle("Fetching news Articles of " + category);
        dialog.show();
        if (category.equals("home")){
            category = "general";
        }
        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadline(listenenr, category, null);
    }
}