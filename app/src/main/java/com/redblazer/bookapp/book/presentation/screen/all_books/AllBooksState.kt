package com.redblazer.bookapp.book.presentation.screen.all_books

import com.redblazer.bookapp.book.domain.model.BookModel

data class AllBooksState(
    val isLoading: Boolean = false,
    var booksList: List<BookModel> = emptyList(),
)
