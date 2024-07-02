package com.paw.katalogbuku.model.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    @field:SerializedName("payload")
    val payload: List<BookItem>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
) : Parcelable

@Parcelize
data class BookItem(
    @field:SerializedName("cover")
    val cover: String,

    @field:SerializedName("pages")
    val pages: Int,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("__v")
    val v: Int,

    @field:SerializedName("publisher")
    val publisher: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("title")
    val title: String
) : Parcelable
