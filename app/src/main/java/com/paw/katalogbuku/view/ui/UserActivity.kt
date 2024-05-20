package com.paw.katalogbuku.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
    }
}