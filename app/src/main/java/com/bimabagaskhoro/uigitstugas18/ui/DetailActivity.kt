package com.bimabagaskhoro.uigitstugas18.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityDetailBinding
import com.bimabagaskhoro.uigitstugas18.model.DataItem

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
            }
        }
        getDetail()
    }

    private fun getDetail() {
        val tvName: TextView = findViewById(R.id.tv_nama_buah)
        val tvPrice: TextView = findViewById(R.id.tv_harga)

        val item = intent.getParcelableExtra<DataItem>(EXTRA_DATA) as DataItem

        tvName.text = item.nama
        tvPrice.text = item.harga
    }
}