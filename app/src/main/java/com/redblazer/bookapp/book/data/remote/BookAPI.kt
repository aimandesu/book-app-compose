package com.redblazer.bookapp.book.data.remote

import com.redblazer.bookapp.book.domain.model.BookModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BookAPI {

    @GET("get_books.php")
    suspend fun getAllBooksAPI() : List<BookModel>

    @POST("create_book.php")
    suspend fun createBookAPI(@Body bookModel: BookModel) : Response<Void>

    @POST("update_book.php")
    suspend fun updateBookAPI(@Body bookModel: BookModel)

    @POST("delete_book.php")
    suspend fun deleteBookAPI(@Body requestId: RequestBody)

}