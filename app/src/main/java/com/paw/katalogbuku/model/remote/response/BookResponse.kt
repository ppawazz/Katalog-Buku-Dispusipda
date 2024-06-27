package com.paw.katalogbuku.model.remote.response

import com.google.gson.annotations.SerializedName

data class ApiResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("payload")
	val payload: List<BookResponse>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BookResponse(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("cover")
	val cover: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("publisher")
	val publisher: String? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("__v")
	val version: Int? = null
)
