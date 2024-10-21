package com.gd.xix.game.feature;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gd.xix.game.R;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.QbSdk;

public class X5WebViewActivity extends BaseWebViewActivity {

    private static final String M_TAG = "X5WebViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.setTAG(M_TAG);
        super.onCreate(savedInstanceState);

        if (isEmulator() || Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            startExternalBrowser();
            return;
        }

        IX5WebViewExtension extension = mWebView.getX5WebViewExtension();
        if (extension != null) {
            Log.e("MyX5WebView", "IX5WebViewExtension 初始化成功");
            extension.setScrollBarFadingEnabled(true);
            startDefinedUrl();
        } else {
            Log.e("MyX5WebView", "IX5WebViewExtension 未初始化");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                startExternalBrowser();
            }else{
                startDefinedUrl();
            }

        }
    }

    @Override
    protected void initWebView() {
        super.initWebView();
//        Toast.makeText(this, mWebView.getIsX5Core() ?
//                "X5内核: " + QbSdk.getTbsVersion(this) : "SDK系统内核" , Toast.LENGTH_SHORT).show();
    }

    private  void startExternalBrowser(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.appUrl)));
        startActivity(browserIntent);
        System.exit(0);
    }

    private void startDefinedUrl() {
//        Intent intent = getIntent();
//        if (intent != null) {
            String url = getResources().getString(R.string.appUrl);//intent.getStringExtra("url");
            if (mWebView != null) {
                mWebView.loadUrl(url);
            }
//        } else {
//            Log.i(M_TAG, "Intent is null");
//        }
    }

    private boolean isEmulator() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String hardware = Build.HARDWARE;
        String fingerprint = Build.FINGERPRINT;
        String brand = Build.BRAND;
        String device = Build.DEVICE;

        return (manufacturer.contains("Genymotion") ||
                manufacturer.equals("unknown") ||
                model.contains("google_sdk") ||
                model.contains("Emulator") ||
                model.contains("Android SDK built for x86") ||
                product.contains("sdk_google") ||
                product.contains("vbox") ||
                hardware.contains("goldfish") ||
                hardware.contains("ranchu") ||
                fingerprint.startsWith("generic") ||
                fingerprint.startsWith("unknown") ||
                brand.startsWith("generic") ||
                device.startsWith("generic") ||
                "google_sdk".equals(product));
    }

}
