package com.bimabagaskhoro.uigitstugas18.ui.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.bimabagaskhoro.uigitstugas18.MainActivity
import com.bimabagaskhoro.uigitstugas18.R
import com.bimabagaskhoro.uigitstugas18.databinding.ActivityLoginBinding
import com.bimabagaskhoro.uigitstugas18.model.ResponseGambar
import com.bimabagaskhoro.uigitstugas18.model.login.ResponseLogins
import com.bimabagaskhoro.uigitstugas18.rest.RetrofitClient
import com.bimabagaskhoro.uigitstugas18.ui.person.InsertPersonActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(){

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

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
            tvCreateAccount.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            executor = ContextCompat.getMainExecutor(this@LoginActivity)

            biometricPrompt = BiometricPrompt(this@LoginActivity, executor,
                object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(this@LoginActivity, "Authentication Error: $errString", Toast.LENGTH_SHORT).show()
                    }

                    @SuppressLint("HardwareIds")
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val idDevice: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                        loginBiometric(idDevice)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(this@LoginActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login with fingerprint")
                .setSubtitle("Please tap your finger!")
                .setNegativeButtonText("Use Account password")
                .build()

            buttonLoginBiometric.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }
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



    private fun loginBiometric(idDevice : String) {
        RetrofitClient().apiInstance().loginAuth(
            idDevice
        ).enqueue(object : Callback<ResponseGambar>{
            override fun onResponse(
                call: Call<ResponseGambar>,
                response: Response<ResponseGambar>,
            ) {
                Toast.makeText(this@LoginActivity, "Respon sukses", Toast.LENGTH_SHORT).show()

//                val name = response.body()?.data?.get(0)?.nama.toString()
//                val email = response.body()?.data?.get(0)?.email.toString()
//                val password = response.body()?.data?.get(0)?.passwd.toString()
//
//                session(name, email, password)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<ResponseGambar>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Respon gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }

}