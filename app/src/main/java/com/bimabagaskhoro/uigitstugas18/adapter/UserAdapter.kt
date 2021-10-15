package com.bimabagaskhoro.uigitstugas18.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.uigitstugas18.ui.user.UpdateUserActivity
import com.bimabagaskhoro.uigitstugas18.databinding.ItemUserBinding
import com.bimabagaskhoro.uigitstugas18.model.login.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter(private val listItem: ArrayList<DataItem>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    companion object {
        const val EXTRA_LINK = "http://192.168.43.225/tugasGitsApi/uploadgambar/"
    }

    fun setShow(data: List<DataItem>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.apply {
                tvIdUser.text = item.id
                tvNameUser.text = item.nama
                tvEmailUser.text = item.email
                Glide.with(itemView)
                    .load(EXTRA_LINK + item.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgUser)
//                itemView.setOnClickListener {
//                    val moveDetailPerson = Intent(itemView.context, DetailPersonActivity::class.java)
//                    moveDetailPerson.putExtra(DetailPersonActivity.EXTRA_PERSON,item)
//                    ContextCompat.startActivity(itemView.context, moveDetailPerson, null)
//                }
                btnUpdateUser.setOnClickListener {
                    val intent = Intent(tvIdUser.context, UpdateUserActivity::class.java)
                    intent.putExtra("id",item.id)
                    intent.putExtra("nama",item.nama)
                    intent.putExtra("email",item.email)
                    intent.putExtra("passwd",item.passwd)
                    intent.putExtra("avatar",item.avatar)
                    intent.putExtra(UpdateUserActivity.EXTRA_DATA,item)
                    btnUpdateUser.context.startActivity(intent)
                }
            }
        }
    }
}