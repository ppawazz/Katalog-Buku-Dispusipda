package com.paw.katalogbuku.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityMainBinding
import com.paw.katalogbuku.model.remote.response.BookResponse
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.view.adapter.ListBookAdapter
import com.paw.katalogbuku.viewmodel.BookViewModel
import com.paw.katalogbuku.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: ListBookAdapter
    private val bookViewModel: BookViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private var isAdmin: Boolean = false

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

        isAdmin = intent.getBooleanExtra("isAdmin", false)
        setupUI()
        setupRecyclerView()
        setupObservers()

        // Memuat data buku
        bookViewModel.getAllBooks()
    }

    private fun setupUI() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text.toString())  // Menggunakan metode setText
                searchView.hide()
                Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }

            if (isAdmin) {
                fabAdd.show()
                fabAdd.setOnClickListener {
                    // Implement add book logic here
                    // Contoh: startActivity(Intent(this@MainActivity, AddBookActivity::class.java))
                }
            } else {
                fabAdd.hide()
            }
        }
    }


    private fun setupRecyclerView() {
        bookAdapter = ListBookAdapter(isAdmin, ::editBook, ::deleteBook)
        binding.rvBook.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }
    }

    private fun setupObservers() {
        bookViewModel.getAllBooks().observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.pbMain.visibility = View.GONE
                    bookAdapter.submitList(result.data)
                }
                is ResultState.Error -> {
                    binding.pbMain.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun editBook(book: BookResponse) {
        if (isAdmin) {
            // Buka Activity atau Dialog untuk mengedit buku
            // Contoh: startActivity(Intent(this, EditBookActivity::class.java).putExtra("BOOK_ID", book.id))
        } else {
            Toast.makeText(this, "You don't have permission to edit this book", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteBook(book: BookResponse) {
        if (isAdmin) {
            bookViewModel.deleteBook(book.id).observe(this) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        binding.pbMain.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.pbMain.visibility = View.GONE
                        Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
                        // Refresh data
                        bookViewModel.getAllBooks()
                    }
                    is ResultState.Error -> {
                        binding.pbMain.visibility = View.GONE
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "You don't have permission to delete this book", Toast.LENGTH_SHORT).show()
        }
    }
}
