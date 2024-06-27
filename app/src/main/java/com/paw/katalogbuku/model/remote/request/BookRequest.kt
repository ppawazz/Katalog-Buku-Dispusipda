package com.paw.katalogbuku.model.remote.request

data class BookRequest(
    val cover: String,
    val title: String,
    val author: String,
    val publisher: String,
    val pages: Int
)
