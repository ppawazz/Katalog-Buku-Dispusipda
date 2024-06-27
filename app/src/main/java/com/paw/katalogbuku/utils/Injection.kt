package com.paw.katalogbuku.utils

import android.content.Context
import com.paw.katalogbuku.model.BookRepository
import com.paw.katalogbuku.model.remote.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): BookRepository {
        val apiService = ApiConfig.getApiService()
        return BookRepository.getInstance(apiService)
    }
}