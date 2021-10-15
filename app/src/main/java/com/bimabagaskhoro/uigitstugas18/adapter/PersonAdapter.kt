package com.bimabagaskhoro.uigitstugas18.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.uigitstugas18.ui.person.PersonActivity
import com.bimabagaskhoro.uigitstugas18.ui.person.UpdatePersonActivity
import com.bimabagaskhoro.uigitstugas18.ui.person.DetailPersonActivity
import com.bimabagaskhoro.uigitstugas18.databinding.ItemPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.DataItemPerson
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PersonAdapter(private val listItem: ArrayList<DataItemPerson> ):
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){
    companion object {
        const val EXTRA_LINK = "http://192.168.43.225/tugasGitsApi/uploadgambar/"
    }

    fun setShow(data: List<DataItemPerson>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.PersonViewHolder {
        val view = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder((view))
    }

    override fun onBindViewHolder(holder: PersonAdapter.PersonViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    inner class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItemPerson) {
            binding.apply {
                tvIdPerson.text = item.id
                tvNamePerson.text = item.nama
                tvEmailPerson.text = item.email
                tvTittle.text = item.tittle
                Glide.with(itemView)
                        .load(EXTRA_LINK + item.gambar)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgPerson)
                itemView.setOnClickListener {
                    val moveDetailPerson = Intent(itemView.context, DetailPersonActivity::class.java)
                    moveDetailPerson.putExtra(DetailPersonActivity.EXTRA_PERSON,item)
                    ContextCompat.startActivity(itemView.context, moveDetailPerson, null)
                }

                btnUpdatePerson.setOnClickListener {
                    val intent = Intent(tvIdPerson.context, UpdatePersonActivity::class.java)
                    intent.putExtra("id",item.id)
                    intent.putExtra("nama",item.nama)
                    intent.putExtra("email",item.email)
                    intent.putExtra("tittle",item.tittle)
                    intent.putExtra("gambar",item.gambar)
                    intent.putExtra(UpdatePersonActivity.EXTRA_DATA,item)
                    btnUpdatePerson.context.startActivity(intent)
                }
                btnDeletedPerson.setOnClickListener{
                    PersonActivity().deleteData(itemView.context, tvIdPerson.text.toString())
                }
            }
        }
    }
}