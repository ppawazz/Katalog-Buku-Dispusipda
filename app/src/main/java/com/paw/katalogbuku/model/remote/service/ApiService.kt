package com.paw.katalogbuku.model.remote.service

import com.paw.katalogbuku.model.remote.response.ApiResponse
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.model.remote.response.UpdateResponse
import com.paw.katalogbuku.model.remote.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("books")
    suspend fun getAllBooks(): ApiResponse

    @DELETE("books/{id}")
    suspend fun deleteBook(@Path("id") bookId: String)

    @PUT("books/{id}")
    suspend fun updateBook(@Path("id") bookId: String, @Body book: BookItem): UpdateResponse

    @Multipart
    @POST("books/addbooks")
    suspend fun postBook(
        @Part cover: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("author") author: RequestBody,
        @Part("publisher") publisher: RequestBody,
        @Part("pages") pages: RequestBody
    ): UploadResponse
}