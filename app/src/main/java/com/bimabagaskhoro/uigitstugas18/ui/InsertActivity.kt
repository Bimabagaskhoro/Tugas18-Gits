package com.bimabagaskhoro.uigitstugas18.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.add)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonRegister.setOnClickListener{
                insertData()
            }
        }
    }

    private fun insertData() {
        val edtId :EditText = findViewById(R.id.edt_id)
        val edtName:EditText = findViewById(R.id.edt_nama)
        val edtHarga:EditText = findViewById(R.id.edt_harga)

        RetrofitClient.apiInstance.insertBuah(
                edtId.text.toString().trim(),
                edtName.text.toString().trim(),
                edtHarga.text.toString().trim(),
                "insert_buah"
        ).enqueue(object : Callback<ResponseStatus>{
            override fun onResponse(call: Call<ResponseStatus>, response: Response<ResponseStatus>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        edtId.setText("")
                        edtName.setText("")
                        edtHarga.setText("")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseStatus>, t: Throwable) {
                Toast.makeText(this@InsertActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
            }

        })

    }
}