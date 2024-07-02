package com.paw.katalogbuku.view.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.katalogbuku.model.BookRepository
import com.paw.katalogbuku.model.remote.request.UploadRequest
import com.paw.katalogbuku.model.remote.response.BookItem
import java.io.File

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    fun getAllBooks() = repository.getAllBooks()

    fun deleteBook(bookId: String) = repository.deleteBook(bookId)

    fun updateBook(book: BookItem) = repository.updateBook(book)

    fun postBook(cover: File, uploadRequest: UploadRequest) = repository.postBook(cover, uploadRequest)
}