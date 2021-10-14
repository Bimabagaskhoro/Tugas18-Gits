package com.bimabagaskhoro.uigitstugas18.ui.person

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.uigitstugas18.MainActivity
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.adapter.PersonAdapter
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.DataItemPerson
import com.bimabagaskhoro.uigitstugas18.model.person.ResponsePerson
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class PersonActivity : AppCompatActivity() {
    val personAdapter = PersonAdapter(arrayListOf())
    private lateinit var binding: ActivityPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvItemPerson.layoutManager = LinearLayoutManager(this@PersonActivity)
            rvItemPerson.setHasFixedSize(true)
            rvItemPerson.adapter = personAdapter
            getData()
            fabInsert.setOnClickListener {
                val intent = Intent(this@PersonActivity, InsertPersonActivity::class.java)
                startActivity(intent)
            }
            imgBack.setOnClickListener {
                val intent = Intent(this@PersonActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun deleteData(context: Context, id: String) {
        RetrofitClient().apiInstance().deletePerson(id)
            .enqueue(object : Callback<ResponseStatusPerson> {
                override fun onResponse(call: Call<ResponseStatusPerson>, response: Response<ResponseStatusPerson>) {
                    Toast.makeText(context.applicationContext, "Berhasil $id", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                    Toast.makeText(this@PersonActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getData() {
        RetrofitClient().apiInstance().getPerson()
            .enqueue(object : Callback<ResponsePerson>{
                override fun onResponse(
                        call: Call<ResponsePerson>,
                        response: Response<ResponsePerson>
                ) {
                    if (response.isSuccessful){
                        show(response.body())
                    }else{
                        Toast.makeText(this@PersonActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponsePerson>, t: Throwable) {
                    Toast.makeText(this@PersonActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun show(data: ResponsePerson?){
        val result = data?.data
        personAdapter.setShow(result as List<DataItemPerson>)
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                rvItemPerson.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                rvItemPerson.visibility = View.VISIBLE
            }
        }
    }
}