package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityAddBookBinding
import com.paw.katalogbuku.utils.getImageUri

class AddBookActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddBookBinding
    private var currentImageUri: Uri? = null
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
            startCamera()
        }
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
            binding.uploadImage.setImageURI(it)
        }
    }

    private fun playAnimation() {
        val cover = ObjectAnimator.ofFloat(binding.uploadImage, View.ALPHA, 1f).setDuration(100)
        val title =
            ObjectAnimator.ofFloat(binding.edAddTitle, View.ALPHA, 1f).setDuration(100)
        val tlTitle =
            ObjectAnimator.ofFloat(binding.addTitle, View.ALPHA, 1f).setDuration(100)
        val author =
            ObjectAnimator.ofFloat(binding.edAddAuthor, View.ALPHA, 1f).setDuration(100)
        val tlAuthor =
            ObjectAnimator.ofFloat(binding.addAuthor, View.ALPHA, 1f).setDuration(100)
        val publisher =
            ObjectAnimator.ofFloat(binding.edAddPublisher, View.ALPHA, 1f).setDuration(100)
        val tlPublisher =
            ObjectAnimator.ofFloat(binding.addPublisher, View.ALPHA, 1f).setDuration(100)
        val pages =
            ObjectAnimator.ofFloat(binding.edAddPages, View.ALPHA, 1f).setDuration(100)
        val tlPages =
            ObjectAnimator.ofFloat(binding.addPages, View.ALPHA, 1f).setDuration(100)
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
}