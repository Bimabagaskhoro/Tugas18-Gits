//package com.bimabagaskhoro.uigitstugas18.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bimabagaskhoro.uigitstugas18.databinding.ItemBinding
//import com.bimabagaskhoro.uigitstugas18.model.animal.AnimalModel
//
//class AnimalAdapter (private val listItem: ArrayList<AnimalModel>):
//    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>(){
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): AnimalAdapter.AnimalViewHolder {
//        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return AnimalViewHolder((view))
//    }
//
//    override fun onBindViewHolder(holder: AnimalAdapter.AnimalViewHolder, position: Int) {
//        holder.bind(listItem[position])
//    }
//
//    override fun getItemCount(): Int = listItem.size
//
//    inner class AnimalViewHolder(private val binding: ItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: AnimalModel) {
////            tvId.text = item.id
////            tvPrice.text = item.harga
//        }
//    }
//    interface NoteListener{
//        fun OnItemClicked(note: AnimalModel)
//    }
//}