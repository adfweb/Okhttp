package com.example.handingreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView logintxt, registxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logintxt = (TextView) findViewById(R.id.txtlogin);
        registxt = (TextView) findViewById(R.id.txtregis);

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(log);
            }
        });

        registxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regs = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(regs);
            }
        });



    }
}
