package com.bimabagaskhoro.uigitstugas18

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.uigitstugas18.adapter.ItemAdapter
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityMainBinding
import com.bimabagaskhoro.uigitstugas18.model.DataItem
import com.bimabagaskhoro.uigitstugas18.model.ResponseData
import com.bimabagaskhoro.uigitstugas18.model.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bimabagaskhoro.uigitstugas18.ui.InsertActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    val itemAdapter = ItemAdapter(arrayListOf())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.home)

        binding.apply {
            rvItem.layoutManager = LinearLayoutManager(this@MainActivity)
            rvItem.setHasFixedSize(true)
            rvItem.adapter = itemAdapter
            getData()
            fabInsert.setOnClickListener {
                val intent = Intent(this@MainActivity, InsertActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getData() {
        RetrofitClient().apiInstance().getBuah()
                .enqueue(object : Callback<ResponseData>{
                    override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>
                    ) {
                        if (response.isSuccessful){
                            show(response.body())
                        }else{
                            Toast.makeText(this@MainActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                    }

                })
    }

    fun deleteData(context: Context, id: String) {
        RetrofitClient().apiInstance().deleteBuah(id)
                .enqueue(object : Callback<ResponseStatus>{
                    override fun onResponse(call: Call<ResponseStatus>, response: Response<ResponseStatus>) {
                        Toast.makeText(context.applicationContext, "Berhasil $id", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseStatus>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                    }
                })
    }
    private fun show(data: ResponseData?){
        val result = data?.data
        itemAdapter.setShow(result as List<DataItem>)
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                rvItem.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                rvItem.visibility = View.VISIBLE
            }
        }
    }
}