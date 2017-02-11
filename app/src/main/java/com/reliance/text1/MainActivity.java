package com.reliance.text1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final VerifyView viewById = (VerifyView) findViewById(R.id.vv_view);
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewById.setVerifyTextContent("text1");
            }
        });
    }
}
