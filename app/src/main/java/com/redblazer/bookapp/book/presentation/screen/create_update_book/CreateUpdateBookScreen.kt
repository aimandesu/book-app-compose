package com.redblazer.bookapp.book.presentation.screen.create_update_book

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.redblazer.bookapp.book.domain.model.BookModel
import com.redblazer.bookapp.book.presentation.screen.BookViewModel
import com.redblazer.bookapp.book.presentation.util.CreateBookFields
import com.redblazer.bookapp.book.presentation.util.TextFieldUtil
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

data class FieldState(
    var input: MutableState<String>,
    val label: CreateBookFields,
    val isFocused: Boolean = false,
    val focusRequester: FocusRequester = FocusRequester(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateBookScreen(
    viewModel: BookViewModel,
    navController: NavController,
    bookId: Int?,
) {

    val bookDetailState by viewModel.bookDetailsState.collectAsStateWithLifecycle()
    val createBookState by viewModel.createUpdateBookState.collectAsStateWithLifecycle()

    val currentDateTime = LocalDateTime.now()
    val date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant())
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val isbn = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val year = remember { mutableStateOf("") }
    val author = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val image =
        remember { mutableStateOf("") }
    val createdAt = remember { mutableStateOf(date.toString()) }
    val updatedAt = remember { mutableStateOf(date.toString()) }

    if (bookId != null) {

        viewModel.getBookDetails(bookId)

        if (bookDetailState.bookDetails != null) {
            bookDetailState.bookDetails.let {
                isbn.value = it!!.isbn
                name.value = it.name
                year.value = it.year.toString()
                author.value = it.author
                description.value = it.description
                image.value = it.image
                createdAt.value = it.createdAt.toString()
                updatedAt.value = it.updatedAt.toString()
            }
        }

    }

    val list = remember {
        mutableStateListOf(
            FieldState(isbn, CreateBookFields.ISBN),
            FieldState(name, CreateBookFields.NAME),
            FieldState(year, CreateBookFields.YEAR),
            FieldState(author, CreateBookFields.AUTHOR),
            FieldState(description, CreateBookFields.DESCRIPTION),
            FieldState(image, CreateBookFields.IMAGE),
            FieldState(createdAt, CreateBookFields.CREATED),
            FieldState(updatedAt, CreateBookFields.UPDATED),
        )
    }

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
                    Text(text = if (bookId != null) "Update Book" else "Create Book")
                },
            )
        }
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(13.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            if (createBookState.isCreating){
                Text(text = "Creating...")
            }

            list.forEachIndexed { index, fieldState ->
                var isFocused by remember { mutableStateOf(fieldState.isFocused) }

                TextFieldUtil(
                    text = fieldState.input.value,
                    onTextChange = { newText -> fieldState.input.value = newText },
                    label = fieldState.label.toString(),
                    isFocused = isFocused,
                    onFocusChange = { isFocused = it },
                    focusRequester = fieldState.focusRequester,
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (index < list.lastIndex) ImeAction.Next else ImeAction.Done,
                        keyboardType = when (fieldState.label) {
                            CreateBookFields.YEAR -> KeyboardType.Number
                            CreateBookFields.CREATED -> KeyboardType.Number
                            CreateBookFields.UPDATED -> KeyboardType.Number
                            else -> KeyboardType.Text
                        }
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            TextButton(
                onClick = {
                    val allNonEmpty = list.all { it.input.value.isNotEmpty() }

                    val book = BookModel(
                        id = bookId, //this could be either null or not, so no need to check
                        isbn = isbn.value,
                        name = name.value,
                        year = Integer.parseInt(year.value),
                        author = author.value,
                        description = description.value,
                        image = image.value,
                        //both these not quite sure how yet
                        createdAt = date,
                        updatedAt = date,
                    )

                    if (allNonEmpty) {
                        if (bookId != null){
                            viewModel.updateBook(book, context)
                        }else{
                            viewModel.createBook(book, context)
                        }
                    } else {
                        Toast.makeText(context, "There is empty field", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(15.dp)
                    )
                    .align(Alignment.End)
            ) {
                Text(
                    text = if(bookId != null) "Update Book" else "Create New Book",
                )
            }

        }
    }

}
