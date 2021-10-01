package com.bimabagaskhoro.uigitstugas18

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityMainBinding
import com.bimabagaskhoro.uigitstugas18.ui.buah.BuahActivity
import com.bimabagaskhoro.uigitstugas18.ui.buah.UpdateActivity
import com.bimabagaskhoro.uigitstugas18.ui.person.PersonActivity

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.home)

        sharedPref = getSharedPreferences("GENERAL_KEY", Context.MODE_PRIVATE)

        binding.apply {
            btnBuah.setOnClickListener{
                val intent = Intent(this@MainActivity, BuahActivity::class.java)
                startActivity(intent)
            }
            btnPerson.setOnClickListener{
                val intent = Intent(this@MainActivity, PersonActivity::class.java)
                startActivity(intent)
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu2 -> {
                logout(sharedPref)
                true
            }
            else -> true
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