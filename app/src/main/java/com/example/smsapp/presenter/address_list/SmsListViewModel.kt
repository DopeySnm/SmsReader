package com.example.smsapp.presenter.address_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smsapp.data.Address
import com.example.smsapp.data.Message
import com.example.smsapp.data.SenderType
import kotlinx.coroutines.launch

class SmsListViewModel : ViewModel() {

    private val _addressesLiveData = MutableLiveData<List<Address>>()
    val addressesLiveData: LiveData<List<Address>>
        get() = _addressesLiveData

    val filteredAddresses = MutableLiveData<List<Address>>()

    fun filterAddresses(query: String) {
        viewModelScope.launch {
            val filteredList = mutableListOf<Address>()
            addressesLiveData.value?.map {
                if (it.name.contains(query, true)) filteredList.add(it)
            }
            filteredAddresses.postValue(filteredList)
        }
    }

    fun loadSmsMessages(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val result = AddressAndMessageList(mutableListOf())

        if (cursor?.moveToFirst() == true) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val typeSender = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))

                if (typeSender != 2)
                    if (typeSender != 1)
                        continue

                if (address != null)
                    result.list.add(address to Message(
                        body,
                        SenderType.getByValue(typeSender),
                        date
                    ))

            } while (cursor.moveToNext())
        }

        cursor?.close()

        val res = result.toSmsChatEntriesList()

        _addressesLiveData.postValue(res)
    }

}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, Message>>
) {
    fun toSmsChatEntriesList(): List<Address> =
        list
            .groupBy { it.first }
            .map {
                val messages = it.value.map {
                    it.second
                }
                Address(it.key, messages)
            }
}

