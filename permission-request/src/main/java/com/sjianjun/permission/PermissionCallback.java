package com.sjianjun.permission;


import com.sjianjun.permission.model.Permission;

import java.util.List;

/**
 * Created by SJJ on 2017/7/30.
 */

public interface PermissionCallback {
    void onRequestPermissionsResult(List<Permission> permissions);
}
