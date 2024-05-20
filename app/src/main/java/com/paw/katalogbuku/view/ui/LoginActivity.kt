package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityLoginBinding
import com.paw.katalogbuku.utils.showToast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email == "admin@gmail.com" && password == "admin") {
                val toMain = Intent(this, MainActivity::class.java)
                toMain.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                toMain.putExtra("isAdmin", true)
                startActivity(toMain)
                finish()
            } else {
                showToast("Email dan Kata Sandi salah")
            }
        }
    }

    private fun playAnimation() {
        val text = ObjectAnimator.ofFloat(binding.tvLoginWelcome, View.ALPHA, 1f).setDuration(100)
        val email =
            ObjectAnimator.ofFloat(binding.tlLoginEmail, View.ALPHA, 1f).setDuration(100)
        val password =
            ObjectAnimator.ofFloat(binding.tlLoginPassword, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                text,
                email,
                password,
                login
            )
            startDelay = 100
        }.start()
    }
}