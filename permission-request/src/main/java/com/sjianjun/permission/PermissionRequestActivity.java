package com.sjianjun.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class PermissionRequestActivity extends Activity {
    private static final String REQUEST_CODE = "REQUEST_CODE";
    private static final SparseArray<PermissionRequest> runnables = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invasionStatusBar(this);
        overridePendingTransition(0, 0);
        int extra = getIntent().getIntExtra(REQUEST_CODE, 0);
        if (extra == 0) {
            finish();
        } else {
            PermissionRequest runnable = runnables.get(extra);
            if (runnable == null) {
                finish();
            } else {
                runnables.remove(extra);
                runnable.run(this);
            }
        }
    }

    private static void invasionStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void startActivity(Context context, int requestCode,PermissionRequest request) {
        Log.e("logger", "startActivity");
        PermissionRequestActivity.runnables.append(requestCode,request);
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(REQUEST_CODE, requestCode);
        context.startActivity(intent);
    }
    public interface PermissionRequest {
        void run(PermissionRequestActivity activity);
    }
}
