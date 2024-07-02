package com.paw.katalogbuku.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.paw.katalogbuku.model.remote.request.UploadRequest
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.model.remote.response.UpdateResponse
import com.paw.katalogbuku.model.remote.response.UploadResponse
import com.paw.katalogbuku.model.remote.service.ApiService
import com.paw.katalogbuku.utils.ResultState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

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

    fun postBook(cover: File, uploadRequest: UploadRequest): LiveData<ResultState<UploadResponse>> = liveData {
        emit(ResultState.Loading)
        val requestImageFile = cover.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val coverPart = MultipartBody.Part.createFormData(
            "cover",
            cover.name,
            requestImageFile
        )
        val title = uploadRequest.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val author = uploadRequest.author.toRequestBody("text/plain".toMediaTypeOrNull())
        val publisher = uploadRequest.publisher.toRequestBody("text/plain".toMediaTypeOrNull())
        val pages = uploadRequest.pages.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        try {
            val response = apiService.postBook(coverPart, title, author, publisher, pages)
            if (response.success) {
                emit(ResultState.Success(response))
            } else {
                emit(ResultState.Error(response.message))
            }
        } catch (e: HttpException) {
            Log.d(TAG, "postBook: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        } catch (e: Exception) {
            Log.d(TAG, "postBook: ${e.message.toString()}")
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
}
