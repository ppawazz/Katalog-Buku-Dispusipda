package com.paw.katalogbuku.model.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(
	@field:SerializedName("payload")
	val payload: List<BookItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

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
)
