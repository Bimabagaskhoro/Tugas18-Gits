package com.bimabagaskhoro.uigitstugas18

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.bimabagaskhoro.uigitstugas18.ui.user.LoginActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private val delay: Int = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, delay.toLong())
    }
}