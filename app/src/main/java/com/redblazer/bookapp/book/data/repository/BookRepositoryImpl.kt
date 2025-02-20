package com.redblazer.bookapp.book.data.repository

import com.redblazer.bookapp.book.data.remote.BookAPI
import com.redblazer.bookapp.book.domain.model.ApiResponse
import com.redblazer.bookapp.book.domain.model.BookModel
import com.redblazer.bookapp.book.domain.repository.BookRepository
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val books: BookAPI
) : BookRepository {

    //read
    override suspend fun getAllBooks(): List<BookModel> {
        return books.getAllBooksAPI()
    }

    //create
    override suspend fun createBook(book: BookModel): ApiResponse<Void> {
        return try {
            val response: Response<Void> = books.createBookAPI(book)
            if (response.isSuccessful) {
                ApiResponse(
                    data = response.body(),
                    statusCode = response.code(),
                    isSuccess = true
                )
            } else {
                ApiResponse(
                    data = null,
                    statusCode = response.code(),
                    isSuccess = false,
                    errorMessage = response.message()
                )
            }
        } catch (e: Exception) {
            ApiResponse(
                data = null,
                statusCode = -1,
                isSuccess = false,
                errorMessage = e.localizedMessage
            )
        }

    }

    //update
    override suspend fun updateBook(book: BookModel) {
        books.updateBookAPI(book)
    }

    //delete
    override suspend fun deleteBook(requestId: RequestBody) {
        books.deleteBookAPI(requestId)
    }

}