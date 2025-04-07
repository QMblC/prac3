package com.example.myapplication.di

import com.example.myapplication.api.MovieMapper
import com.example.myapplication.data.MoviesRepository
import com.example.myapplication.view.DetailsViewModel
import com.example.myapplication.view.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    single<MoviesRepository> { MoviesRepository(get(), get()) }

    factory { MovieMapper() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
}