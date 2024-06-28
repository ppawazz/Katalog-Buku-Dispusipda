package com.paw.katalogbuku.view.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityMainBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.view.adapter.ListBookAdapter
import com.paw.katalogbuku.viewmodel.BookViewModel
import com.paw.katalogbuku.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: ListBookAdapter
    private val bookViewModel: BookViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupRecyclerView()
        observeData()
    }

    private fun setupUI() {
        with(binding) {
            val isAdmin = intent.getBooleanExtra("isAdmin", false)
            if (isAdmin) {
                fabAdd.show()
            } else {
                fabAdd.hide()
            }

            fabAdd.setOnClickListener {
                // Implement add book logic here
            }

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    bookAdapter.filter(s.toString())
                    clearQueryButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            clearQueryButton.setOnClickListener {
                searchEditText.text.clear()
                clearQueryButton.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        bookAdapter = ListBookAdapter(
            isAdmin = intent.getBooleanExtra("isAdmin", false),
            onEditClick = ::editBook,
            onDeleteClick = ::deleteBook
        )
        binding.rvBook.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }
    }

    private fun observeData() {
        bookViewModel.getAllBooks().observe(this, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.pbMain.visibility = View.GONE
                    bookAdapter.submitFullList(result.data)
                }
                is ResultState.Error -> {
                    binding.pbMain.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun editBook(book: BookItem) {
        // Implement edit book logic here
    }

    private fun deleteBook(book: BookItem) {
        // Implement delete book logic here
    }
}