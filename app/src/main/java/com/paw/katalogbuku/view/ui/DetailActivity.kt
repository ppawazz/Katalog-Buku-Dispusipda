package com.paw.katalogbuku.view.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.paw.katalogbuku.R
import com.paw.katalogbuku.databinding.ActivityDetailBinding
import com.paw.katalogbuku.model.remote.response.BookItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bookItem: BookItem? = intent.getParcelableExtra(BOOK_ITEM)
        bookItem?.let {
            displayBookDetails(it)
        }
        playAnimation()
    }

    private fun displayBookDetails(book: BookItem) {
        with(binding) {
            tvDetailTitle.text = book.title
            tvDetailAuthor.text = book.author
            tvDetailPublisher.text = getString(R.string.publisher_template, book.publisher)
            tvDetailPages.text = getString(R.string.pages_template, book.pages.toString())
            Glide.with(this@DetailActivity).load(book.cover).into(ivDetailPhoto)
        }
    }

    private fun playAnimation() {
        val photo = ObjectAnimator.ofFloat(binding.ivDetailPhoto, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.tvDetailTitle, View.ALPHA, 1f).setDuration(100)
        val author = ObjectAnimator.ofFloat(binding.tvDetailAuthor, View.ALPHA, 1f).setDuration(100)
        val publisher = ObjectAnimator.ofFloat(binding.tvDetailPublisher, View.ALPHA, 1f).setDuration(100)
        val pages = ObjectAnimator.ofFloat(binding.tvDetailPages, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                photo,
                title,
                author,
                publisher,
                pages
            )
            startDelay = 100
        }.start()
    }

    companion object {
        const val BOOK_ITEM = "book_item_extra"
    }
}
