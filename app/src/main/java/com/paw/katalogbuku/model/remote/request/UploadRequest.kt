package com.paw.katalogbuku.model.remote.request

data class UploadRequest(
    val title: String,
    val author: String,
    val publisher: String,
    val pages: Int
)
