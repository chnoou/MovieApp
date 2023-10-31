package com.chnoou.movieapp.ui.detailed_movie_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chnoou.movieapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedMovieFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedMovieFragment()
    }

    private val viewModel: DetailedMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)












    }

}