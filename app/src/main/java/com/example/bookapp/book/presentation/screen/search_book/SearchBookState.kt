package com.example.bookapp.book.presentation.screen.search_book

import com.example.bookapp.book.domain.model.BookModel

data class SearchBookState(
    val isFound: Boolean = false,
    val booksList: List<BookModel> = emptyList(),
)
