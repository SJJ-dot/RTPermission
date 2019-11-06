package com.sjianjun.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.sjianjun.permission.model.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJJ on 2017/7/30.
 */

public class PermissionFragment extends Fragment {
    private final SparseArray<PermissionCallback> callbackMap = new SparseArray<>();
    private final SparseArray<List<Permission>> permissionMap = new SparseArray<>();

    public PermissionFragment() {
        setRetainInstance(true);
    }

    public void requestPermissions(@NonNull String[] permissions, @NonNull PermissionCallback callback, int requestCode) {
        if (!isMarshmallow()) {
            List<Permission> list = new ArrayList<>(permissions.length);
            for (String s : permissions) {
                list.add(new Permission(s, true, false));
            }
            callback.onRequestPermissionsResult(list);
            finishActivity();
        } else {
            List<Permission> list = new ArrayList<>(permissions.length);
            List<String> permission_denied = new ArrayList<>(permissions.length);
            for (String s : permissions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED) {
                    list.add(new Permission(s, true, false));
                } else {
                    permission_denied.add(s);
                }
            }
            if (permission_denied.size() > 0) {
                permissionMap.put(requestCode,list);
                callbackMap.put(requestCode,callback);
                String[] strings = new String[permission_denied.size()];
                requestPermissions(permission_denied.toArray(strings), requestCode);
            } else {
                callback.onRequestPermissionsResult(list);
                finishActivity();
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionCallback permissionCallback = callbackMap.get(requestCode);
        if (permissionCallback != null) {
            callbackMap.remove(requestCode);
            List<Permission> list = permissionMap.get(requestCode);
            permissionMap.remove(requestCode);
            for (int i = 0; i < permissions.length; i++) {
                String permissionName = permissions[i];
                boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                list.add(new Permission(permissionName, granted, shouldShowRequestPermissionRationale(permissionName)));
            }
            permissionCallback.onRequestPermissionsResult(list);
            finishActivity();
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void finishActivity() {
        Activity activity = getActivity();
        if (activity instanceof PermissionRequestActivity) {
            activity.finish();
        }
    }
}
