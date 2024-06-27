package com.paw.katalogbuku.viewmodel

import androidx.lifecycle.ViewModel
import com.paw.katalogbuku.model.BookRepository
import com.paw.katalogbuku.model.remote.request.BookRequest

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    fun getAllBooks() = repository.getAllBooks()

    fun getBookById(id: String) = repository.getBookById(id)

    fun addBook(bookRequest: BookRequest) = repository.postBook(bookRequest)

    fun updateBook(id: String, bookRequest: BookRequest) = repository.updateBook(id, bookRequest)

    fun deleteBook(id: String) = repository.deleteBook(id)
}
