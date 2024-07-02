package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityEditBookBinding
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.utils.getImageUri
import com.paw.katalogbuku.utils.uriToFile
import com.paw.katalogbuku.view.viewmodel.BookViewModel
import com.paw.katalogbuku.view.viewmodel.ViewModelFactory
import java.io.File

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

        book = intent.getParcelableExtra("book")
        book?.let { fillBookData(it) }

        setupUI()
        animateViews()
        setupUpdateButton()
    }

    private fun fillBookData(book: BookItem) {
        binding.edEditTitle.setText(book.title)
        binding.edEditAuthor.setText(book.author)
        binding.edEditPublisher.setText(book.publisher)
        binding.edEditPages.setText(book.pages.toString())
        book.cover?.let {
            currentImageUri = Uri.parse(it)
            binding.uploadImageEdit.setImageURI(currentImageUri)
            binding.uploadImageEdit.alpha = 1f
        }
    }

    private fun setupUI() {
        binding.btnGalleryEdit.setOnClickListener { startGallery() }
        binding.btnCameraEdit.setOnClickListener { startCamera() }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.uploadImageEdit.setImageURI(it)
        }
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
                        cover = it.cover // Assuming cover stays the same for now
                    )

                    bookViewModel.updateBook(updatedBook).observe(this, Observer { result ->
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
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun animateViews() {
        val cover = ObjectAnimator.ofFloat(binding.uploadImageEdit, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.edEditTitle, View.ALPHA, 1f).setDuration(100)
        val tlTitle = ObjectAnimator.ofFloat(binding.editTitle, View.ALPHA, 1f).setDuration(100)
        val author = ObjectAnimator.ofFloat(binding.edEditAuthor, View.ALPHA, 1f).setDuration(100)
        val tlAuthor = ObjectAnimator.ofFloat(binding.editAuthor, View.ALPHA, 1f).setDuration(100)
        val publisher = ObjectAnimator.ofFloat(binding.edEditPublisher, View.ALPHA, 1f).setDuration(100)
        val tlPublisher = ObjectAnimator.ofFloat(binding.editPublisher, View.ALPHA, 1f).setDuration(100)
        val pages = ObjectAnimator.ofFloat(binding.edEditPages, View.ALPHA, 1f).setDuration(100)
        val tlPages = ObjectAnimator.ofFloat(binding.editPages, View.ALPHA, 1f).setDuration(100)
        val camera = ObjectAnimator.ofFloat(binding.btnCameraEdit, View.ALPHA, 1f).setDuration(100)
        val gallery = ObjectAnimator.ofFloat(binding.btnGalleryEdit, View.ALPHA, 1f).setDuration(100)
        val update = ObjectAnimator.ofFloat(binding.buttonEdit, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playTogether(
                camera, gallery, update, cover, title, tlTitle, author, tlAuthor, publisher, tlPublisher, pages, tlPages
            )
            startDelay = 200
        }.start()
    }
}