package com.redblazer.bookapp.book.presentation.screen.search_book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.redblazer.bookapp.book.presentation.Screen
import com.redblazer.bookapp.book.presentation.screen.BookViewModel
import com.redblazer.bookapp.book.presentation.screen.all_books.components.UniqueBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBookScreen(
    viewModel: BookViewModel,
    keywordToFind: String?,
    navController: NavController
) {
    val searchBookState by viewModel.searchBookState.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    if (keywordToFind != null) {
        viewModel.searchBook(searchKeyword = keywordToFind)
    }

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = ""
                            )
                        }
                    },
                    title = {
                        Text(text = "Searched Book")
                    },
                )
            }
        ) { contentPadding ->
            Box(
                Modifier.padding(contentPadding)
            ) {
                if (!searchBookState.isFound) {
                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            Modifier
                                .size(150.dp),
//                    Modifier.align(Alignment.TopCenter),
                        )
                        Text(
                            text = "BOOK NOT FOUND",
//                    Modifier.align(Alignment.Center),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }

                if (searchBookState.isFound) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        searchBookState.booksList.forEach { book ->
                            UniqueBook(
                                book = book,
                                screenWidth = screenWidth * 1f,
                                onClick = {
                                    navController.navigate(
                                        Screen.BookDetail(
                                            bookId = book.id!!,
                                        )
                                    )
                                },
                            )
                        }
                    }
                }

            }
        }
    }
}