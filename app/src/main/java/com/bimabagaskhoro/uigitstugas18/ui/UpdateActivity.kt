package com.bimabagaskhoro.uigitstugas18.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertBinding
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonRegister.setOnClickListener{
                updateData()
            }
        }
    }

    private fun updateData() {
        TODO("Not yet implemented")
    }
}