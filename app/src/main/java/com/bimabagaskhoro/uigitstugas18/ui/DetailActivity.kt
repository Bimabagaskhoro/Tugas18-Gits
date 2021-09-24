package com.bimabagaskhoro.uigitstugas18.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityDetailBinding
import com.bimabagaskhoro.uigitstugas18.model.DataItem
import com.bimabagaskhoro.uigitstugas18.model.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.detail)
        actionbar.setDisplayHomeAsUpEnabled(true)


        binding.apply {
            fabUpdate.setOnClickListener {
                val intent = Intent(this@DetailActivity, UpdateActivity::class.java)
                startActivity(intent)
            }

            fabDelete.setOnClickListener {
                deleteData()
            }
        }

        val id = intent.getIntExtra("id",0)
        getDetail()
    }

    private fun getDetail() {
        val tvId: TextView = findViewById(R.id.tv_id_detail)
        val tvName: TextView = findViewById(R.id.tv_nama_buah_detail)
        val tvPrice: TextView = findViewById(R.id.tv_harga_detail)

        val item = intent.getParcelableExtra<DataItem>(EXTRA_DATA) as DataItem
        tvId.text = item.id
        tvName.text = item.nama
        tvPrice.text = item.harga
    }

    private fun deleteData() {
       /* RetrofitClient.apiInstance.deleteBuah()
                .enqueue(object : Callback<ResponseStatus>{
                    override fun onResponse(call: Call<ResponseStatus>, response: Response<ResponseStatus>) {
                     if (response.isSuccessful){
                         if (response.body()?.status == 1){
                             (response.body())
                         }
                     }
                    }

                    override fun onFailure(call: Call<ResponseStatus>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })*/
    }
}