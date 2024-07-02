package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityEditBookBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.utils.showToast
import com.paw.katalogbuku.view.viewmodel.BookViewModel
import com.paw.katalogbuku.view.viewmodel.ViewModelFactory

class EditBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBookBinding
    private var book: BookItem? = null
    private val bookViewModel: BookViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBookBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        book = intent.getParcelableExtra("book")
        book?.let { fillBookData(it) }

        animateViews()
        setupUpdateButton()
    }

    private fun fillBookData(book: BookItem) {
        binding.edEditTitle.setText(book.title)
        binding.edEditAuthor.setText(book.author)
        binding.edEditPublisher.setText(book.publisher)
        binding.edEditPages.setText(book.pages.toString())
    }

    private fun setupUpdateButton() {
        binding.buttonEdit.setOnClickListener {
            val title = binding.edEditTitle.text.toString()
            val author = binding.edEditAuthor.text.toString()
            val publisher = binding.edEditPublisher.text.toString()
            val pages = binding.edEditPages.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty() && author.isNotEmpty() && publisher.isNotEmpty() && pages > 0) {
                book?.let {
                    val updatedBook = it.copy(
                        title = title,
                        author = author,
                        publisher = publisher,
                        pages = pages,
                        cover = it.cover
                    )

                    bookViewModel.updateBook(updatedBook).observe(this) { result ->
                        when (result) {
                            is ResultState.Loading -> {
                                binding.progressIndicatorEdit.isVisible = true
                            }

                            is ResultState.Success -> {
                                Toast.makeText(
                                    this,
                                    getString(R.string.success_update),
                                    Toast.LENGTH_SHORT
                                ).show()
                                setResult(RESULT_OK)
                                finish()
                            }

                            is ResultState.Error -> {
                                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                showToast(getString(R.string.fill_correctly))
            }
        }
    }

    private fun animateViews() {
        val welcome = ObjectAnimator.ofFloat(binding.tvEditBook, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.edEditTitle, View.ALPHA, 1f).setDuration(100)
        val tlTitle = ObjectAnimator.ofFloat(binding.editTitle, View.ALPHA, 1f).setDuration(100)
        val author = ObjectAnimator.ofFloat(binding.edEditAuthor, View.ALPHA, 1f).setDuration(100)
        val tlAuthor = ObjectAnimator.ofFloat(binding.editAuthor, View.ALPHA, 1f).setDuration(100)
        val publisher =
            ObjectAnimator.ofFloat(binding.edEditPublisher, View.ALPHA, 1f).setDuration(100)
        val tlPublisher =
            ObjectAnimator.ofFloat(binding.editPublisher, View.ALPHA, 1f).setDuration(100)
        val pages = ObjectAnimator.ofFloat(binding.edEditPages, View.ALPHA, 1f).setDuration(100)
        val tlPages = ObjectAnimator.ofFloat(binding.editPages, View.ALPHA, 1f).setDuration(100)
        val update = ObjectAnimator.ofFloat(binding.buttonEdit, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playTogether(
                welcome,
                update,
                title,
                tlTitle,
                author,
                tlAuthor,
                publisher,
                tlPublisher,
                pages,
                tlPages
            )
            startDelay = 200
        }.start()
    }
}