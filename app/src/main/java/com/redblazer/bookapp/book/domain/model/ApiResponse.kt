package com.redblazer.bookapp.book.domain.model

data class ApiResponse<T>(
    val data: T?,
    val statusCode: Int,
    val isSuccess: Boolean,
    val errorMessage: String? = null
)
