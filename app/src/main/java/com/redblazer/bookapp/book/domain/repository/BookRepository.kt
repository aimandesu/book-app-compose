package com.redblazer.bookapp.book.domain.repository

import com.redblazer.bookapp.book.domain.model.ApiResponse
import com.redblazer.bookapp.book.domain.model.BookModel
import okhttp3.RequestBody

interface BookRepository {

    suspend fun getAllBooks(): List<BookModel>
    suspend fun createBook(book: BookModel) : ApiResponse<Void>
    suspend fun updateBook(book: BookModel)
    suspend fun deleteBook(requestId: RequestBody)
}