package com.redblazer.bookapp.book.presentation.screen.book_details

import com.redblazer.bookapp.book.domain.model.BookModel

data class BookDetailState(
    val isLoading: Boolean = false,
    val bookDetails: BookModel? = null,
    val isUpdate: Boolean = false,
    val isDelete: Boolean = false,
)
