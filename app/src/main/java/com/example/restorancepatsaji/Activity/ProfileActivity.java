package com.example.restorancepatsaji.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.restorancepatsaji.R;

public class ProfileActivity extends AppCompatActivity {

    ListView listView;
    String usernamee, email;
    Button logout;
    String[] values = new String[]{
            "Username", "Email", "Change Password"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout = findViewById(R.id.logout_btn);
        listView = findViewById(R.id.listview);
        usernamee = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Logging out")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, values);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent = new Intent(ProfileActivity.this, ChangeUsernameActivity.class);
                    intent.putExtra("username", usernamee);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }if(i == 1){
                    Intent intent = new Intent(ProfileActivity.this, ChangeEmailActivity.class);
                    intent.putExtra("username", usernamee);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }if(i == 2){
                    Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("username", usernamee);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(ProfileActivity.this, MemberActivityHome.class);
                intent.putExtra("email", email);
                intent.putExtra("username", usernamee);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}