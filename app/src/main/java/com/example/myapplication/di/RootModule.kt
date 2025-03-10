package com.example.myapplication.di

import com.example.myapplication.data.MovieRepository
import com.example.myapplication.view.DetailsViewModel
import com.example.myapplication.view.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    single<MovieRepository> { MovieRepository() }

    viewModel { ListViewModel(get(), it.get()) }
    viewModel { DetailsViewModel(get(), it.get(), it.get()) }
}