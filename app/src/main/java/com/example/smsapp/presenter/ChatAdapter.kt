package com.example.smsapp.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.smsapp.R
import com.example.smsapp.data.Message
import com.example.smsapp.data.SenderType
import com.example.smsapp.databinding.SmsInboxItemBinding
import com.example.smsapp.databinding.SmsOutboxItemBinding
import java.lang.IllegalArgumentException

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val list = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType){
            R.layout.sms_inbox_item -> ChatViewHolder.ChatInboxViewHolder(
                SmsInboxItemBinding.inflate(layoutInflater, parent, false)
            )
            R.layout.sms_outbox_item -> ChatViewHolder.ChatOutboxViewHolder(
                SmsOutboxItemBinding.inflate(layoutInflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provider")
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<Message>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        when(holder){
            is ChatViewHolder.ChatInboxViewHolder -> holder.bind(list[position])
            is ChatViewHolder.ChatOutboxViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position].senderType){
            SenderType.INBOX -> R.layout.sms_inbox_item
            SenderType.OUTBOX -> R.layout.sms_outbox_item
        }
    }

    sealed class ChatViewHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        class ChatOutboxViewHolder(
            private val binding: SmsOutboxItemBinding
        ) : ChatViewHolder(binding) {
            fun bind(message: Message){
                binding.message.text = message.body
                binding.timeMessage.text = "10:56 Am"
            }
        }

        class ChatInboxViewHolder(
            private val binding: SmsInboxItemBinding
        ) : ChatViewHolder(binding) {
            fun bind(message: Message){
                binding.message.text = message.body
                binding.timeMessage.text = "10:56 Am"
            }
        }

    }


//    private val list = mutableListOf<Message>()
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = SmsItemBinding.inflate(layoutInflater, parent, false)
//        return ChatViewHolder(binding)
//    }
//
//    fun submitList(list: List<Message>) = with(this.list) {
//        clear()
//        addAll(list)
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//    class ChatViewHolder(
//        private val binding_inbox: SmsItemBinding
//    ) : RecyclerView.ViewHolder(binding_inbox.root){
//        fun bind(message: String){
//            binding_inbox.message.text = message
//            binding_inbox.timeMessage.text = "10:56 Am"
//        }
//    }
}