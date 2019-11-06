package com.sjianjun.permission.util;

import android.app.Activity;
import android.app.FragmentManager;

import com.sjianjun.permission.PermissionCallback;
import com.sjianjun.permission.PermissionFragment;
import com.sjianjun.permission.model.Permission;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by SJJ on 2017/7/30.
 */

public final class PermissionUtil {
    private static final String TAG = "sjj.permission.util.PermissionUtil.TAG";
    private static final AtomicInteger requestCode = new AtomicInteger(123456);

    private PermissionUtil() {
    }

    public static void requestPermissions(Activity activity, String[] permissions, PermissionCallback callback) {
        PermissionFragment fragment = getPermissionsFragment(activity);
        fragment.requestPermissions(permissions,callback,requestCode.getAndIncrement());

    }
    private static PermissionFragment getPermissionsFragment(Activity activity) {
        FragmentManager manager = activity.getFragmentManager();
        PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PermissionFragment();
            manager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
        return fragment;
    }

    public static boolean isGranted(List<Permission> permissions) {
        for (Permission permission : permissions) {
            if (!permission.granted) {
                return false;
            }
        }
        return true;
    }

}
