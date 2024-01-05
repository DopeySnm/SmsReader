package com.example.smsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class SenderType(val value: Int) {
    INBOX(1),
    OUTBOX(2);

    companion object {
        fun getByValue(value: Int) = SenderType.values().first { it.value == value }
    }
}

@Parcelize
data class Message(
    val body: String,
    val senderType: SenderType,
    val date: Long
) : Parcelable