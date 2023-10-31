package com.chnoou.movieapp.di

import com.chnoou.movieapp.backend.MovieRepository
import com.chnoou.movieapp.ui.detailed_movie_fragment.DetailedMovieViewModel
import com.chnoou.movieapp.ui.home_fragment.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::MovieRepository)
    viewModelOf(::DetailedMovieViewModel)
    viewModelOf(::HomeViewModel)
    //viewModel { HomeViewModel(get()) }
}