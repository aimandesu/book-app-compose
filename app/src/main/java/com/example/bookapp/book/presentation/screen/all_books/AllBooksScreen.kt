package com.example.bookapp.book.presentation.screen.all_books

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookapp.book.presentation.BookIdNullable
import com.example.bookapp.book.presentation.Screen
import com.example.bookapp.book.presentation.screen.BookViewModel
import com.example.bookapp.book.presentation.screen.all_books.components.SearchBarTop
import com.example.bookapp.book.presentation.screen.all_books.components.UniqueBook
import com.example.bookapp.book.presentation.screen.book_details.BookDetailState
import com.example.bookapp.book.presentation.util.LoadingUtil
import com.example.bookapp.book.presentation.util.NavigationTappable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


@Composable
fun AllBooksScreen(
    viewModel: BookViewModel,
    navController: NavController,
    changeTheme: () -> Boolean,
) {
    val allBooksState by viewModel.allBooksState.collectAsStateWithLifecycle()
    val bookDetailState by viewModel.bookDetailsState.collectAsStateWithLifecycle()

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    if (allBooksState.isLoading) {
        LoadingUtil()
    }

    if (allBooksState.booksList.isNotEmpty()) {
        BooksContent(
            allBooksState,
            bookDetailState,
            screenWidth,
            navController,
            viewModel,
            changeTheme
        )
    }


}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BooksContent(
    state: AllBooksState,
    bookDetailState: BookDetailState,
    screenWidth: Dp,
    navController: NavController,
    viewModel: BookViewModel,
    changeTheme: () -> Boolean,
) {

    val bookListChecked by remember { mutableStateOf(mutableStateListOf<Int>()) }

    //date mockup
    val currentDateTime = LocalDateTime.now()
    val date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant())

    var isDarkTheme by remember { mutableStateOf(true) }

    Surface {
        Column(
            Modifier
                .fillMaxSize()
            // .background(color = MaterialTheme.colorScheme.background),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    changeTheme().let { themeCurrent -> isDarkTheme = themeCurrent }
                },
            ) {
                if (isDarkTheme)
                    Icon(imageVector = Icons.Filled.LightMode, contentDescription = "") else
                    Icon(imageVector = Icons.Filled.DarkMode, contentDescription = "")
            }

            SearchBarTop(navController = navController)

            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.94f)
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                    ) {
                        items(state.booksList) { book ->
                            Row {
                                UniqueBook(
                                    book = book,
                                    screenWidth = if (!bookDetailState.isDelete) screenWidth * 1f else screenWidth * 0.88f,
                                    onClick = {
                                        navController.navigate(
                                            Screen.BookDetail(
                                                bookId = book.id!!,
                                            )
                                        )
                                    },
                                )
                                if (bookDetailState.isDelete) {
                                    Box(
                                        Modifier
                                            .width(screenWidth * 0.1f)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                if (bookListChecked.contains(book.id)) {
                                                    bookListChecked.remove(book.id!!)
                                                } else {
                                                    bookListChecked.add(book.id!!)
                                                }

                                            },
                                        ) {
                                            Icon(
                                                imageVector = if (bookListChecked.contains(book.id)) {
                                                    Icons.Filled.Check
                                                } else {
                                                    Icons.Filled.CheckBoxOutlineBlank
                                                },
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.BottomStart),
                ) {
                    NavigationTappable(
                        content = {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    onClick = {
                                        navController.navigate(
                                            Screen.CreateUpdateBook(
                                                bookId = BookIdNullable(id = null)
                                            ),
                                        )
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Add"
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (bookListChecked.isNotEmpty()) {
                                            //viewModel.updateBook(state.booksList[0],null)
                                            viewModel.toggleDeleteBook(bookListChecked)
                                            bookListChecked.clear()
                                            viewModel.toggleDeleteBook(
                                                null
                                            )
                                        } else {
                                            viewModel.toggleDeleteBook(
                                                null
                                            )
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                            }
                        },
                    )
                }
            }
        }

    }


}