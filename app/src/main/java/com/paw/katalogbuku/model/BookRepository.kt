package com.paw.katalogbuku.model

import android.util.Log
import androidx.lifecycle.liveData
import com.paw.katalogbuku.model.remote.request.BookRequest
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

    companion object {
        private const val TAG = "BookRepository"

        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(apiService: ApiService): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(apiService)
            }.also { instance = it }
    }

//    fun getAllBooks() = liveData {
//        emit(ResultState.Loading)
//        try {
//            val response = apiService.getAllBooks()
//            emit(ResultState.Success(response))
//        } catch (e: HttpException) {
//            Log.d(TAG, "getAllBooks: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        } catch (e: Exception) {
//            Log.d(TAG, "getAllBooks: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        }
//    }
//
//    fun postBook(book: BookRequest) = liveData {
//        emit(ResultState.Loading)
//        try {
//            val response = apiService.postBook(book)
//            emit(ResultState.Success(response))
//        } catch (e: HttpException) {
//            Log.d(TAG, "postBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        } catch (e: Exception) {
//            Log.d(TAG, "postBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        }
//    }
//
//    fun getBookById(id: String) = liveData {
//        emit(ResultState.Loading)
//        try {
//            val response = apiService.getBookById(id)
//            emit(ResultState.Success(response))
//        } catch (e: HttpException) {
//            Log.d(TAG, "getBookById: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        } catch (e: Exception) {
//            Log.d(TAG, "getBookById: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        }
//    }
//
//    fun updateBook(id: String, book: BookRequest) = liveData {
//        emit(ResultState.Loading)
//        try {
//            val response = apiService.updateBook(id, book)
//            emit(ResultState.Success(response))
//        } catch (e: HttpException) {
//            Log.d(TAG, "updateBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        } catch (e: Exception) {
//            Log.d(TAG, "updateBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        }
//    }
//
//    fun deleteBook(id: String) = liveData {
//        emit(ResultState.Loading)
//        try {
//            val response = apiService.deleteBook(id)
//            emit(ResultState.Success(response))
//        } catch (e: HttpException) {
//            Log.d(TAG, "deleteBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        } catch (e: Exception) {
//            Log.d(TAG, "deleteBook: ${e.message.toString()}")
//            emit(ResultState.Error(e.message.toString()))
//        }
//    }
//
//    companion object {
//        private const val TAG = "BookRepository"
//
//        @Volatile
//        private var instance: BookRepository? = null
//        fun getInstance(apiService: ApiService): BookRepository =
//            instance ?: synchronized(this) {
//                instance ?: BookRepository(apiService)
//            }.also { instance = it }
//    }
}
