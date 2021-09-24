package com.bimabagaskhoro.uigitstugas18.adapter

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.uigitstugas18.databinding.ItemBinding
import com.bimabagaskhoro.uigitstugas18.model.DataItem
import com.bimabagaskhoro.uigitstugas18.ui.DetailActivity

class ItemAdapter (private val listItem: ArrayList<DataItem> ):
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    fun setShow(data: List<DataItem>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder((view))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    inner class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.apply {
                tvId.text = item.id
                tvName.text = item.nama
                tvPrice.text = item.harga
                itemView.setOnClickListener {
                    val moveDetail = Intent(itemView.context,DetailActivity::class.java)
                    moveDetail.putExtra(DetailActivity.EXTRA_DATA,item)
                    ContextCompat.startActivity(itemView.context, moveDetail, null)
                }
            }
        }
    }
}