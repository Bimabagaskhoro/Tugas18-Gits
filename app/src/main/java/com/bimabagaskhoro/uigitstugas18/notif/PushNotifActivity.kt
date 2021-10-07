package com.bimabagaskhoro.uigitstugas18.notif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityPushNotifBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseNotification
import com.bimabagaskhoro.uigitstugas18.model.buah.ResponseStatus
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PushNotifActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushNotifBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushNotifBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.addnotif)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonPush.setOnClickListener{
                RetrofitClient().apiInstance().pushNotif(
                    binding.edtTittlePush.toString().trim(),
                    binding.edtMessage.toString().trim()
                ).enqueue(object : Callback<ResponseNotification>{
                    override fun onResponse(
                        call: Call<ResponseNotification>,
                        response: Response<ResponseNotification>,
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1) {
                                binding.edtTittlePush.setText("")
                                binding.edtMessage.setText("")
                                Toast.makeText(this@PushNotifActivity,
                                    "Respon sukses",
                                    Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseNotification>, t: Throwable) {
                        Toast.makeText(this@PushNotifActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }

    }

    private fun pushNotif() {

    }
}