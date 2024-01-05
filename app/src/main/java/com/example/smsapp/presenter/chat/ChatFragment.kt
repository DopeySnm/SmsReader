package com.example.smsapp.presenter.chat

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsapp.R
import com.example.smsapp.databinding.FragmentChatBinding

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private val binding: FragmentChatBinding by viewBinding()
    private val adapter = ChatAdapter()
    private val args = navArgs<ChatFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentAddress = args.value.currentAddress
        binding.addressName.text = args.value.currentAddress.name
        adapter.submitList(currentAddress.messages)

        initializeRecycler()
    }

    private fun initializeRecycler() = with(binding.recyclerViewChat) {
        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            true
        )
        adapter = this@ChatFragment.adapter
        addItemDecoration(createItemDecorator())
    }

    private fun createItemDecorator() =
        DividerItemDecoration(requireContext(), RecyclerView.VERTICAL).apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.sms_item_decoration)
                ?.let { this@apply.setDrawable(it) }
        }

}