package com.bimabagaskhoro.uigitstugas18

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityMainBinding
import com.bimabagaskhoro.uigitstugas18.ui.buah.BuahActivity
import com.bimabagaskhoro.uigitstugas18.ui.person.PersonActivity

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.home)

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


}