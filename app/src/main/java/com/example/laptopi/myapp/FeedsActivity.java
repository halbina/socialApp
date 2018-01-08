package com.example.laptopi.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;




public class FeedsActivity extends AppCompatActivity{


    TextView textName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_layout);



        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textName.setText(SharedPrefManager.getInstance(this).getUsername());




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
            SharedPrefManager.getInstance(this).Logout();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            break;

            case R.id.add_photo:

                startActivity(new Intent(this, PostActivity.class));
                break;

        }
        return true;

    }


}