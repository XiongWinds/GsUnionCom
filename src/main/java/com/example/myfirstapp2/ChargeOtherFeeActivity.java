package com.example.myfirstapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChargeOtherFeeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_other_fee);

        Button button = findViewById(R.id.sureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeSuccess();
                Log.v(this.toString(),"success!!!");
            }
        });

    }

    private void chargeSuccess(){
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        startActivity(intent);
    }
}
