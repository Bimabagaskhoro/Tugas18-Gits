package com.bimabagaskhoro.uigitstugas18.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdateUserBinding
import com.bimabagaskhoro.uigitstugas18.model.login.DataItem
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUser : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserBinding
    companion object {
        const val EXTRA_LINK = "http://192.168.43.225/tugasGitsApi/uploadgambar/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)

        val intent = intent
        val id = intent.getStringExtra("id")
        binding.apply {
            tvIdUpdateUser.setText(id)
           // getUserByid()
        }
    }

}