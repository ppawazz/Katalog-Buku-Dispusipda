package com.paw.katalogbuku.view.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityMainBinding
import com.paw.katalogbuku.model.local.BookModel
import com.paw.katalogbuku.view.adapter.ListBookAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var bookAdapter : ListBookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bookAdapter = ListBookAdapter()
        binding.rvBook.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

        val bookList = listOf(
            BookModel(1, R.drawable.book_cover1, "Title 1", "Author 1", "Description 1"),
            BookModel(2, R.drawable.book_cover2, "Title 2", "Author 2", "Description 2"),
            BookModel(3, R.drawable.book_cover3, "Title 3", "Author 3", "Description 3")
        )

        bookAdapter.submitList(bookList)
    }
}