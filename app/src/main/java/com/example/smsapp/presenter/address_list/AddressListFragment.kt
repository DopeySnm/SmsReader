package com.example.smsapp.presenter.address_list

import android.Manifest.permission.READ_SMS
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsapp.R
import com.example.smsapp.data.Address
import com.example.smsapp.databinding.FragmentAddressListBinding
import com.example.smsapp.requirePermission

class AddressListFragment : Fragment(R.layout.fragment_address_list) {

    private val binding: FragmentAddressListBinding by viewBinding()
    private val viewModel: SmsListViewModel by viewModels()
    private val adapter = AddressAdapter(
        ::onChatItemClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filteredAddresses.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.addressesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeUI()
        requireSmsPermission()
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = READ_SMS,
            successDelegate = {
                viewModel.loadSmsMessages(requireContext().contentResolver)
            },
            failureDelegate = {

            }
        )
    }

    private fun initializeUI() {
        initializeSearch()
        initializeRecycler()
    }

    private fun onChatItemClick(entry: Address) {
        val action = AddressListFragmentDirections.actionAddressListFragmentToChatFragment(entry)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initializeSearch() = with(binding.search) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.filterAddresses(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.filterAddresses(it) }
                return true
            }
        })
    }

    private fun initializeRecycler() = with(binding.recyclerViewAddress) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@AddressListFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }
}