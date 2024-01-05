package com.example.smsapp.presenter.address_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smsapp.data.Address
import com.example.smsapp.databinding.AddressItemBinding

class AddressAdapter(
    private val onItemClick: (Address) -> Unit,
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private val list = mutableListOf<Address>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AddressItemBinding.inflate(layoutInflater, parent, false)
        return AddressViewHolder(binding, onItemClick)
    }

    fun submitList(list: List<Address>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class AddressViewHolder(
        private val binding: AddressItemBinding,
        private val onItemClick: (Address) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(address: Address){
            binding.nameSubscriber.text = address.name
            binding.messages.text = address.messages.first().body

            binding.root.setOnClickListener  {
                onItemClick(address)
            }
        }
    }

}