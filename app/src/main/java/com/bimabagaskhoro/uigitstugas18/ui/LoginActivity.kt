package com.bimabagaskhoro.uigitstugas18.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bimabagaskhoro.uigitstugas18.MainActivity
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonLogin.setOnClickListener(this@LoginActivity)
            tvCreateAccount.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> {
                val btnLogin = Intent(this, MainActivity::class.java)
                startActivity(btnLogin)
            }
            R.id.tv_create_account -> {
                val btnCreate = Intent(this, RegisterActivity::class.java)
                startActivity(btnCreate)
            }
        }
    }
}