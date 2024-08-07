package com.paw.katalogbuku.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paw.katalogbuku.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCatalog.setOnClickListener {
            val toMain = Intent(this@UserActivity, MainActivity::class.java)
            startActivity(toMain)
        }

        binding.btnLoginAdmin.setOnClickListener {
            val toLogin = Intent(this@UserActivity, LoginActivity::class.java)
            startActivity(toLogin)
        }

        // Add click listeners for Instagram ImageView and TextView
        binding.ivIg.setOnClickListener {
            openInstagramProfile()
        }

        binding.tvDinas.setOnClickListener {
            openInstagramProfile()
        }
    }

    private fun openInstagramProfile() {
        val instagramUrl = "https://www.instagram.com/dispusipda_kotasmi"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
        startActivity(intent)
    }
}