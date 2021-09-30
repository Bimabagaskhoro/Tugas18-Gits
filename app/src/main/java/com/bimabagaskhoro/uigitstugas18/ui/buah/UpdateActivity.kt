package com.bimabagaskhoro.uigitstugas18.ui.buah

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdateBinding
import com.bimabagaskhoro.uigitstugas18.model.buah.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val id = intent.getStringExtra("id")
        val nama = intent.getStringExtra("nama")
        val harga = intent.getStringExtra("harga")

        binding.apply {
            edtIdUpdated.setText(id)
            edtNamaUpdated.setText(nama)
            edtHargaUpdated.setText(harga)
            buttonRegister.setOnClickListener{

                val edtId: EditText = findViewById(R.id.edt_id_updated)
                val edtName: EditText = findViewById(R.id.edt_nama_updated)
                val edtHarga: EditText = findViewById(R.id.edt_harga_updated)

                RetrofitClient().apiInstance().updateBuah(
                        edtName.text.toString().trim(),
                        edtHarga.text.toString().trim(),
                        edtId.text.toString().trim()
                ).enqueue(object : Callback<ResponseStatus>{
                    override fun onResponse(call: Call<ResponseStatus>, response: Response<ResponseStatus>) {
                        if (response.isSuccessful){
                            Toast.makeText(this@UpdateActivity, "Berhasil di Update", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseStatus>, t: Throwable) {
                        Toast.makeText(this@UpdateActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }
}