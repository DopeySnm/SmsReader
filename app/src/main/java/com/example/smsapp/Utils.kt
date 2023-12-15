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
    val permissionState = ContextCompat.checkSelfPermission( // вернёт числовое обозначение
        this.requireContext(),
        permission
    )
    if (permissionState != PackageManager.PERMISSION_GRANTED) // То есть если в системе не выдано
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) successDelegate() // Permissions granted just now!!!
            else failureDelegate() // Explain to user why app's functionality not working
            return@registerForActivityResult
        }.launch(permission)
    else successDelegate() // permission already granted times ago
}
