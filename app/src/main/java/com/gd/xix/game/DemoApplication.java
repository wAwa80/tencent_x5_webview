package com.gd.xix.game;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.gd.xix.game.feature.X5WebViewActivity;
import com.gd.xix.game.utils.X5CorePreLoadService;

public class DemoApplication extends Application {

    private static final String TAG = "DemoApplication";

    public static boolean hasStartedGame = false; // 标志位
    public static final int OPEN_X5_WEBVIEW = 1; // 定义一个消息标识

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == OPEN_X5_WEBVIEW) {
                // 处理打开 X5 WebView 的逻辑
                if (hasStartedGame) {
                    return true;
                }
                hasStartedGame = true; // 设置标志位

                Intent intent1 = new Intent(getApplicationContext(), X5WebViewActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                return true;
            }
            return false;
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();

        X5CorePreLoadService.setHandler(handler);
        preInitX5Core();
    }

    /**
     * 初始化X5内核
     */
    private void preInitX5Core() {
        //预加载x5内核
        Intent intent = new Intent(this, X5CorePreLoadService.class);
        startService(intent);
    }

}
