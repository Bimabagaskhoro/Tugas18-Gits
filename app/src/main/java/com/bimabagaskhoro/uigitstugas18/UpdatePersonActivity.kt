package com.bimabagaskhoro.uigitstugas18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdateBinding
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityUpdatePersonBinding
import com.bimabagaskhoro.uigitstugas18.model.person.ResponseStatusPerson
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.update)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("nama")
        val email = intent.getStringExtra("email")
        val tittle = intent.getStringExtra("tittle")
        val gambar = intent.getStringExtra("gambar")

        binding.apply {
            edtIdPersonUpdate.setText(id)
            edtNamePersonUpdate.setText(name)
            edtEmailPersonUpdate.setText(email)
            edtTittlePersonUpdate.setText(tittle)
            edtProfilePersonUpdate.setText(gambar)
            buttonSave.setOnClickListener {
                val edtId: EditText = findViewById(R.id.edt_id_person_update)
                val edtName: EditText = findViewById(R.id.edt_name_person_update)
                val edtEmail: EditText = findViewById(R.id.edt_email_person_update)
                val edtTittle: EditText = findViewById(R.id.edt_tittle_person_update)
                val edtGambar: EditText = findViewById(R.id.edt_profile_person_update)

                RetrofitClient().apiInstance().updatePerson(
                    edtName.text.toString().trim(),
                    edtEmail.text.toString().trim(),
                    edtTittle.text.toString().trim(),
                    edtGambar.text.toString().trim(),
                    edtId.text.toString().trim()
                ).enqueue(object : Callback<ResponseStatusPerson>{
                    override fun onResponse(
                        call: Call<ResponseStatusPerson>,
                        response: Response<ResponseStatusPerson>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@UpdatePersonActivity, "Berhasil di Update", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseStatusPerson>, t: Throwable) {
                        Toast.makeText(this@UpdatePersonActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }
}