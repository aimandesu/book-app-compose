package com.redblazer.bookapp.book.presentation.screen.book_details

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.redblazer.bookapp.book.domain.model.BookModel
import com.redblazer.bookapp.book.presentation.BookIdNullable
import com.redblazer.bookapp.book.presentation.Screen
import com.redblazer.bookapp.book.presentation.screen.BookViewModel
import com.redblazer.bookapp.book.presentation.util.BookDetailsMenu
import com.redblazer.bookapp.book.presentation.util.LoadingUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    viewModel: BookViewModel,
    bookId: Int?,
    navController: NavController,
) {

    val bookDetailState by viewModel.bookDetailsState.collectAsStateWithLifecycle()
    val menuItems = BookDetailsMenu.entries
    val context = LocalContext.current

    //variable
    var isActionExpanded by remember { mutableStateOf(false) }

    if (bookId != null) {
        viewModel.getBookDetails(bookId = bookId)
    }

    if (bookDetailState.isLoading) {
        LoadingUtil()
    }

    if (bookDetailState.bookDetails != null) {

        val theBook = bookDetailState.bookDetails as BookModel

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
                        Text(text = "Book Details")
                    },
                    actions = {
                        IconButton(

                            onClick = {
                                //open menu option
                                isActionExpanded = !isActionExpanded
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = ""
                            )
                        }
                        DropdownMenu(
                            expanded = isActionExpanded,
                            onDismissRequest = { isActionExpanded = false },
                            properties = PopupProperties(focusable = true)
                        ) {
                            menuItems.map { item ->
                                DropdownMenuItem(
                                    onClick = {
                                        isActionExpanded = false
                                        when (item) {
                                            BookDetailsMenu.EDIT -> {
                                                // Handle EDIT action
                                                navController.navigate(
                                                    Screen.CreateUpdateBook(bookId = BookIdNullable(id = bookId))
                                                )
                                            }

                                            BookDetailsMenu.DELETE -> {
                                                // Handle DELETE action
                                                Toast.makeText(context, "Delete Not Yet Implemented Here", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    text = {
                                        Text(item.name)
                                    },
                                )
                            }

                        }

                    }
                )
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                Column(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = theBook.name,
                        style = TextStyle(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = theBook.author,
                        style = TextStyle(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
//                    Box (
//                        Modifier
//                            .fillMaxWidth()
//                            ,
//                        contentAlignment = Alignment.CenterEnd,
//                    ) {
//                        AsyncImage(
//                            modifier = Modifier
//                                .fillMaxWidth(0.5f),
//                            model = theBook.image,
//                            contentDescription = null,
//                        )
//                    }
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(230.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(2.dp, MaterialTheme.colorScheme.surfaceVariant)
                            .padding(5.dp),
                        model = theBook.image,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        Modifier
//                            .fillMaxWidth(0.6f)
                            .height(70.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(15.dp))
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(15.dp)
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Book ID: ${theBook.id}",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Year: ${ theBook.year}",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(15.dp)
                        ),
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Description",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = theBook.description,
                                style = TextStyle(fontSize = 20.sp),
                            )
                        }
                    }
                }
            }
        }
    }
}