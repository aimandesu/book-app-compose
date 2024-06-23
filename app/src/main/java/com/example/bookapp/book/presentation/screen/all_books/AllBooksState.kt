package com.example.bookapp.book.presentation.screen.all_books

import com.example.bookapp.book.domain.model.BookModel

data class AllBooksState(
    val isLoading: Boolean = false,
    var booksList: List<BookModel> = emptyList(),
)
