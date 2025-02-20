package com.redblazer.bookapp.di

import com.redblazer.bookapp.book.data.repository.BookRepositoryImpl
import com.redblazer.bookapp.book.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBookRepository(impl: BookRepositoryImpl) : BookRepository
}