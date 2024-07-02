package com.paw.katalogbuku.view.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityMainBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.utils.showToast
import com.paw.katalogbuku.view.adapter.ListBookAdapter
import com.paw.katalogbuku.view.viewmodel.BookViewModel
import com.paw.katalogbuku.view.viewmodel.ViewModelFactory

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
                val toAddBook = Intent(this@MainActivity, AddBookActivity::class.java)
                startActivityForResult(toAddBook, ADD_BOOK_REQUEST_CODE)
            }

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

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
            onDeleteClick = ::confirmDeleteBook
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
        val intent = Intent(this, EditBookActivity::class.java)
        intent.putExtra("book", book)
        startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE)
    }

    private fun confirmDeleteBook(book: BookItem) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.remove_book))
            .setMessage(getString(R.string.sure_remove))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> deleteBook(book) }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun deleteBook(book: BookItem) {
        bookViewModel.deleteBook(book.id).observe(this, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }

                is ResultState.Success -> {
                    showToast(getString(R.string.success_delete))
                    refreshBookList()
                }

                is ResultState.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun refreshBookList() {
        bookViewModel.getAllBooks().observe(this) { result ->
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
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshBookList()
        } else if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshBookList()
        }
    }

    companion object {
        private const val EDIT_BOOK_REQUEST_CODE = 1
        private const val ADD_BOOK_REQUEST_CODE = 2
    }
}