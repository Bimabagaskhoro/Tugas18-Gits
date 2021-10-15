package com.bimabagaskhoro.uigitstugas18

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityMainBinding
import com.bimabagaskhoro.uigitstugas18.notif.PushNotifActivity
import com.bimabagaskhoro.uigitstugas18.ui.buah.BuahActivity
import com.bimabagaskhoro.uigitstugas18.ui.person.PersonActivity
import com.bimabagaskhoro.uigitstugas18.ui.user.UserActivity

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("GENERAL_KEY", Context.MODE_PRIVATE)

        binding.apply {
            cardBuah.setOnClickListener{
                val intent = Intent(this@MainActivity, BuahActivity::class.java)
                startActivity(intent)
            }
            cardView2.setOnClickListener{
                val intent = Intent(this@MainActivity, PersonActivity::class.java)
                startActivity(intent)
            }
            cardView3.setOnClickListener{
                val intent = Intent(this@MainActivity, UserActivity::class.java)
                startActivity(intent)
            }
            imgLogout.setOnClickListener {
                logout(sharedPref)
            }
            imgNotification.setOnClickListener {
                val intent = Intent(this@MainActivity, PushNotifActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun logout(sharedPref: SharedPreferences) {
        sharedPref.edit().clear().apply()
        Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
        result()
    }

    private fun result() {
        finish()
    }
}