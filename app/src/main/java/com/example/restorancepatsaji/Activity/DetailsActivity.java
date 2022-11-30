package com.example.restorancepatsaji.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restorancepatsaji.Model.NewsHeadline;
import com.example.restorancepatsaji.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadline headline;
    TextView txt_title, txt_author, txt_time, txt_detail, txt_content;
    ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);

        headline = (NewsHeadline) getIntent().getSerializableExtra("data");

        txt_title.setText(headline.getTitle());
        txt_author.setText("By : " + headline.getAuthor());
        txt_time.setText(headline.getPublishedAt());
        txt_detail.setText(headline.getDescription());
        txt_content.setText(headline.getContent());
        Picasso.get().load(headline.getUrlToImage()).into(img_news);

        Toolbar toolbar = findViewById(R.id.toooool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}