package com.paw.katalogbuku.model.remote.service

import com.paw.katalogbuku.model.remote.request.BookRequest
import com.paw.katalogbuku.model.remote.response.ApiResponse
import retrofit2.http.*

interface ApiService {

    @GET("books")
    suspend fun getAllBooks(): ApiResponse

//    @GET("books")
//    suspend fun getAllBooks(): ApiResponse
//
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
}