package com.example.bookapp.book.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bookapp.book.presentation.screen.BookViewModel
import com.example.bookapp.book.presentation.screen.all_books.AllBooksScreen
import com.example.bookapp.book.presentation.screen.book_details.BookDetailsScreen
import com.example.bookapp.book.presentation.screen.create_update_book.CreateUpdateBookScreen
import com.example.bookapp.book.presentation.screen.search_book.SearchBookScreen
import kotlin.reflect.typeOf


@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    startDestination: Screen = Screen.Home,
    onToggleTheme: () -> Boolean
) {

    val viewModel = hiltViewModel<BookViewModel>()

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable<Screen.Home> {
            AllBooksScreen(navController = navHostController, changeTheme = onToggleTheme, viewModel = viewModel)
        }
        composable<Screen.BookDetail> { backStackEntry ->
            val id: Int = backStackEntry.toRoute<Screen.BookDetail>().bookId

            BookDetailsScreen(bookId = id, navController = navHostController, viewModel = viewModel)
        }
        composable<Screen.SearchBook> { backStackEntry ->
            val bookTitle: String = backStackEntry.toRoute<Screen.SearchBook>().bookTitle

            SearchBookScreen(keywordToFind = bookTitle, navController = navHostController, viewModel = viewModel)
        }
        composable<Screen.CreateUpdateBook>(
            typeMap = mapOf(typeOf<BookIdNullable>() to bookIdNullable)
        ) { backStackEntry->
            val bookId: Int? = backStackEntry.toRoute<Screen.CreateUpdateBook>().bookId.id
            //val castingToInt = bookId?.toString()?.toIntOrNull()
            CreateUpdateBookScreen(navController = navHostController, bookId = bookId, viewModel = viewModel)
        }
    }
}