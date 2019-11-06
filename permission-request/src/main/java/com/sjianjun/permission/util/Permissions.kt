package com.sjianjun.permission.util

import com.sjianjun.permission.model.Permission

fun List<Permission>.isGranted(): Boolean {
    forEach {
        if (!it.granted) {
            return false
        }
    }
    return true
}