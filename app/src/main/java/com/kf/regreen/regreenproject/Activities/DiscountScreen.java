package com.kf.regreen.regreenproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kf.regreen.regreenproject.QRCode.ScanActivity;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;

import static com.kf.regreen.regreenproject.Utils.Constant.TABPOSITION;

/**
 * Created by admin on 30/12/2017.
 */

public class DiscountScreen extends AppCompatActivity {

    RelativeLayout rl_redeem;
    TextView txtSkip;
    private PreferencesUtils mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_bonus_screen);

        mPreferences = new PreferencesUtils(DiscountScreen.this);

        rl_redeem = (RelativeLayout) findViewById(R.id.btnRedeemContainer);
        txtSkip = (TextView) findViewById(R.id.txtSkip);
        rl_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DiscountScreen.this, ScanActivity.class));
                Intent intent = new Intent(DiscountScreen.this, ScanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.setPrefDiscSkiped("true");
                Intent intent = new Intent(DiscountScreen.this, HomeScreenActivity.class);
                intent.putExtra(TABPOSITION, 2);
                intent.putExtra("type", 0);
                startActivity(intent);
                finish();
            }
        });
    }
}
