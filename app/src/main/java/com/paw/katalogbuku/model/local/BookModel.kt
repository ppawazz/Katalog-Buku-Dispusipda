package com.paw.katalogbuku.model.local

data class BookModel(
    val id: Int,
    val photo: Int,
    val title: String,
    val author: String,
    val desc: String
)