package com.kf.regreen.regreenproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.kf.regreen.regreenproject.R;

/**
 * Created by admin on 31/12/2017.
 */

public class ComingSoon extends AppCompatActivity {

    RelativeLayout btnFinishContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        btnFinishContainer = (RelativeLayout)findViewById(R.id.btnFinishContainer);
        btnFinishContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
