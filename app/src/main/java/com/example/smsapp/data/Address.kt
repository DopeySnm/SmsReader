package com.example.smsapp.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Address(
    val name: String,
    val messages: List<Message>
) : Parcelable