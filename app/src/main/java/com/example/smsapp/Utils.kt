package com.example.smsapp

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requirePermission(
    permission: String,
    successDelegate: () -> Unit,
    failureDelegate: () -> Unit,
) {
    val permissionState = ContextCompat.checkSelfPermission(
        this.requireContext(),
        permission
    )
    if (permissionState != PackageManager.PERMISSION_GRANTED)
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) successDelegate()
            else failureDelegate()
            return@registerForActivityResult
        }.launch(permission)
    else successDelegate()
}
