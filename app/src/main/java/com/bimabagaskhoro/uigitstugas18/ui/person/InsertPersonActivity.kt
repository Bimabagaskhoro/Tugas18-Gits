package com.bimabagaskhoro.uigitstugas18.ui.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertBinding
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityInsertPersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertPersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.add)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonSave.setOnClickListener{
                insertData()
            }
        }
    }

    private fun insertData() {
        val edtId : EditText = findViewById(R.id.edt_id_person)
        val edtName: EditText = findViewById(R.id.edt_name_person)
        val edtEmail: EditText = findViewById(R.id.edt_email_person)
        val edtTittle: EditText = findViewById(R.id.edt_tittle_person)
        val edtImg: EditText = findViewById(R.id.edt_profile_person)

        RetrofitClient().apiInstance().insertPerson(
                edtId.text.toString().trim(),
                edtName.text.toString().trim(),
                edtEmail.text.toString().trim(),
                edtTittle.text.toString().trim(),
                edtImg.text.toString().trim(),
                "insert_person"
        ).enqueue(object : Callback<ResponseStatusPerson>{
            override fun onResponse(call: Call<ResponseStatusPerson>, response: Response<ResponseStatusPerson>) {
                if (response.isSuccessful){
                    if (response.body()?.status == 1){
                        edtId.setText("")
                        edtName.setText("")
                        edtEmail.setText("")
                        edtTittle.setText("")
                        edtImg.setText("")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                Toast.makeText(this@InsertPersonActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
            }

        })

    }
}