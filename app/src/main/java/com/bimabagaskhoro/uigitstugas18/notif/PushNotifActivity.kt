package com.bimabagaskhoro.uigitstugas18.notif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityPushNotifBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseNotification
import com.bimabagaskhoro.uigitstugas18.notif.FirebaseService.Companion.TAG
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PushNotifActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPushNotifBinding
    companion object {
        var TOKEN = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushNotifBinding.inflate(layoutInflater)
        setContentView(binding.root)


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(FirebaseService.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            TOKEN = token.toString()
            token?.let { Log.d(FirebaseService.TAG, it) }
            Log.d(TAG, "Token saat ini: $token")

        })

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.addnotif)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            buttonPush.setOnClickListener{
                pushNotif()
            }
        }

    }

    private fun pushNotif() {
        RetrofitClient().apiInstance().pushNotif(
            TOKEN,
            binding.edtTittlePush.text.toString().trim(),
            binding.edtMessage.text.toString().trim(),
            "sendPushNotification"
        ).enqueue(object : Callback<ResponseNotification>{
            override fun onResponse(
                call: Call<ResponseNotification>,
                response: Response<ResponseNotification>,
            ) {
                if (response.isSuccessful) {
                    binding.edtTittlePush.setText("")
                    binding.edtMessage.setText("")
                    Toast.makeText(this@PushNotifActivity,
                        "Respon sukses",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseNotification>, t: Throwable) {
                Toast.makeText(this@PushNotifActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
            }

        })
    }
}