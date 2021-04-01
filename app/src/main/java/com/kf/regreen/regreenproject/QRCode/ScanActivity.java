package com.kf.regreen.regreenproject.QRCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.kf.regreen.regreenproject.Activities.CongratulationsActivity;
import com.kf.regreen.regreenproject.Activities.HomeScreenActivity;
import com.kf.regreen.regreenproject.R;
import com.kf.regreen.regreenproject.Utils.PreferencesUtils;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
    //    BarcodeDetector barcodeDetector;
    TextView barcodeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        // get the barcode reader instance
        barcodeReader = (BarcodeReader) this.getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();
        PreferencesUtils mPreferences = new PreferencesUtils(ScanActivity.this);
        mPreferences.setPrefDiscSkiped("false");

        // ticket details activity by passing barcode
        Intent intent = new Intent(ScanActivity.this, CongratulationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);
        finish();

    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  Intent intent = new Intent(ScanActivity.this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("activity", "Congratulations");
        startActivity(intent);
        finish();*/
    }

    /*@Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
        barcodeInfo = new TextView(ScanActivity.this);
        if (barcodes.size() != 0) {
//                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
//                        public void run() {
//                            barcodeInfo.setText(    // Update the TextView
//                                    barcodes.valueAt(0).displayValue
//                            );
//                        }
//                    });
            Toast.makeText(ScanActivity.this,barcodes.valueAt(0).displayValue,Toast.LENGTH_SHORT).show();
        }
        barcodeReader.playBeep();
    }*/
}
