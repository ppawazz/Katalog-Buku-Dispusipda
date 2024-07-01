package com.paw.katalogbuku.model.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("payload")
    val payload: BookItem,

    @field:SerializedName("message")
    val message: String
)
