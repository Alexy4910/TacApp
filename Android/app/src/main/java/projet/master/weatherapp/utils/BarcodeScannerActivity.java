package projet.master.weatherapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class BarcodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    private int CAMERA_FRONTAL_ID = 0;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        List<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.DATA_MATRIX);
        mScannerView.setFormats(barcodeFormats);
        contentFrame.addView(mScannerView);
        Button button = findViewById(R.id.activity_btn_photo);

        button.setOnClickListener(view -> {
            mScannerView.stopCamera();
            if (CAMERA_FRONTAL_ID == 0) {
                CAMERA_FRONTAL_ID = 1;
                mScannerView.startCamera(CAMERA_FRONTAL_ID);
            }else{
                CAMERA_FRONTAL_ID = 0;
                mScannerView.startCamera(CAMERA_FRONTAL_ID);
            }
        });
//
//        Button tagIllisible = findViewById(R.id.activity_btn_tag_illisible_photo);
//        tagIllisible.setOnClickListener(view -> {
//            Intent result = new Intent();
//            result.putExtra("result", ERREUR_TAG);
//            setResult(Activity.RESULT_OK, result);
//            finish();
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera(CAMERA_FRONTAL_ID);          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        String barcodeResult = rawResult.getText();
        Intent result = new Intent();
        result.putExtra("result", barcodeResult);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

}
