package com.paw.katalogbuku.model.remote.service

import com.paw.katalogbuku.model.remote.request.BookRequest
import com.paw.katalogbuku.model.remote.response.ApiResponse
import com.paw.katalogbuku.model.remote.response.BookItem
import com.paw.katalogbuku.model.remote.response.UpdateResponse
import retrofit2.http.*

interface ApiService {

    @GET("books")
    suspend fun getAllBooks(): ApiResponse

    @PUT("books/{id}")
    suspend fun updateBook(@Path("id") bookId: String, @Body book: BookItem): UpdateResponse

    @DELETE("books/{id}")
    suspend fun deleteBook(@Path("id") bookId: String)
}

//    @POST("books/addbooks")
//    suspend fun postBook(
//        @Body request: BookRequest
//    ): AResponse
//
//    @GET("books/{id}")
//    suspend fun getBookById(
//        @Path("id") id: String
//    ): ApiResponse
//
//    @PUT("books/{id}")
//    suspend fun updateBook(
//        @Path("id") id: String,
//        @Body request: BookRequest
//    ): ApiResponse
//
//    @DELETE("books/{id}")
//    suspend fun deleteBook(
//        @Path("id") id: String
//    ): ApiResponse
