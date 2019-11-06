package com.sjianjun.permission.demo

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sjianjun.permission.util.PermissionUtil
import com.sjianjun.permission.util.isGranted
import sjj.alog.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtil.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE)
        ) {
            Log.e(it.isGranted())
            Log.e(it)
        }
    }
}
