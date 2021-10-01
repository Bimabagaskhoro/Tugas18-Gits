package com.bimabagaskhoro.uigitstugas18.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bimabagaskhoro.uigitstugas18.MainActivity
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityLoginBinding
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseLogins
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bimabagaskhoro.uigitstugas18.ui.person.DetailPersonActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("GENERAL_KEY", Context.MODE_PRIVATE)
        cekLogin()
        binding.apply {
            buttonLogin.setOnClickListener{
                login()
            }
            tvCreateAccount.setOnClickListener(this@LoginActivity)
        }
    }

    private fun cekLogin() {
        if (this.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun isLoggedIn(): Boolean {
        return this.sharedPref.getBoolean("IS_LOGIN", false)
    }

    private fun login() {
        RetrofitClient().apiInstance().login(
                binding.editTextTextEmailAddress.text.toString().trim(),
                binding.editTextTextPassword.text.toString().trim()
        ).enqueue(object : Callback<ResponseLogins>{
            override fun onResponse(call: Call<ResponseLogins>, response: Response<ResponseLogins>) {
                if(response.body()?.status == 1){
                    Toast.makeText(this@LoginActivity, "Respon sukses", Toast.LENGTH_SHORT).show()

                    val name = response.body()?.data?.get(0)?.nama.toString()
                    val email = response.body()?.data?.get(0)?.email.toString()
                    val password = response.body()?.data?.get(0)?.passwd.toString()

                    session(name, email, password)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                }else {
                    Toast.makeText(this@LoginActivity, "respon gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogins>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "respon gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun session(name: String, email: String, password: String) {
        val editor = sharedPref.edit()
        editor.putBoolean("IS_LOGIN", true)
        editor.putString("NAME", name)
        editor.putString("EMAIL", email)
        editor.putString("PASSWORD", password)
        editor.apply()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> {
                val btnLogin = Intent(this, MainActivity::class.java)
                startActivity(btnLogin)
            }
            R.id.tv_create_account -> {
                val regist = Intent(this, RegisterActivity::class.java)
                startActivity(regist)
            }
        }
    }
}