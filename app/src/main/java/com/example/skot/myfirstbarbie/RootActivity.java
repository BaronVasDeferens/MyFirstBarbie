package com.example.skot.myfirstbarbie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {

        }

        MediaWrangler.initialize(this);

        Intent intent = new Intent(this, ShipSelectActivity.class);
        startActivity(intent);
        finishActivity(0);
    }
}
