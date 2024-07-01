package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityEditBookBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.viewmodel.BookViewModel
import com.paw.katalogbuku.viewmodel.ViewModelFactory

class EditBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBookBinding
    private var currentImageUri: Uri? = null
    private var book: BookItem? = null
    private val bookViewModel: BookViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBookBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_edit)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        animateViews()
        setupUpdateButton()

        // Mengambil data buku dari Intent
        book = intent.getParcelableExtra("book")
        book?.let { fillBookData(it) }
    }

    private fun fillBookData(book: BookItem) {
        binding.edEditTitle.setText(book.title)
        binding.edEditAuthor.setText(book.author)
        binding.edEditPublisher.setText(book.publisher)
        binding.edEditPages.setText(book.pages.toString())
        // Mengisi URI gambar jika ada
//        book.imageUrl?.let {
//            currentImageUri = Uri.parse(it)
//            binding.uploadImageEdit.setImageURI(currentImageUri)
//            binding.uploadImageEdit.alpha = 1f
//        }
    }

    private fun setupUI() {
        binding.btnCameraEdit.setOnClickListener {
            // Handle camera click
        }
        binding.btnGalleryEdit.setOnClickListener {
            // Handle gallery click
        }
        binding.uploadImageEdit.setOnClickListener {
            // Handle image click
        }
    }

    private fun animateViews() {
        val fadeIn = ObjectAnimator.ofFloat(binding.cvUploadEdit, View.ALPHA, 0f, 1f)
        val fadeIn1 = ObjectAnimator.ofFloat(binding.btnCameraEdit, View.ALPHA, 0f, 1f)
        val fadeIn2 = ObjectAnimator.ofFloat(binding.btnGalleryEdit, View.ALPHA, 0f, 1f)
        val fadeIn3 = ObjectAnimator.ofFloat(binding.editTitle, View.ALPHA, 0f, 1f)
        val fadeIn4 = ObjectAnimator.ofFloat(binding.editAuthor, View.ALPHA, 0f, 1f)
        val fadeIn5 = ObjectAnimator.ofFloat(binding.editPublisher, View.ALPHA, 0f, 1f)
        val fadeIn6 = ObjectAnimator.ofFloat(binding.editPages, View.ALPHA, 0f, 1f)
        val fadeIn7 = ObjectAnimator.ofFloat(binding.buttonEdit, View.ALPHA, 0f, 1f)
        AnimatorSet().apply {
            playTogether(fadeIn, fadeIn1, fadeIn2, fadeIn3, fadeIn4, fadeIn5, fadeIn6, fadeIn7)
            duration = 1000
            start()
        }
    }

    private fun setupUpdateButton() {
        binding.buttonEdit.setOnClickListener {
            updateBook()
        }
    }

    private fun updateBook() {
        val title = binding.edEditTitle.text.toString()
        val author = binding.edEditAuthor.text.toString()
        val publisher = binding.edEditPublisher.text.toString()
        val pages = binding.edEditPages.text.toString().toIntOrNull() ?: 0

        if (title.isNotEmpty() && author.isNotEmpty() && publisher.isNotEmpty() && pages > 0) {
            // Update book data
            val updatedBook = book?.copy(
                title = title,
                author = author,
                publisher = publisher,
                pages = pages,
//                imageUrl = currentImageUri.toString()
            )

            updatedBook?.let {
                bookViewModel.updateBook(it).observe(this, Observer { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            // Show loading indicator if needed
                        }
                        is ResultState.Success -> {
                            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        }
                        is ResultState.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        } else {
            // Show validation error
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
        }
    }
}
