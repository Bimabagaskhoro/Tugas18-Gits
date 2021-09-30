package com.bimabagaskhoro.uigitstugas18.ui.buah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityDetailBinding
import com.bimabagaskhoro.uigitstugas18.model.buah.DataItem

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
}