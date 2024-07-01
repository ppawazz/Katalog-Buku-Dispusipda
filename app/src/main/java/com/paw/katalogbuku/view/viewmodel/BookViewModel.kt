package com.paw.katalogbuku.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.katalogbuku.model.BookRepository
import com.paw.katalogbuku.model.remote.request.BookRequest
import com.paw.katalogbuku.model.remote.response.BookItem

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    fun getAllBooks() = repository.getAllBooks()

    fun updateBook(book: BookItem) = repository.updateBook(book)

    fun deleteBook(bookId: String) = repository.deleteBook(bookId)
}