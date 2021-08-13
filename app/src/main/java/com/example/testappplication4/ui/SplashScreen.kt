package com.example.testappplication4.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.testappplication4.MainActivity
import com.example.testappplication4.R
import kotlinx.coroutines.delay

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backgroundImage:ImageView = findViewById(R.id.SplashScreenImage)
        val animate1 = AnimationUtils.loadAnimation(this, R.anim.side_slide_1)
        val animate2 = AnimationUtils.loadAnimation(this, R.anim.side_slide_2)
        backgroundImage.startAnimation(animate1)
        val handler = Handler()
        handler.postDelayed({backgroundImage.startAnimation(animate2)},1500)



        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}