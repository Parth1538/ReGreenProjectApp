package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kf.regreen.regreenproject.R;

/**
 * Created by admin on 01/01/2018.
 */

public class MaintenanceScreen extends AppCompatActivity {

    TextView txtMsg;
    Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenances_screen);

        txtMsg = (TextView)findViewById(R.id.txtMsg);
        btnOk = (Button)findViewById(R.id.btnOk);
        Intent i = getIntent();
        String msg = i.getStringExtra("msg");
        txtMsg.setText(msg);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
