package com.paw.katalogbuku.view.ui

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityAddBookBinding
import com.paw.katalogbuku.model.remote.request.UploadRequest
import com.paw.katalogbuku.utils.ResultState
import com.paw.katalogbuku.utils.getImageUri
import com.paw.katalogbuku.utils.reduceFileImage
import com.paw.katalogbuku.utils.showToast
import com.paw.katalogbuku.utils.uriToFile
import com.paw.katalogbuku.view.viewmodel.BookViewModel
import com.paw.katalogbuku.view.viewmodel.ViewModelFactory

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private var selectedImageUri: Uri? = null
    private val bookViewModel: BookViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playAnimation()

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            checkCameraPermission()
        }

        binding.buttonAdd.setOnClickListener {
            postBook()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        selectedImageUri = getImageUri(this)
        launcherIntentCamera.launch(selectedImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        selectedImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.uploadImage.setImageURI(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun postBook() {
        val title = binding.edAddTitle.text.toString().trim()
        val author = binding.edAddAuthor.text.toString().trim()
        val publisher = binding.edAddPublisher.text.toString().trim()
        val pages = binding.edAddPages.text.toString().trim()

        // Validate the input fields
        if (title.isEmpty()) {
            binding.edAddTitle.error = "Title is required"
            return
        }
        if (author.isEmpty()) {
            binding.edAddAuthor.error = "Author is required"
            return
        }
        if (publisher.isEmpty()) {
            binding.edAddPublisher.error = "Publisher is required"
            return
        }
        if (pages.isEmpty()) {
            binding.edAddPages.error = "Pages are required"
            return
        }

        selectedImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()

            val uploadRequest = UploadRequest(
                title = title,
                author = author,
                publisher = publisher,
                pages = pages.toInt()
            )

            bookViewModel.postBook(imageFile, uploadRequest).observe(this) { response ->
                when (response) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.isVisible = true
                    }
                    is ResultState.Error -> {
                        binding.progressIndicator.isVisible = false
                        showToast(response.error)
                    }
                    is ResultState.Success -> {
                        binding.progressIndicator.isVisible = false
                        showToast(response.data.message)
                        setResult(RESULT_OK)  // Set result to indicate success
                        finish()  // Go back to previous screen
                    }
                }
            }
        } ?: showToast("No Image")
    }

    private fun playAnimation() {
        val cover = ObjectAnimator.ofFloat(binding.uploadImage, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.edAddTitle, View.ALPHA, 1f).setDuration(100)
        val tlTitle = ObjectAnimator.ofFloat(binding.addTitle, View.ALPHA, 1f).setDuration(100)
        val author = ObjectAnimator.ofFloat(binding.edAddAuthor, View.ALPHA, 1f).setDuration(100)
        val tlAuthor = ObjectAnimator.ofFloat(binding.addAuthor, View.ALPHA, 1f).setDuration(100)
        val publisher = ObjectAnimator.ofFloat(binding.edAddPublisher, View.ALPHA, 1f).setDuration(100)
        val tlPublisher = ObjectAnimator.ofFloat(binding.addPublisher, View.ALPHA, 1f).setDuration(100)
        val pages = ObjectAnimator.ofFloat(binding.edAddPages, View.ALPHA, 1f).setDuration(100)
        val tlPages = ObjectAnimator.ofFloat(binding.addPages, View.ALPHA, 1f).setDuration(100)
        val camera = ObjectAnimator.ofFloat(binding.btnCamera, View.ALPHA, 1f).setDuration(100)
        val gallery = ObjectAnimator.ofFloat(binding.btnGallery, View.ALPHA, 1f).setDuration(100)
        val upload = ObjectAnimator.ofFloat(binding.buttonAdd, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playTogether(
                camera, gallery, upload, cover, title, tlTitle, author, tlAuthor, publisher, tlPublisher, pages, tlPages
            )
            startDelay = 200
        }.start()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show()
            }
        }
    }
}