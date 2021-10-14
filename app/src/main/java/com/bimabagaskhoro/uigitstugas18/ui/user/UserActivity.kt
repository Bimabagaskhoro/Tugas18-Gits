package com.bimabagaskhoro.uigitstugas18.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.uigitstugas18.MainActivity
import com.bimabagaskhoro.uigitstugas18.adapter.UserAdapter
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUserBinding
import com.bimabagaskhoro.uigitstugas18.model.login.DataItem
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseLogins
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {
    val userAdapter = UserAdapter(arrayListOf())
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            rvItemUser.layoutManager = LinearLayoutManager(this@UserActivity)
            rvItemUser.setHasFixedSize(true)
            rvItemUser.adapter = userAdapter
            getData()
            imgBack.setOnClickListener {
                val intent = Intent(this@UserActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getData() {
        RetrofitClient().apiInstance().getUser()
            .enqueue(object : Callback<ResponseLogins> {
                override fun onResponse(call: Call<ResponseLogins>, response: Response<ResponseLogins>) {
                    if (response.isSuccessful){
                        show(response.body())
                    }else{
                        Toast.makeText(this@UserActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseLogins>, t: Throwable) {
                    Toast.makeText(this@UserActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                }
            })
    }
    private fun show(data: ResponseLogins?){
        val result = data?.data
        userAdapter.setShow(result as List<DataItem>)
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                rvItemUser.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                rvItemUser.visibility = View.VISIBLE
            }
        }
    }
}