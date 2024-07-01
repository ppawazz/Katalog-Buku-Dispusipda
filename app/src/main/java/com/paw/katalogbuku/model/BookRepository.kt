package com.paw.katalogbuku.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.paw.katalogbuku.model.remote.request.BookRequest
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.model.remote.service.ApiService
import com.paw.katalogbuku.utils.ResultState
import retrofit2.HttpException

class BookRepository private constructor(
    private val apiService: ApiService,
) {

    fun getAllBooks() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getAllBooks()
            if (response.success) {
                emit(ResultState.Success(response.payload))
            } else {
                emit(ResultState.Error(response.message))
            }
        } catch (e: HttpException) {
            Log.d(TAG, "getAllBooks: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        } catch (e: Exception) {
            Log.d(TAG, "getAllBooks: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun updateBook(book: BookItem): LiveData<ResultState<BookItem>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.updateBook(book.id, book)
            if (response.success) {
                emit(ResultState.Success(response.payload))
            } else {
                emit(ResultState.Error(response.message))
            }
        } catch (e: HttpException) {
            emit(ResultState.Error(e.message()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown Error"))
        }
    }

    fun deleteBook(bookId: String): LiveData<ResultState<Unit>> = liveData {
        emit(ResultState.Loading)
        try {
            apiService.deleteBook(bookId)
            emit(ResultState.Success(Unit))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.message ?: "Unknown error"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown Error"))
        }
    }

    companion object {
        private const val TAG = "BookRepository"

        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(apiService: ApiService): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(apiService)
            }.also { instance = it }
    }
}
