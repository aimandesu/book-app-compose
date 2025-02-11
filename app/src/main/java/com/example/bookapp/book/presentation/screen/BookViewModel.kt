package com.example.bookapp.book.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.book.domain.model.ApiResponse
import com.example.bookapp.book.domain.model.BookModel
import com.example.bookapp.book.domain.repository.BookRepository
import com.example.bookapp.book.presentation.screen.all_books.AllBooksState
import com.example.bookapp.book.presentation.screen.book_details.BookDetailState
import com.example.bookapp.book.presentation.screen.create_update_book.CreateUpdateBookState
import com.example.bookapp.book.presentation.screen.search_book.SearchBookState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    //first state initial
    private val _allBooksState = MutableStateFlow(AllBooksState())
    private val _bookDetailsState = MutableStateFlow(BookDetailState())
    private val _searchBookState = MutableStateFlow(SearchBookState())
    private val _createUpdateBookState = MutableStateFlow(CreateUpdateBookState())

    //to consume the state in the view / composable
    val bookDetailsState = _bookDetailsState.asStateFlow()
    val allBooksState = _allBooksState.asStateFlow()
    val searchBookState = _searchBookState.asStateFlow()
    val createUpdateBookState = _createUpdateBookState.asStateFlow()

    //date mockup
    private val currentDateTime = LocalDateTime.now()
    private val date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant())

    private val booksMockUpData = listOf(
        BookModel(
            id = 1,
            isbn = "isbn",
            name = "The journey of Elaina",
            year = 2012,
            author = "Elaina",
            description = "The book is about the adventurous Elaina, a witch in discovering the truth about the world",
            image = "https:\\/\\/content.packt.com\\/B11621\\/cover_image.png",
            createdAt = date,
            updatedAt = date,
        ),
        BookModel(
            id = 2,
            isbn = "isbn",
            name = "One Punch Man",
            year = 2012,
            author = "Saitama",
            description = "The book is about Saitama, a hero for fun person which is the strongest person in the universe of OPM",
            image = "https:\\/\\/content.packt.com\\/B11621\\/cover_image.png",
            createdAt = date,
            updatedAt = date,
        ),
    )

    init {
        getBooksFrom()
    }

     private fun getBooksFrom() {
        viewModelScope.launch {
            _allBooksState.update {
                it.copy(isLoading = true)
            }
            val books =booksMockUpData //  bookRepository.getAllBooks() // //
            if (books.isNotEmpty()) {
                _allBooksState.update { it.copy(isLoading = false, booksList = books) }
            }
        }
    }

    fun getBookDetails(bookId: Int) {
        viewModelScope.launch {
            _bookDetailsState.update { it.copy(isLoading = true) }
            _allBooksState.collect { book ->
                val theBook = book.booksList.find { it.id == bookId }
                _bookDetailsState.update { it.copy(bookDetails = theBook, isLoading = false) }
            }
        }
    }

    //create
    fun createBook(book: BookModel, context: Context) {
        viewModelScope.launch {
            val response: ApiResponse<Void> = bookRepository.createBook(book)
            _createUpdateBookState.update { it.copy(isCreating = true) }

            if (response.isSuccess) {
                _createUpdateBookState.update { it.copy(isCreated = true) }
                Toast.makeText(context, "Book is created", Toast.LENGTH_SHORT).show()

                _allBooksState.update { currentState ->
                    currentState.copy(booksList = currentState.booksList + book)
                }

            } else {
                _createUpdateBookState.update { it.copy(isCreated = false) }
                Toast.makeText(context, "Book could not be created", Toast.LENGTH_SHORT).show()
            }
            _createUpdateBookState.update { it.copy(isCreated = null, isCreating = false) }
            getBooksFrom()
        }
    }

    //update
    fun updateBook(book: BookModel, context: Context?) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
            _allBooksState.update { books ->
                val bookListNew = books.booksList.map { existingBook ->
                    if (existingBook.id == book.id) book else existingBook
                }
                    books.copy(booksList = bookListNew)
            }
            Toast.makeText(context, "Book has been updated", Toast.LENGTH_SHORT).show()
        }
    }

    //delete
    fun toggleDeleteBook(bookId: List<Int>?) {
        viewModelScope.launch {
            if (bookId == null) {
                _bookDetailsState.update { bookDetailState ->
                    bookDetailState.copy(isDelete = !bookDetailState.isDelete)
                }
            } else {
                _allBooksState.update { allBooksState ->
                    val updatedBooksList = allBooksState.booksList.filterNot { book ->
                        book.id in bookId
                    }
                    allBooksState.copy(booksList = updatedBooksList)
                }

                val deferred = bookId.map { id ->
                    async {
                        val json = Gson().toJson(mapOf("id" to id))
                        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
                        bookRepository.deleteBook(requestBody)
                    }
                }
                deferred.awaitAll()
            }
        }
    }

    //search
    fun searchBook(searchKeyword: String) {
        viewModelScope.launch {
            _allBooksState.collect { book ->
                val keywordLower = searchKeyword.lowercase()
                val booksList = book.booksList.filter { it.name.lowercase().contains(keywordLower) }
                if (booksList.isNotEmpty()) {
                    _searchBookState.update { it.copy(booksList = booksList, isFound = true) }
                } else {
                    _searchBookState.update { it.copy(booksList = emptyList(), isFound = false) }
                }
            }
        }
    }

}